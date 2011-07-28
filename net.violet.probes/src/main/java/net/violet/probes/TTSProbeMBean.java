package net.violet.probes;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public interface TTSProbeMBean {

	CompositeData getTTSStats() throws OpenDataException;
}
