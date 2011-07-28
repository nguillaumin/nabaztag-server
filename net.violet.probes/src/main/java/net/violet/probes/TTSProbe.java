package net.violet.probes;

import java.util.HashMap;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

class TTSProbe extends AbstractProbe implements TTSProbeMBean {

	private static final String GROUP = "TTSStats";

	@CompositeDataField(description = "The amount of TTS pending", group = TTSProbe.GROUP)
	protected int pendingTTSs;
	@CompositeDataField(description = "The amount of TTS finished", group = TTSProbe.GROUP)
	protected int processedTTSs;
	@CompositeDataField(description = "The amount of TTS running", group = TTSProbe.GROUP)
	protected int processingTTSs;
	@CompositeDataField(description = "The amount of TTS failed", group = TTSProbe.GROUP)
	protected int failedTTSs;

	protected TTSProbe() throws OpenDataException {
		final Map<String, String> theMap = new HashMap<String, String>();
		theMap.put(TTSProbe.GROUP, "The Composite type for the TTS probe");
		initGroups(theMap);
	}

	public synchronized void addProcessing() {
		this.processingTTSs++;
		this.pendingTTSs--;
	}

	public synchronized void addProcessed() {
		this.processedTTSs++;
		this.processingTTSs--;
	}

	public synchronized void addFailed() {
		this.failedTTSs++;
		this.processingTTSs--;
	}

	public synchronized void addNewTTS() {
		this.pendingTTSs++;
	}

	public synchronized CompositeData getTTSStats() throws OpenDataException {
		final CompositeData theResult = getStat(TTSProbe.GROUP);
		this.processedTTSs = 0;
		this.failedTTSs = 0;
		return theResult;
	}
}
