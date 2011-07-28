package net.violet.platform.voice.server;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import net.violet.common.utils.io.StreamTools;

import org.apache.log4j.Logger;

/**
 * This class provides several methods to launch tests on a TTSServer. About
 * test files format : - a line is a test, you can't include \n in your test
 * text - line format is :
 * voiceName<tabulation>languageAbbrev<tabulation>yourText<\n> - the test text
 * can contain tabulation
 */
public final class TTSServerCtl {

	private static final Logger LOGGER = Logger.getLogger(TTSServerCtl.class);

	private final TTSClient mTTSClient;

	private TTSServerCtl(String inHost) {
		this.mTTSClient = new TTSClient(inHost);
	}

	public static void main(String[] args) {

		if ((args.length > 0) && args[0].equals("--help")) {
			TTSServerCtl.printHelp();
			return;
		}

		// Analyse des paramètres
		final LongOpt[] theLongOpts = new LongOpt[] { new LongOpt("host", LongOpt.OPTIONAL_ARGUMENT, null, 'h'), new LongOpt("priority", LongOpt.OPTIONAL_ARGUMENT, null, 'p'), new LongOpt("output", LongOpt.OPTIONAL_ARGUMENT, null, 'o'), new LongOpt("frequency", LongOpt.OPTIONAL_ARGUMENT, null, 'f'), new LongOpt("encoding", LongOpt.OPTIONAL_ARGUMENT, null, 'e'), };
		final Getopt theGetOpt = new Getopt(TTSServerCtl.class.getSimpleName(), args, "h:p:o:f:", theLongOpts);
		int theOption;

		int priority = 0;
		String output = null;
		int frequency = 2;
		String host = "localhost";// by default we try to connect on localhost
		String encoding = "UTF-8";
		int nbrThreadPool = 20;

		while ((theOption = theGetOpt.getopt()) != -1) {
			switch (theOption) {
			case 'h':
				host = theGetOpt.getOptarg();
				break;

			case 'p':
				priority = Integer.parseInt(theGetOpt.getOptarg());
				break;

			case 'o':
				output = theGetOpt.getOptarg();
				break;

			case 'f':
				frequency = Integer.parseInt(theGetOpt.getOptarg());
				break;

			case 'e':
				encoding = theGetOpt.getOptarg();
				break;

			case 't':
				nbrThreadPool = Integer.parseInt(theGetOpt.getOptarg());
				break;

			default:
				TTSServerCtl.LOGGER.debug("getopt() -> " + theOption + "\n");
			}
		}

		final int nbArgs = args.length;
		final int commandIx = theGetOpt.getOptind();
		if (commandIx >= nbArgs) {
			TTSServerCtl.syntaxError();
		} else {
			final TTSServerCtl theCtl = new TTSServerCtl(host);

			final String theCommand = args[commandIx];
			if (theCommand.equals("tts")) {
				if (nbArgs - commandIx != 4) {
					TTSServerCtl.syntaxError();
				} else {
					final String theVoice = args[commandIx + 1];
					final String theLang = args[commandIx + 2];
					final String theText = args[commandIx + 3];
					theCtl.generateTTS(theVoice, theLang, theText, priority, output);
				}
			} else if (theCommand.equals("list")) {
				if (nbArgs - commandIx != 1) {
					TTSServerCtl.syntaxError();
				} else {
					theCtl.list();
				}
			} else if (theCommand.equals("size")) {
				if (nbArgs - commandIx != 1) {
					TTSServerCtl.syntaxError();
				} else {
					theCtl.listSize();
				}
			} else if (theCommand.equals("reloadxml")) {
				if (nbArgs - commandIx != 1) {
					TTSServerCtl.syntaxError();
				} else {
					theCtl.reloadXml();
				}
			} else if (theCommand.equals("reloaddrivers")) {
				if (nbArgs - commandIx != 1) {
					TTSServerCtl.syntaxError();
				} else {
					theCtl.reloadDrivers();
				}
			} else if (theCommand.equals("kill")) {
				if (nbArgs - commandIx != 2) {
					TTSServerCtl.syntaxError();
				} else {
					theCtl.kill(Integer.parseInt(args[commandIx + 1]));
				}
			} else if (theCommand.equals("abort")) {
				if (nbArgs - commandIx != 2) {
					TTSServerCtl.syntaxError();
				} else {
					theCtl.abort(Integer.parseInt(args[commandIx + 1]));
				}
			} else if (theCommand.equals("loadtest")) {
				if (nbArgs - commandIx != 2) {
					TTSServerCtl.syntaxError();
				} else {
					theCtl.loadTest(args[commandIx + 1], frequency, encoding, nbrThreadPool);
				}
			} else if (theCommand.equals("voicelist")) {
				if (nbArgs - commandIx != 2) {
					TTSServerCtl.syntaxError();
				} else {
					theCtl.voiceList(args[commandIx + 1]);
				}
			} else if (theCommand.equals("voicetest")) {
				if (nbArgs - commandIx != 2) {
					TTSServerCtl.syntaxError();
				} else {
					theCtl.voiceTest(args[commandIx + 1]);
				}
			} else if (theCommand.equals("stats")) {
				if (nbArgs - commandIx != 2) {
					TTSServerCtl.syntaxError();
				} else {
					theCtl.stats(args[commandIx + 1]);
				}
			} else {
				System.err.println("Unknown command : " + theCommand);
				TTSServerCtl.printHelp();
			}
		}
	}

