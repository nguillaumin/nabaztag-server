package net.violet.platform.ping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.db.records.SgbdConnection;
import net.violet.platform.management.ThreadWithLog;

import org.apache.log4j.Logger;

public final class GroupUpdate {

	private static final Logger LOGGER = Logger.getLogger(GroupUpdate.class);

	private static final int MAX_REQUESTS = 1200;
	private static final int WRITE_DELAY = 5000;
	private static final String PING_UPDATE_STATEMENT = "update object set object_lastping = ? where object_id = ?";
	private static Long timeOut = 0l;
	private static Map<Integer, Long> requests = new HashMap<Integer, Long>();

	// private static Map<String, Map<Integer, Long>> requests = new
	// HashMap<String, Map<Integer, Long>>();

	public static void setTimeOut() {
		GroupUpdate.timeOut = System.currentTimeMillis();
	}

	public static void update(long id) {
		synchronized (GroupUpdate.requests) {
			GroupUpdate.requests.put((int) id, System.currentTimeMillis() / 1000);
			if ((System.currentTimeMillis() - GroupUpdate.timeOut > GroupUpdate.WRITE_DELAY) || (GroupUpdate.requests.size() >= GroupUpdate.MAX_REQUESTS)) {
				GroupUpdate.setTimeOut();
				final Map<Integer, Long> theRequests = GroupUpdate.requests;
				GroupUpdate.requests = new HashMap<Integer, Long>();
				new ThreadWithLog() {

					@Override
					public void doRun() {
						GroupUpdate.updateRecords(theRequests);
					}
				}.start();
			}
		}
	}

	/**
	 * Ecriture des enregistrements.
	 */
	private static void updateRecords(Map<Integer, Long> inRecords) {
		final SgbdConnection connection_tmp = new SgbdConnection(SgbdConnection.SGBD_ACCESS.WRITEONLY);
		try {
			final List<Object> vals = new ArrayList<Object>();
			for (final Integer key : inRecords.keySet()) {
				vals.add(inRecords.get(key));
				vals.add(key);
				connection_tmp.doQueryUpdatePS(GroupUpdate.PING_UPDATE_STATEMENT, vals);
				vals.clear();
			}
		} catch (final SQLException e) {
			GroupUpdate.LOGGER.fatal(e, e);
		} finally {
			connection_tmp.close();
		}
	}
}
