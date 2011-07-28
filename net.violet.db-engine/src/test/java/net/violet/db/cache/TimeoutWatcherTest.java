package net.violet.db.cache;

import junit.framework.Assert;

import org.junit.Test;

public class TimeoutWatcherTest {

	@Test
	public void testNoUpdate() throws InterruptedException {
		final int[] count = new int[1];
		count[0] = 0;
		final TimeoutWatcher theWatcher = new TimeoutWatcher(500) {

			@Override
			public void timeoutHandling() {
				count[0]++;
			}
		};
		try {
			theWatcher.start();
			Thread.sleep(550);
			Assert.assertEquals(1, count[0]);
		} finally {
			theWatcher.quit();
		}
	}

	@Test
	public void testUpdate() throws InterruptedException {
		final int[] count = new int[1];
		count[0] = 0;
		final TimeoutWatcher theWatcher = new TimeoutWatcher(500) {

			@Override
			public void timeoutHandling() {
				count[0]++;
			}
		};
		try {
			theWatcher.start();
			Thread.sleep(250);
			theWatcher.update();
			Thread.sleep(300);
			Assert.assertEquals(0, count[0]);
		} finally {
			theWatcher.quit();
		}
	}

	@Test
	public void testUpdateNoUpdate() throws InterruptedException {
		final int[] count = new int[1];
		count[0] = 0;
		final TimeoutWatcher theWatcher = new TimeoutWatcher(500) {

			@Override
			public void timeoutHandling() {
				count[0]++;
			}
		};
		try {
			theWatcher.start();
			Thread.sleep(250);
			theWatcher.update();
			Assert.assertEquals(0, count[0]);
			Thread.sleep(300);
			Assert.assertEquals(0, count[0]);
			Thread.sleep(250);
			Assert.assertEquals(1, count[0]);
		} finally {
			theWatcher.quit();
		}
	}
}
