package net.violet.probes;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.openmbean.OpenDataException;

import org.apache.log4j.Logger;

public class EventProbeHandler extends ProbesHandler {


	private static final Logger LOGGER = Logger.getLogger(EventProbeHandler.class);

	private final EventProbe mProbeInstance;
	private final ObjectName mObjectName;

	protected EventProbeHandler() {
		EventProbe theProbe = null;
		try {
			theProbe = new EventProbe();
		} catch (final OpenDataException e) {
			EventProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mProbeInstance = theProbe;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName mxbeanName = null;
		try {
			mxbeanName = new ObjectName("net.violet.admin.probes:type=EventProbe");
			mbs.registerMBean(this.mProbeInstance, mxbeanName);
		} catch (final MalformedObjectNameException e) {
			EventProbeHandler.LOGGER.fatal(e, e);
		} catch (final NullPointerException e) {
			EventProbeHandler.LOGGER.fatal(e, e);
		} catch (final InstanceAlreadyExistsException e) {
			EventProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			EventProbeHandler.LOGGER.fatal(e, e);
		} catch (final NotCompliantMBeanException e) {
			EventProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mObjectName = mxbeanName;
		}

	}

	@Override
	protected void shutdown() {
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			mbs.unregisterMBean(this.mObjectName);
		} catch (final InstanceNotFoundException e) {
			EventProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			EventProbeHandler.LOGGER.fatal(e, e);
		}
	}

	public void solve(long duration, long wait) {
		this.mProbeInstance.solve(duration, wait);
	}

}
