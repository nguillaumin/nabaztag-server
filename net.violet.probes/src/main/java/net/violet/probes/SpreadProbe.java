package net.violet.probes;

import java.util.Collections;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

class SpreadProbe extends AbstractProbe implements SpreadProbeMBean {

	private static final String GROUP = "MessageStats";

	@CompositeDataField(description = "The amount of messages pending", group = SpreadProbe.GROUP)
	protected int pendingMessages;
	@CompositeDataField(description = "The amount of messages processed", group = SpreadProbe.GROUP)
	protected int processedMessages;
	@CompositeDataField(description = "The amount of messages received", group = SpreadProbe.GROUP)
	protected int receivedMessages;

	protected SpreadProbe() throws OpenDataException {
		initGroups(Collections.singletonMap(SpreadProbe.GROUP, "The Composite type for the Spread probe"));
	}

	public synchronized void addProcessedMessage() {
		this.pendingMessages--;
		this.processedMessages++;
	}

	public synchronized void addReceivedMessage() {
		this.pendingMessages++;
		this.receivedMessages++;
	}

	public CompositeData getMessagesStats() throws OpenDataException {
		final CompositeData theResult = getStat(SpreadProbe.GROUP);
		this.processedMessages = 0;
		this.receivedMessages = 0;
		return theResult;
	}
}
