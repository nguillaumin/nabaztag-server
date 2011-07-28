package net.violet.platform.voice.server;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

import net.violet.platform.voice.server.TTSJob.TTSJobPrioritized;
import net.violet.platform.voice.server.TTSJob.TTSJobRef;
import net.violet.probes.ProbesHandler;

public abstract class TTSEngine {

	static class TTSJobQueue<T extends TTSJobPrioritized> extends PriorityBlockingQueue<T> {

		private static final int INTIAL_TTS_BLOCKING_QUEUE_SIZE = 5;

		public TTSJobQueue() {
			super(TTSJobQueue.INTIAL_TTS_BLOCKING_QUEUE_SIZE, new Comparator<T>() {

				public int compare(T o1, T o2) {
					if (o1.getPriority() == o2.getPriority()) {
						final long dateDiff = o2.getCreationDate().getTimeInMillis() - o1.getCreationDate().getTimeInMillis();

						if (dateDiff < 0) {
							return -1;
						}

						if (dateDiff > 0) {
							return 1;
						}
						return 0;
					}

					return o2.getPriority() - o1.getPriority();
				}

			});

		}
	}

	private int mMaxChannels;
	private String mCommand;
	private String mHost;
	private final String[] envp;
	private final List<JobThread> mThreadPool;
	private final Map<TTSJobRef, TTSJob> mCurrentlyProcessingJobs;
	private final Map<TTSJob, TTSJobRef> mJobs;
	private final TTSJobQueue<TTSJobRef> mJobQueue;

	/**
	 * Thread pour l'exécution.
	 */
	private class JobThread extends Thread {

		private boolean mQuit;

		public void quit() {
			this.mQuit = true;
			interrupt();
		}

		@Override
		public void run() {
			while (!this.mQuit) {
				try {
					final TTSJobRef theJobRef = TTSEngine.this.mJobQueue.take();
					ProbesHandler.TTS.addProcessing();
					final TTSJob theJob = theJobRef.getTTSJob();
					TTSEngine.this.mCurrentlyProcessingJobs.put(theJobRef, theJob);
					try {
						final Map<String, Object> theResult = doProcessJob(theJob);
						ProbesHandler.TTS.addProcessed();
						theJob.setResult(theResult);
					} catch (final Exception anException) {
						ProbesHandler.TTS.addFailed();
						theJob.setException(anException);
					}
					TTSEngine.this.mCurrentlyProcessingJobs.remove(theJobRef);
					TTSEngine.this.mJobs.remove(theJob);
				} catch (final InterruptedException anException) {
					// On sort (peut-être).
				}
			}
		}
	}

	/**
	 * Crée le moteur à partir de la config.
	 */
	public static TTSEngine createEngine(TTSEngineConfig inConfig) {
		final TTSEngine theResult;
		final String theEngineName = inConfig.getName();
		if (theEngineName.equals(AcapelaEngine.NAME)) {
			theResult = new AcapelaEngine(inConfig);
		} else if (theEngineName.equals(NuanceEngine.NAME)) {
			theResult = new NuanceEngine(inConfig);
		} else {
			throw new IllegalArgumentException("Unknown engine " + theEngineName);
		}
		return theResult;
	}

	/**
	 * Constructeur à partir de la configuration.
	 */
	protected TTSEngine(TTSEngineConfig inConfig) {
		this.mMaxChannels = inConfig.getMaxChannels();
		this.mCommand = inConfig.getCommand();
		this.mHost = inConfig.getHost();
		this.envp = inConfig.getEnvp();
		this.mThreadPool = new ArrayList<JobThread>(this.mMaxChannels);
		this.mCurrentlyProcessingJobs = new HashMap<TTSJobRef, TTSJob>(this.mMaxChannels);
		this.mJobs = new HashMap<TTSJob, TTSJobRef>();
		this.mJobQueue = new TTSJobQueue<TTSJobRef>();
		for (int indexThread = 0; indexThread < this.mMaxChannels; indexThread++) {
			final JobThread theThread = new JobThread();
			theThread.start();
			this.mThreadPool.add(theThread);
		}
	}

	/**
	 * Methode pour changer la configuration du moteur
	 */
	public void updateEngineConfig(TTSEngineConfig inConfig) {
		this.mCommand = inConfig.getCommand();
		this.mHost = inConfig.getHost();

		// on augmente le nombre de thread
		if (this.mMaxChannels < inConfig.getMaxChannels()) {
			final JobThread theThread = new JobThread();
			theThread.start();
			this.mThreadPool.add(theThread);
		}

		// on réduit le nombre de thread
		if (this.mMaxChannels > inConfig.getMaxChannels()) {
			final int numberOfThreadsToQuit = this.mMaxChannels - inConfig.getMaxChannels();
			for (int i = 0; i < numberOfThreadsToQuit; i++) {
				this.mThreadPool.get(i).quit();
			}
		}

		final int oldMaxChannels = this.mMaxChannels;
		this.mMaxChannels = inConfig.getMaxChannels();

		doCleanEngineConfig(oldMaxChannels, inConfig.getMaxChannels());
	}

