package net.violet.platform.voice.server;

import java.util.Random;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.util.CCalendar;
import net.violet.platform.voice.server.TTSEngine.TTSJobQueue;
import net.violet.platform.voice.server.TTSJob.TTSJobPrioritized;

import org.junit.Assert;
import org.junit.Test;

public class TTSJobQueueTest extends MockTestBase {

	public class JobTester implements TTSJobPrioritized {

		private final CCalendar mCreationDate = new CCalendar(false);

		private final int priority;

		public JobTester(int inPriority) {
			this.priority = inPriority;
		}

		public CCalendar getCreationDate() {
			return this.mCreationDate;
		}

		public int getPriority() {
			return this.priority;
		}

	}

	@Test
	public void testOrdering() {
		final Random randomGenerator = new Random(6);
		final TTSJobQueue<JobTester> theQueue = new TTSJobQueue<JobTester>();

		for (int i = 0; i < 100; i++) {
			final int thePriority = randomGenerator.nextInt(15);
			final long now = System.currentTimeMillis();
			System.out.println("id = " + i + " p = " + thePriority + " time = " + now);
			theQueue.put(new JobTester(thePriority));
		}

		JobTester theJob2 = null;

		while (!theQueue.isEmpty()) {
			try {
				final JobTester theJob1 = theQueue.take();
				System.out.println("job -> p = " + theJob1.getPriority() + " time = " + theJob1.getCreationDate().getTimeInMillis());
				if (theJob2 != null) {
					Assert.assertTrue(theJob1.getPriority() <= theJob2.getPriority());
					if (theJob1.getPriority() == theJob2.getPriority()) {
						System.out.println("job1: " + theJob1.getCreationDate().getTimeInMillis());
						System.out.println("job2: " + theJob2.getCreationDate().getTimeInMillis());
						Assert.assertTrue(theJob1.getCreationDate().getTimeInMillis() <= theJob2.getCreationDate().getTimeInMillis());
					}
				}

				theJob2 = theJob1;

			} catch (final InterruptedException e) {
				Assert.assertTrue(false);
			}
		}
	}
}
