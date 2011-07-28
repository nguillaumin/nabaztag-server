package net.violet.probes;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public interface SgbdConnectionProbeMBean {

	CompositeData getConnectionsStats() throws OpenDataException;

	CompositeData getQueryStats() throws OpenDataException;
}
