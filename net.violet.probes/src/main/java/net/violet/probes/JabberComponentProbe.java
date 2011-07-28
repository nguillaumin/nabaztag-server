package net.violet.probes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public class JabberComponentProbe extends AbstractProbe implements JabberComponentProbeMBean {

	private static final String GROUP = "JabberComponentStats";

	@CompositeDataField(description = "List of connected Jabber Components", group = JabberComponentProbe.GROUP)
	protected List<String> connectedJabberComponents = new ArrayList<String>();

	protected JabberComponentProbe() throws OpenDataException {
		final Map<String, String> theMap = new HashMap<String, String>();
		theMap.put(JabberComponentProbe.GROUP, "The Composite type for the JabberComponent probe");
		initGroups(theMap);
	}

	public synchronized void addConnectedComponent(String inComponentName) {
		this.connectedJabberComponents.add(inComponentName);
	}

	public synchronized void delConnectedComponent(String inComponentName) {
		this.connectedJabberComponents.remove(inComponentName);
	}

	public synchronized CompositeData getComponentStats() throws OpenDataException {
		final CompositeData theResult = getStat(JabberComponentProbe.GROUP);
		return theResult;
	}

}
