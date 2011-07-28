package net.violet.common.utils.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class BlockingExecutorLight<T> {

	private static final Logger LOGGER = Logger.getLogger(BlockingExecutorLight.class);

	public interface Worker<T> {

		void process(T inUnit);
	}

	private final BlockingQueue<T> mBlockingQueue;
	private final Thread[] mThreadPool;
	private final Worker<T> mWorker;
	private boolean mQuit;

	/**
	 * Thread de traitement des VActionImpl
	 */
	private class JobRunner extends Thread {

		public JobRunner(String inService) {
			super(inService);
		}

		/**
		 * Méthode génère un TTS à partir du contenu passé en paramètre
		 */
		@Override
		public void run() {
			while (!BlockingExecutorLight.this.mQuit) {
				try {
					final T theUnit = take();
					BlockingExecutorLight.this.mWorker.process(theUnit);
				} catch (final Exception e) {
					BlockingExecutorLight.LOGGER.fatal(e, e);
				}
			}
		}
	}

	/**
	 * Creates a {@link BlockingQueue} of {@link Thread}s excecuting the given
	 * {@link Worker}. The excecution of the {@link Worker}s is not
	 * {@link Thread} safe (only 1 worker for nbThread {@link Thread}s)
	 * 
	 * @param inWorker the worker for this executor.
	 */
	public BlockingExecutorLight(int nbThread, Worker<T> inWorker, String inService) {
		this(nbThread, nbThread, inWorker, inService);
	}

	/**
	 * Creates a {@link BlockingQueue} of {@link Thread}s excecuting the given
	 * {@link Worker}. The excecution of the {@link Worker}s is not
	 * {@link Thread} safe (only 1 worker for nbThread {@link Thread}s)
	 * 
	 * @param nbThread the amount of concurrent threads
	 * @param nbElements the maximum amount of elements in the waiting list
	 * @param inWorker the worker for this executor.
	 * @param inService the name of the service using the
	 *            {@link BlockingExecutorLight}
	 */
	protected BlockingExecutorLight(int nbThread, int nbElements, Worker<T> inWorker, String inService) {
		this.mBlockingQueue = new LinkedBlockingQueue<T>();
		this.mThreadPool = new Thread[nbThread];
		this.mWorker = inWorker;

		for (int indexThr = 0; indexThr < nbThread; indexThr++) {
			final JobRunner theThread = new JobRunner(inService + "-" + indexThr);
			theThread.start();
			this.mThreadPool[indexThr] = theThread;
		}
	}

	public T take() throws InterruptedException {
		return this.mBlockingQueue.take();
	}

	public void put(T element) throws InterruptedException {
		this.mBlockingQueue.put(element);
	}

	public void quit() {
		this.mQuit = true;
		for (final Thread theThread : this.mThreadPool) {
			theThread.interrupt();
		}
	}
}
