package net.violet.platform.voice.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.common.utils.io.StreamTools;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public abstract class CommonEngine extends TTSEngine {

	private static final Logger LOGGER = Logger.getLogger(CommonEngine.class);

	private static final int DEFAULT_TTS_STRING_MAX = 500;

	private static final int MAX_ATTEMPTS = 5;
	private static final int WAIT_INCR_PER_ATTEMPT = 3000;
	private static final int WAIT_SERVER_OUT = 20000;

	private final Map<TTSJob, Process> mProcesses;
	private final BlockingQueue<Process> mAvailableProcesses;

	private static final Pattern DATA_REGEX = Pattern.compile("DATA (\\d+)");
	private static final Pattern ERROR_REGEX = Pattern.compile("ERROR (-?\\d+) (.*)");

	/** stats **/
	private final TimerTask mTimerTask;
	private long nbTTS;
	private long time = System.currentTimeMillis();
	private long workTime;
	private long failed;

	private long rNbTTS;
	private long rNbTTSPast;
	private long ratePast;
	private long rWorkTime;
	private long rFailed;
	private long rFailedPast;
	private static final Timer STAT_TIMER = new Timer();
	private static final long STATS_FREQUENCE = 300000L;
	private static final long JOBS_MAX_LIFETIME = 60000L;

	public CommonEngine(TTSEngineConfig inConfig) {
		super(inConfig);
		this.mProcesses = new HashMap<TTSJob, Process>();
		this.mAvailableProcesses = new LinkedBlockingQueue<Process>();
		for (int indexProcess = inConfig.getMaxChannels(); indexProcess > 0; indexProcess--) {
			this.mAvailableProcesses.add(createProcess());
		}

		this.mTimerTask = new TimerTask() {

			@Override
			public void run() {
				synchronized (CommonEngine.this.mTimerTask) {
					if ((CommonEngine.this.rNbTTS != 0) || (CommonEngine.this.rFailed != 0)) {
						if (CommonEngine.this.rNbTTS != 0) {
							CommonEngine.this.ratePast = CommonEngine.this.rWorkTime / CommonEngine.this.rNbTTS;
						} else {
							CommonEngine.this.ratePast = 0;
						}
						CommonEngine.this.rNbTTSPast = CommonEngine.this.rNbTTS;
						CommonEngine.this.rFailedPast = CommonEngine.this.rFailed;
					} else {
						CommonEngine.this.ratePast = 0;
						CommonEngine.this.rNbTTSPast = 0;
						CommonEngine.this.rFailedPast = 0;
					}
					CommonEngine.this.rNbTTS = 0;
					CommonEngine.this.rWorkTime = 0;
					CommonEngine.this.rFailed = 0;
				}
			}
		};

		CommonEngine.STAT_TIMER.schedule(this.mTimerTask, Calendar.getInstance().getTime(), CommonEngine.STATS_FREQUENCE);
	}

	@Override
	protected void doCleanEngineConfig(int inOldMaxChannels, int inNewMaxChannels) {
		try {
			for (int countOldChannels = inOldMaxChannels; countOldChannels > 0; countOldChannels--) {
				final Process theProcess = this.mAvailableProcesses.take();
				theProcess.destroy();
			}

			for (int indexProcess = inNewMaxChannels; indexProcess > 0; indexProcess--) {
				this.mAvailableProcesses.put(createProcess());
			}
		} catch (final InterruptedException e) {
			CommonEngine.LOGGER.warn(e, e);
		}
	}

	@Override
	protected void doKillJob(TTSJob theJob) {
		synchronized (this.mProcesses) {
			final Process theProcess = this.mProcesses.remove(theJob);
			if (theProcess != null) {
				restartProcess(theProcess);
			}
		}
	}

	/**
	 * Détruit un process et crée un nouveau.
	 * 
	 * @param inProcess processus à détruire.
	 */
	private void restartProcess(Process inProcess) {
		try {
			inProcess.getOutputStream().write(CommonEngine.QUIT);
			inProcess.getOutputStream().write('\n');
		} catch (final IOException e) {
			CommonEngine.LOGGER.warn(e, e);
		}

		inProcess.destroy();
		try {
			this.mAvailableProcesses.put(createProcess());
		} catch (final InterruptedException e) {
			CommonEngine.LOGGER.fatal(e, e);
		}
	}

	private void restartProcess(TTSJob inJob) {
		final Process theProcess;
		synchronized (this.mProcesses) {
			theProcess = this.mProcesses.remove(inJob);
		}

		if (theProcess != null) {
			// Kill.
			restartProcess(theProcess);
		}
	}

	protected abstract String getTTSFormat();

	@Override
	protected Map<String, Object> doProcessJob(final TTSJob inJob) throws Exception {
		inJob.setStatus("beginning");
		Process theProcess = this.mAvailableProcesses.take();
		synchronized (this.mProcesses) {
			this.mProcesses.put(inJob, theProcess);
		}

		final long startTime = System.currentTimeMillis();
		String texte = inJob.getText();
		final TTSVoice theVoice = inJob.getVoice();
		CommonEngine.LOGGER.info("USED VOICE : " + theVoice.getName() + " (" + theVoice.getLanguage() + ") -> TEXT : " + texte);
		if (texte.length() > CommonEngine.DEFAULT_TTS_STRING_MAX) {
			texte = texte.substring(0, CommonEngine.DEFAULT_TTS_STRING_MAX);
		}
		int mNbAttempts = 0;
		final Map<String, Object> theResult = new HashMap<String, Object>();
		final String cmd = TTSServerImpl.SPEAK + theVoice.getCommand() + net.violet.common.StringShop.SPACE + theVoice.getLanguage() + net.violet.common.StringShop.SPACE + theVoice.getPitch() + net.violet.common.StringShop.SPACE + theVoice.getSpeed() + net.violet.common.StringShop.SPACE + hexstr(texte);
		while (theResult.get(TTSServerImpl.DATA) == null) {
			final Timer theTimer = new Timer();
			try {
				final TimerTask theTask = new TimerTask() {

					@Override
					public void run() {
						CommonEngine.LOGGER.warn("KILLING PROCESS FOR JOB " + inJob);
						doKillJob(inJob);
					}
				};
				// appel du code en C
				inJob.setStatus("executing");
				theResult.put(TTSServerImpl.FORMAT, getTTSFormat());
				theTimer.schedule(theTask, CommonEngine.JOBS_MAX_LIFETIME);
				CommonEngine.LOGGER.info("cmd sent to the server " + cmd);
				theResult.put(TTSServerImpl.DATA, sendSpeakCommand(theProcess, cmd));
				theTimer.cancel();
				inJob.setStatus("finished successfully");

				synchronized (this.mTimerTask) {
					this.nbTTS++;
					this.rNbTTS++;
				}
			} catch (final IOException anException) {
				// On est parti pour redémarrer le job.
				theTimer.cancel();

				CommonEngine.LOGGER.fatal(anException, anException);

				// Pause de mNbAttempts * 3 secondes.
				mNbAttempts++;
				if (mNbAttempts > CommonEngine.MAX_ATTEMPTS) {
					synchronized (this.mTimerTask) {
						this.failed++;
						this.rFailed++;
					}
					inJob.setStatus("failed " + (mNbAttempts - 1) + " attempt(s), no more go");
					restartProcess(inJob);
					throw anException;
				}
				inJob.setStatus("failed " + (mNbAttempts - 1) + " attempt(s), will try again");

				if (anException instanceof SocketException) {
					Thread.sleep(mNbAttempts * CommonEngine.WAIT_SERVER_OUT);
				} else {
					Thread.sleep(mNbAttempts * CommonEngine.WAIT_INCR_PER_ATTEMPT);
				}

				theProcess = restartJob(inJob);
			} catch (final IllegalStateException anException) {
				// On est parti pour redémarrer le job.
				theTimer.cancel();

				CommonEngine.LOGGER.fatal(anException, anException);
				// Pause de mNbAttempts * 3 secondes.
				mNbAttempts++;
				if (mNbAttempts > CommonEngine.MAX_ATTEMPTS) {
					synchronized (this.mTimerTask) {
						this.failed++;
						this.rFailed++;
					}
					inJob.setStatus("failed " + (mNbAttempts - 1) + " attempt(s), no more go");
					restartProcess(inJob);
					throw anException;
				}
				inJob.setStatus("failed " + (mNbAttempts - 1) + " attempt(s), will try again");

				Thread.sleep(mNbAttempts * CommonEngine.WAIT_INCR_PER_ATTEMPT);
			}
		}

		synchronized (this.mTimerTask) {
			this.workTime += System.currentTimeMillis() - startTime;
			CommonEngine.LOGGER.info("TIME SPENT TO PROCESS : " + inJob.getText() + " : " + (System.currentTimeMillis() - startTime) + " ms");
			this.rWorkTime += System.currentTimeMillis() - startTime;
		}

		synchronized (this.mProcesses) {
			// process finished
			this.mProcesses.remove(inJob);
			this.mAvailableProcesses.put(theProcess);
		}

		return theResult;
	}

	/**
	 * Converts a String into Hexa given a charset
	 * 
	 * @param inString
	 * @param charset
	 * @return the Hexa String or an empty String in case something went wrong
	 */
	private String hexstr(String inString) {
		final StringBuilder theResultBuffer = new StringBuilder();
		try {
			final byte[] byteChaine = inString.getBytes("UTF-8");
			for (final byte byteTmp : byteChaine) {
				final int theByteVal;
				if (byteTmp < 0) {
					theByteVal = 256 + byteTmp;
				} else {
					theByteVal = byteTmp;
				}
				// hop on récupère l'hexa et on ne garde que la partie qui nous
				// est utile
				final String theByteAsStr = Integer.toHexString(theByteVal);
				if (theByteAsStr.length() < 2) {
					theResultBuffer.append("0");
				}
				theResultBuffer.append(theByteAsStr);
			}
		} catch (final UnsupportedEncodingException e) {
			CommonEngine.LOGGER.fatal(e, e);
		}
		return theResultBuffer.toString();
	}

	/**
	 * Creates a new process
	 * 
	 * @return the new process
	 */
	protected Process createProcess() {
		final String cmd = getCommand() + net.violet.common.StringShop.SPACE + getHost();
		Process theProcess = null;
		try {
			if (getEnvp() == null) {
				theProcess = Runtime.getRuntime().exec(cmd);
			} else {
				theProcess = Runtime.getRuntime().exec(cmd, getEnvp());
			}

		} catch (final IOException anException) {
			CommonEngine.LOGGER.fatal(anException, anException);
		}
		return theProcess;
	}

	private static final byte[] QUIT = "QUIT".getBytes();

	/**
	 * Crée un nouveau process pour le job inJob.
	 * 
	 * @param inJob job concerné.
	 * @return le nouveau process pour ce job.
	 */
	private Process restartJob(TTSJob inJob) {
		restartProcess(inJob);

		// On récupère un nouveau process pour le job concerné.
		Process theNewProcess = null;
		try {
			theNewProcess = this.mAvailableProcesses.take();
			synchronized (this.mProcesses) {
				this.mProcesses.put(inJob, theNewProcess);
			}
		} catch (final InterruptedException e) {
			CommonEngine.LOGGER.fatal(e, e);
		}

		return theNewProcess;
	}

	private byte[] sendSpeakCommand(Process inProcess, String inCmd) throws IOException {
		CommonEngine.LOGGER.info("[" + inProcess.hashCode() + "]<< " + inCmd);
		final byte[] theCmd = (inCmd + "\n").getBytes();
		final OutputStream theStream = inProcess.getOutputStream();
		try {
			theStream.write(theCmd);
			theStream.flush();
		} catch (final IOException e) {
			try {
				if (theStream != null) {
					theStream.close();
				}
			} catch (final IOException anotherException) {
				// IGNORE
			}
			throw e;
		}

		final byte[] buffer;
		InputStream theInputStream = null;
		try {
			theInputStream = inProcess.getInputStream();
			final String TTSResponse = readLine(theInputStream);
			CommonEngine.LOGGER.info("[" + inProcess.hashCode() + "]>> " + TTSResponse);
			final Matcher theDataMatcher = CommonEngine.DATA_REGEX.matcher(TTSResponse);
			if (theDataMatcher.matches()) {
				final int amount2Read = Integer.parseInt(theDataMatcher.group(1));
				CommonEngine.LOGGER.info("[" + inProcess.hashCode() + "] amount2Read = " + amount2Read);
				// Lecture des données.
				buffer = StreamTools.readBytes(theInputStream, amount2Read);

				if (buffer == null) {
					throw new EOFException("EOF while reading data");
				}
			} else {
				final Matcher theErrorMatcher = CommonEngine.ERROR_REGEX.matcher(TTSResponse);
				if (theErrorMatcher.matches()) {
					// Lever une exception.
					throwError(Integer.parseInt(theErrorMatcher.group(1)), theErrorMatcher.group(2));
				}

				throw new IOException("Unknown answer from subprocess: " + TTSResponse + " [" + Arrays.asList(TTSResponse.getBytes()) + "] | the cmd " + inCmd);
			}
		} catch (final IOException e) {
			IOUtils.closeQuietly(theInputStream);
			throw e;
		}

		return buffer;
	}

	/**
	 * Lève une exception qui correspond au code d'erreur, si celui-ci dénote
	 * une erreur.
	 * 
	 * @param errorCode code d'erreur
	 * @param inErrorMsg message envoyé par le driver.
	 * @throws IOException exception levée.
	 */
	protected abstract void throwError(int errorCode, String inErrorMsg) throws IOException;

	private String readLine(InputStream theInputStream) throws IOException {
		final StringBuilder theResult = new StringBuilder();
		int theByte;
		do {
			theByte = theInputStream.read();
			if (theByte == '\n') {
				break;
			}
			theResult.append((char) theByte);
		} while (theByte >= 0);

		return theResult.toString();
	}

	@Override
	public Object getStats() {
		final long theTime = System.currentTimeMillis() - this.time;
		long rate = 0;
		final StringBuilder theResult = new StringBuilder();
		synchronized (this.mTimerTask) {
			if (this.nbTTS != 0) {
				rate = this.workTime / this.nbTTS;
			}
			theResult.append("STATS : last call was ");
			theResult.append(theTime);
			theResult.append("ms ago - ");
			theResult.append(this.nbTTS);
			theResult.append(" TTS generated (");
			theResult.append(rate);
			theResult.append(" ms/TTS) - ");
			theResult.append(this.failed);
			theResult.append(" TTS failed");
			theResult.append("\nLAST 5 MINUTES : ");
			theResult.append(this.rNbTTSPast);
			theResult.append(" generated (");
			theResult.append(this.ratePast);
			theResult.append(" ms/TTS) - ");
			theResult.append(this.rFailedPast);
			theResult.append(" TTS failed");

			this.time = System.currentTimeMillis();
			this.nbTTS = 0;
			this.workTime = 0;
			this.failed = 0;
		}

		return theResult.toString();
	}
}
