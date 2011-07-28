package net.violet.common.utils.concurrent;


public class BlockingExecutor<T> extends BlockingExecutorLight<T> {

	private int mQueueSize;
	private final Object mQueueMutex = new Object();

	public BlockingExecutor(int nbThread, int nbElements, Worker<T> inWorker, String inService) {
		super(nbThread, nbElements, inWorker, inService);
	}

	public BlockingExecutor(int nbThread, Worker<T> inWorker, String inService) {
		super(nbThread, nbThread, inWorker, inService);
	}

	@Override
	public T take() throws InterruptedException {
		final T element = super.take();
		synchronized (this.mQueueMutex) {
			this.mQueueSize--;
			if (this.mQueueSize == 0) {
				this.mQueueMutex.notify();
			}
		}
		return element;
	}

	@Override
	public void put(T element) throws InterruptedException {
		synchronized (this.mQueueMutex) {
			this.mQueueSize++;
		}
		super.put(element);
	}

	public void waitTermination() throws InterruptedException {
		synchronized (this.mQueueMutex) {
			if (this.mQueueSize != 0) {
				this.mQueueMutex.wait();
			}
		}
	}

	@Override
	public void quit() {
		super.quit();
	}
}
