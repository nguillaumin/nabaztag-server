package net.violet.probes;

import java.util.HashMap;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

class DownloadProbe extends AbstractProbe implements DownloadProbeMBean {

	private static final String GROUP_FREE = "DownloadStatsFree";
	private static final String GROUP_FULL = "DownloadStatsFull";

	@CompositeDataField(description = "The amount of downloas pending", group = DownloadProbe.GROUP_FREE)
	protected int pendingDownloadsFree;
	@CompositeDataField(description = "The amount of download finished", group = DownloadProbe.GROUP_FREE)
	protected int processedDownloadsFree;
	@CompositeDataField(description = "The amount of downloads running", group = DownloadProbe.GROUP_FREE)
	protected int processingDownloadsFree;

	@CompositeDataField(description = "The amount of downloas pending", group = DownloadProbe.GROUP_FULL)
	protected int pendingDownloadsFull;
	@CompositeDataField(description = "The amount of download finished", group = DownloadProbe.GROUP_FULL)
	protected int processedDownloadsFull;
	@CompositeDataField(description = "The amount of downloads running", group = DownloadProbe.GROUP_FULL)
	protected int processingDownloadsFull;

	protected DownloadProbe() throws OpenDataException {
		final Map<String, String> theMap = new HashMap<String, String>();
		theMap.put(DownloadProbe.GROUP_FREE, "The Composite type for the download probe (FREE)");
		theMap.put(DownloadProbe.GROUP_FULL, "The Composite type for the download probe (FULL)");
		initGroups(theMap);
	}

	public synchronized void addProcessing(boolean isFree) {
		if (isFree) {
			this.processingDownloadsFree++;
			this.pendingDownloadsFree--;
		} else {
			this.processingDownloadsFull++;
			this.pendingDownloadsFull--;
		}
	}

	public synchronized void addProcessed(boolean isFree) {
		if (isFree) {
			this.processedDownloadsFree++;
			this.processingDownloadsFree--;
		} else {
			this.processedDownloadsFull++;
			this.processingDownloadsFull--;
		}
	}

	public synchronized void addNewDownload(boolean isFree) {
		if (isFree) {
			this.pendingDownloadsFree++;
		} else {
			this.pendingDownloadsFull++;
		}
	}

	public synchronized CompositeData getDownloadStatsFree() throws OpenDataException {
		final CompositeData theResult = getStat(DownloadProbe.GROUP_FREE);
		this.processedDownloadsFree = 0;
		return theResult;
	}

	public synchronized CompositeData getDownloadStatsFull() throws OpenDataException {
		final CompositeData theResult = getStat(DownloadProbe.GROUP_FULL);
		this.processedDownloadsFull = 0;
		return theResult;
	}
}
