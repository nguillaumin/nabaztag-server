package net.violet.db.cache;

import net.violet.db.utils.test.DBTest;

public class CacheTest extends DBTest {

//	@Test
//	public void testIdentity() {
//		final User theUser1 = Factories.USER.find(1);
//		final User theUser1Again = Factories.USER.find(1);
//		Assert.assertTrue(theUser1 != null);
//		Assert.assertTrue(theUser1 == theUser1Again);
//	}

//	@Test
//	public void testGC() throws InterruptedException {
//		final long[] theAddrHolder = new long[1];
//		final Thread theThread1 = new Thread() {
//
//			@Override
//			public void run() {
//				final User theUser1 = Factories.USER.find(1);
//				final long theUser1Addr = System.identityHashCode(theUser1);
//				theUser1.setPassword("NEWOPASS");
//				theAddrHolder[0] = theUser1Addr;
//				System.gc();
//			}
//		};
//		theThread1.start();
//
//		System.gc();
//		Thread.sleep(500);
//		System.gc();
//		Cache.clear();
//		Thread.sleep(500);
//		System.gc();
//
//		final User theUser1Again = Factories.USER.find(1);
//		final long theUser1AgainAddr = System.identityHashCode(theUser1Again);
//		Assert.assertTrue(theUser1AgainAddr > 0);
//		Assert.assertNotSame(theAddrHolder[0], theUser1AgainAddr);
//	}

}
