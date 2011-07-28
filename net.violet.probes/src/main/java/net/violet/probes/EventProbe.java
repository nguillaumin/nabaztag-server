package net.violet.probes;

import java.util.Collections;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

import org.apache.log4j.Logger;

class EventProbe extends AbstractProbe implements EventProbeMBean {


	private static final Logger LOGGER = Logger.getLogger(EventProbe.class);

	private static final String GROUP = "EventPingStats";

	@CompositeDataField(description = "The amount of time spent to solve", group = EventProbe.GROUP)
	protected long gSolveTimeSum;
	@CompositeDataField(description = "The amount of time solve has been called", group = EventProbe.GROUP)
	protected int gSolveTimeCount;
	@CompositeDataField(description = "The amount of time max spent to solve", group = EventProbe.GROUP)
	protected long gSolveTimeMax;
	@CompositeDataField(description = "The amount of time max spent waiting while solving", group = EventProbe.GROUP)
	protected long gWaitTimeMax;

	protected EventProbe() throws OpenDataException {
		initGroups(Collections.singletonMap(EventProbe.GROUP, "The Composite type for the EventPing probe"));
	}

	public void solve(long inDuration, long inWait) {
		long sum = 0;
		long max = 0;
		long maxwait = 0;
		synchronized (this) {
			this.gSolveTimeCount++;
			this.gSolveTimeSum += inDuration;
			if (this.gWaitTimeMax < inWait) {
				this.gWaitTimeMax = inWait;
			}
			if (this.gSolveTimeMax < inDuration) {
				this.gSolveTimeMax = inDuration;
			}
			if (this.gSolveTimeCount == 1024) {
				sum = this.gSolveTimeSum;
				max = this.gSolveTimeMax;
				maxwait = this.gWaitTimeMax;
				this.gSolveTimeCount = 0;
				this.gSolveTimeSum = 0;
				this.gSolveTimeMax = 0;
				this.gWaitTimeMax = 0;
			}
		}
		if (sum != 0) {
			EventProbe.LOGGER.info("PINGv1 AVERAGE : " + (sum / 1024) + " MAX : " + max + " MAXWAIT : " + maxwait);
		}

	}

	public CompositeData getEventPingStats() throws OpenDataException {
		return getStat(EventProbe.GROUP);
	}

}
