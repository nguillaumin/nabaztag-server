package net.violet.common.utils.concurrent;

import net.violet.common.utils.concurrent.units.TimedProcessUnit;

public class TimedBlockingExecutor<T extends TimedProcessUnit> extends BlockingExecutor<T> {

	public TimedBlockingExecutor(int nbThread, int nbElements, Worker<T> inWorker, String inService) {
		super(nbThread, nbElements, inWorker, inService);
	}

	@Override
	public T take() throws InterruptedException {
		final T theProcessUnit = super.take();
		Thread.sleep(theProcessUnit.getRemainingTime2Wait());
		return theProcessUnit;
	}

}
