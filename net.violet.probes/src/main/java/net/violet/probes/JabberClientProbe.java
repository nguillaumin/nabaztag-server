package net.violet.probes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public class JabberClientProbe extends AbstractProbe implements JabberClientProbeMBean {

	private static final String GROUP = "JabberClientStats";

	@CompositeDataField(description = "List of connected Jabber Clients", group = JabberClientProbe.GROUP)
	protected List<String> connectedJabberClients = new ArrayList<String>();

	protected JabberClientProbe() throws OpenDataException {
		final Map<String, String> theMap = new HashMap<String, String>();
		theMap.put(JabberClientProbe.GROUP, "The Composite type for the JabberClient probe");
		initGroups(theMap);
	}

	public synchronized void addConnectedClient(String inUserName) {
		this.connectedJabberClients.add(inUserName);
	}

	public synchronized void delConnectedClient(String inUserName) {
		this.connectedJabberClients.remove(inUserName);
	}

	public synchronized CompositeData getComponentStats() throws OpenDataException {
		final CompositeData theResult = getStat(JabberClientProbe.GROUP);
		return theResult;
	}

}
