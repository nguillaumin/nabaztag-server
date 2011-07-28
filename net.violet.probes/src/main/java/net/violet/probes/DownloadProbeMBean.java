package net.violet.probes;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public interface DownloadProbeMBean {

	CompositeData getDownloadStatsFree() throws OpenDataException;

	CompositeData getDownloadStatsFull() throws OpenDataException;
}