	private void voiceList(String engineName) {
		final List<Map<String, String>> theVoices = this.mTTSClient.getVoiceList(engineName);
		for (final Map<String, String> theVoice : theVoices) {
			System.out.println(theVoice.get(TTSServerImpl.VOICE_NAME_KEY) + " (" + theVoice.get(TTSServerImpl.VOICE_LANG_KEY) + ")");
		}
	}

	private void voiceTest(String engineName) {
		final List<Map<String, String>> theVoices = this.mTTSClient.getVoiceList(engineName);
		final Map<String, String> theSampleTexts = new HashMap<String, String>();

		for (final Map<String, String> theVoiceData : theVoices) {
			try {
				final String theVoiceName = theVoiceData.get(TTSServerImpl.VOICE_NAME_KEY);
				final String theOutput = theVoiceName + "-sample.pcm";
				final String theLang = theVoiceData.get(TTSServerImpl.VOICE_LANG_KEY);
				String theText = theSampleTexts.get(theLang);
				if (theText == null) {
					final BufferedReader theReader = new BufferedReader(new FileReader("sample-" + theLang + ".txt"));
					final StringBuilder theStringBuilder = new StringBuilder();
					do {
						final String theLine = theReader.readLine();
						if (theLine == null) {
							break;
						}
						theStringBuilder.append(theLine);
					} while (true);
					theText = theStringBuilder.toString();
					theSampleTexts.put(theLang, theText);
				}
				generateTTS(theVoiceName, theLang, theText, 0, theOutput);
			} catch (final IOException anException) {
				System.err.println("Exception : " + anException.getMessage());
			}
		}
	}

	private void stats(String engineName) {
		final String statsRes = this.mTTSClient.getEngineStats(engineName);
		System.out.println(statsRes);
	}

	private static void syntaxError() {
		System.err.println("Syntax error: missing command.");
		TTSServerCtl.printHelp();
	}

	private static void printHelp() {
		System.out.println("TTSServerCtl [options] command params...");
		System.out.println("Common options : ");
		System.out.println("-h hostname : optional option, indicates the ip or server name where the TTSServer is running");
		System.out.println();
		System.out.println("Commands : ");
		System.out.println("list                       list current jobs");
		System.out.println();
		System.out.println("size                       count the number of current jobs");
		System.out.println();
		System.out.println("reloadxml                  reload ttsserver.xml");
		System.out.println();
		System.out.println("reloaddrivers              reload drivers");
		System.out.println();
		System.out.println("tts <voice> <lang> <text>  generate a tts");
		System.out.println("-p priority : the priority, available values are 0 and 1 (default: 0)");
		System.out.println("-o output file : the file to use to store generated tts data (default: stdout)");
		System.out.println();
		System.out.println("kill <jobid>               kill a job");
		System.out.println();
		System.out.println("abort <jobid>              abort a job");
		System.out.println();
		System.out.println("loadtest <file>            start a load test");
		System.out.println("-f number of syntheses per second (default: 2)");
		System.out.println("-e charset : the charset used to encode the provided test file (default: UTF-8)");
		System.out.println("-t number of thread pool (default: 20)");
		System.out.println();
		System.out.println("stats <engine>             print engine statistics.");
		System.out.println();
		System.out.println("voicelist <engine>         print the list of voices for a given engine.");
		System.out.println();
		System.out.println("voicetest <engine>         run a test for all voices of a given engine.");
		System.out.println();
	}

	private void generateTTS(String inVoiceName, String inLangAbbr, String inText, int inPriority, String inOutputFilename) {
		final Map<String, Object> res;
		try {
			res = this.mTTSClient.generateTTS(inVoiceName, inLangAbbr, inText, inPriority);

			PrintStream theOutputStream = null;
			try {
				if (inOutputFilename != null) {
					theOutputStream = new PrintStream(new FileOutputStream(inOutputFilename));
				} else {
					theOutputStream = System.out;
				}
				final byte[] content = (byte[]) res.get(TTSServerImpl.DATA);
				theOutputStream.write(content);
				theOutputStream.flush();
			} catch (final FileNotFoundException e) {
				System.err.println("Couldn't open specified file : " + inOutputFilename);
			} catch (final IOException e) {
				System.err.println("Couldn't saved generated tts into file " + inOutputFilename);
			} finally {
				if (theOutputStream != null) {
					theOutputStream.close();
				}
			}
		} catch (final Exception e) {
			System.err.println("Exception : " + e.getMessage());
		}
	}

