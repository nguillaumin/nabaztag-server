package net.violet.probes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

import org.apache.log4j.Logger;

class SgbdConnectionProbe extends AbstractProbe implements SgbdConnectionProbeMBean {

	private static final Logger LOGGER = Logger.getLogger(SgbdConnectionProbe.class);

	private static final String GROUP1 = "SgbdConnectionStats";
	private static final String GROUP2 = "QueryStats";

	/**
	 * Nombre de connexions ouvertes.
	 */
	@CompositeDataField(description = "The amount of SGBD connections open", group = SgbdConnectionProbe.GROUP1)
	protected int gNbConnections;

	/**
	 * Nombre maximum de connexions ouvertes.
	 */
	@CompositeDataField(description = "The amount of SGBD connections open max at once", group = SgbdConnectionProbe.GROUP1)
	protected long gMaxNbConnections;

	@CompositeDataField(description = "The query done since the last call of getQueryStats()", group = SgbdConnectionProbe.GROUP2)
	protected long queryCounter;

	/**
	 * Average time spent querying
	 */
	@CompositeDataField(description = "Average time spent querying", group = SgbdConnectionProbe.GROUP2)
	protected long avgQueryTime;

	protected SgbdConnectionProbe() throws OpenDataException {
		final Map<String, String> theMap = new HashMap<String, String>();

		theMap.put(SgbdConnectionProbe.GROUP1, "The Composite type for the SgbdConnection probe");
		theMap.put(SgbdConnectionProbe.GROUP2, "The Composite type for the Query probe");

		initGroups(Collections.unmodifiableMap(theMap));
	}

	/**
	 * Adds a connection to the amount of opened connections
	 */
	public synchronized void addConnection() {
		if (++this.gNbConnections > this.gMaxNbConnections) {
			this.gMaxNbConnections = this.gNbConnections;
		}
	}

	/**
	 * Removes a connection to the amount of opened connections
	 */
	public synchronized void remove() {
		this.gNbConnections--;
	}

	/**
	 * Register a slow query to the slow query tracker
	 * 
	 * @param inTimeSpent
	 * @param inQuery
	 * @param inVals
	 */
	public synchronized void registerQuery(long inTimeSpent, String inQuery, List<Object> inVals) {
		this.queryCounter++;
		this.avgQueryTime += inTimeSpent;

		if (inTimeSpent > 500L) {
			SgbdConnectionProbe.LOGGER.info("SLOW QUERY " + inTimeSpent + " : " + inQuery + " " + inVals);
		}
	}

	public CompositeData getConnectionsStats() throws OpenDataException {
		return getStat(SgbdConnectionProbe.GROUP1);
	}

	public synchronized CompositeData getQueryStats() throws OpenDataException {
		if (this.queryCounter != 0L) {
			this.avgQueryTime /= this.queryCounter;
		}
		final CompositeData theCompositeData = getStat(SgbdConnectionProbe.GROUP2);
		this.queryCounter = 0L;
		this.avgQueryTime = 0L;
		return theCompositeData;
	}

}