	/**
	 * Methode pour que le moteur termine tous ces jobs en cours
	 */
	public void closeEngineConfig() {

		for (final JobThread theJobThread : this.mThreadPool) {
			theJobThread.quit();
		}
	}

	/**
	 * Methode pour que le moteur prennent en compte le nouveau driver
	 */
	public void updateDriverEngineConfig() {
		final int theMaxChannels = getMaxChannels();
		doCleanEngineConfig(theMaxChannels, theMaxChannels);
	}

	/**
	 * Méthode qui fait la synthèse vocale, en appelant le client natif.
	 * 
	 * @param theJob
	 * @return les données sour la forme d'un tableau d'octets.
	 */
	protected abstract Map<String, Object> doProcessJob(TTSJob theJob) throws Exception;

	/**
	 * Méthode qui fait interrompt la synthèse vocale, en tuant le client natif.
	 * 
	 * @param theJob
	 */
	protected abstract void doKillJob(TTSJob theJob);

	protected abstract void doCleanEngineConfig(int inOldMaxChannels, int inNewMaxChannels);

	public Object[] getCurrentJobs() {
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (final TTSJobRef ttsJobRef : this.mCurrentlyProcessingJobs.keySet()) {
			list.add(ttsJobRef.toMap(true));
		}
		return list.toArray();
	}

	public Object[] getAwaitingJobs() {
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		synchronized (this.mJobQueue) {
			for (final TTSJobRef ttsJobRef : this.mJobQueue) {
				list.add(ttsJobRef.toMap(false));
			}
		}
		return list.toArray();
	}

	/**
	 * Méthode qui supprime de la liste d'attente un job en cours.
	 * 
	 * @param theJob
	 * @return <code>true</code> si le job a été supprimé.
	 */
	public boolean cancelJob(long inJobRef) {
		boolean theResult = false;
		synchronized (this.mJobQueue) {
			final Iterator<TTSJobRef> theIterator = this.mJobQueue.iterator();
			while (theIterator.hasNext()) {
				final TTSJobRef theRef = theIterator.next();
				if (inJobRef == theRef.getJobID()) {
					theIterator.remove();
					theResult = true;
				}
			}
		}
		return theResult;
	}

	/**
	 * Méthode qui supprime de la liste d'attente un job en cours, ou tue le
	 * process s'il est en cours de synthèse.
	 * 
	 * @param theJob
	 * @return <code>true</code> si le job a été supprimé.
	 */
	public boolean killJob(long inJobRef) {
		boolean theResult = cancelJob(inJobRef);
		if (!theResult) {
			// Pas trouvé.
			for (final TTSJobRef theJobRef : this.mCurrentlyProcessingJobs.keySet()) {
				if (inJobRef == theJobRef.getJobID()) {
					final TTSJob theJob = this.mCurrentlyProcessingJobs.get(theJobRef);
					if (theJob != null) {
						doKillJob(theJob);
						theResult = true;
					}
				}
			}
		}
		return theResult;
	}

	/**
	 * Méthode synchrone pour faire la synthèse.
	 * 
	 * @param theTTSJob le job
	 * @return le son.
	 */
	public Map<String, Object> processJob(TTSJob inTTSJob) throws Exception {
		final TTSJobRef theRef = this.mJobs.get(inTTSJob);
		if (theRef != null) {
			// Linker le job en cours avec le waiting job.
			final TTSJob theOriginalJob = theRef.getTTSJob();
			theOriginalJob.linkJob(inTTSJob);
		} else {
			// On fait vraiment la synthèse.
			final TTSJobRef theJobRef = inTTSJob.getJobRef();
			ProbesHandler.TTS.addNewTTS();
			this.mJobQueue.put(theJobRef);
		}
		return inTTSJob.waitForResult();
	}

	/**
	 * Accesseur sur le chemin de la commande pour faire le TTS.
	 */
	protected final String getCommand() {
		return this.mCommand;
	}

	/**
	 * Accesseur sur le serveur de TTS.
	 */
	protected final String getHost() {
		return this.mHost;
	}

	/**
	 * Accesseur sur le max_channel du moteur.
	 */
	protected final int getMaxChannels() {
		return this.mMaxChannels;
	}

	public abstract Object getStats();

	/**
	 * @return the envp
	 */
	public String[] getEnvp() {
		return this.envp;
	}
}