	private void abort(int id) {
		if (this.mTTSClient.cancelJob(id)) {
			System.out.println("Job " + id + " aborted");
		} else {
			System.out.println("Couldn't abort job " + id);
		}

	}

	private void kill(int id) {
		if (this.mTTSClient.killJob(id)) {
			System.out.println("Job " + id + " killed");
		} else {
			System.out.println("Couldn't kill job " + id);
		}
	}

	private void reloadXml() {
		if (this.mTTSClient.reloadXml()) {
			System.out.println("ttsserver.xml reloaded");
		} else {
			System.out.println("Couldn't reload ttsserver.xml");
		}
	}

	private void reloadDrivers() {
		if (this.mTTSClient.reloadDrivers()) {
			System.out.println("drivers reloaded");
		} else {
			System.out.println("Couldn't reload drivers");
		}
	}

	private void list() {
		final Object[] jobs = this.mTTSClient.getCurrentJobs();
		if ((jobs == null) || (jobs.length == 0)) {
			System.out.println("There is no current jobs");
		} else {
			System.out.println(jobs.length + " current job(s) : ");
			for (final Object job : jobs) {
				final Map<String, String> jobInfo = (Map<String, String>) job;
				System.out.println(jobInfo.get("mJobIsCurrent") + jobInfo.get("mJobID") + "- " + jobInfo.get("mJobStatus") + " - \t" + jobInfo.get("mJobProcessName") + net.violet.common.StringShop.SPACE + jobInfo.get("mJobVoiceName") + "(" + jobInfo.get("mJobVoiceLang") + ")" + net.violet.common.StringShop.SPACE + jobInfo.get("mJobText"));
			}
		}
	}

	private void listSize() {
		System.out.println(this.mTTSClient.getSize());
	}

	private class RunGenerate implements Runnable {

		private final String voiceName;
		private final String lang;
		private final String text;
		private final int priority;

		public RunGenerate(String voiceName, String lang, String text, int priority) {
			this.voiceName = voiceName;
			this.lang = lang;
			this.text = text;
			this.priority = priority;
		}

		public void run() {
			try {
				TTSServerCtl.this.mTTSClient.generateTTS(this.voiceName, this.lang, this.text, this.priority);
			} catch (final Exception e) {
				System.err.println("Failed to generate tts : " + this.voiceName + net.violet.common.StringShop.SPACE + this.lang + net.violet.common.StringShop.SPACE + this.text + net.violet.common.StringShop.SPACE + this.priority);
			}
		}

	}

	private void loadTest(String testName, int frequency, String encoding, int nbrThreadPool) {
		try {
			final byte[] buffer = StreamTools.readBytes(testName);
			final String[] allLines = new String(buffer, encoding).split("\n");

			final Executor theThreadPool = Executors.newFixedThreadPool(nbrThreadPool);
			int lineIndex = 0;

			final long startTime = System.currentTimeMillis();
			System.out.println("Load test started at : " + Calendar.getInstance().getTime() + " (" + allLines.length + " tasks)");

			while (lineIndex < allLines.length) {
				final long before = System.currentTimeMillis();
				int endIndex = lineIndex + frequency;
				if (endIndex > allLines.length) {
					endIndex = allLines.length;
				}

				for (int i = lineIndex; i < endIndex; i++) {
					final String theLine = allLines[i];
					int index = 0;
					final String voiceName = theLine.substring(index, theLine.indexOf('\t'));
					index = theLine.indexOf('\t') + 1;
					final String lang = theLine.substring(index, theLine.indexOf('\t', index));
					index = theLine.indexOf('\t', index) + 1;
					final String theText = theLine.substring(index);
					theThreadPool.execute(new RunGenerate(voiceName, lang, theText, 0));
				}
				lineIndex = endIndex;
				final long after = System.currentTimeMillis();
				if (after - before < 1000) {
					Thread.sleep(1000 - after + before);
				} else {
					System.out.println("Warning : Send " + frequency + " tasks in " + (after - before) + " ms");
				}
			}

			final long completionTime = System.currentTimeMillis() - startTime;
			System.out.println("Test over - " + allLines.length + " tasks treated in " + completionTime + " ms (" + (completionTime / allLines.length) + " ms per task)");

		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

	}
}
