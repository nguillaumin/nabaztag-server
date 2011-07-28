package net.violet.probes;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.openmbean.OpenDataException;

import org.apache.log4j.Logger;

public class SgbdConnectionProbeHandler extends ProbesHandler {


	private static final Logger LOGGER = Logger.getLogger(SgbdConnectionProbeHandler.class);

	private final SgbdConnectionProbe mProbeInstance;
	private final ObjectName mObjectName;

	protected SgbdConnectionProbeHandler() {
		SgbdConnectionProbe theProbe = null;
		try {
			theProbe = new SgbdConnectionProbe();
		} catch (final OpenDataException e) {
			SgbdConnectionProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mProbeInstance = theProbe;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName mxbeanName = null;
		try {
			mxbeanName = new ObjectName("net.violet.admin.probes:type=SgbdConnectionProbe");
			mbs.registerMBean(this.mProbeInstance, mxbeanName);
		} catch (final MalformedObjectNameException e) {
			SgbdConnectionProbeHandler.LOGGER.fatal(e, e);
		} catch (final NullPointerException e) {
			SgbdConnectionProbeHandler.LOGGER.fatal(e, e);
		} catch (final InstanceAlreadyExistsException e) {
			SgbdConnectionProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			SgbdConnectionProbeHandler.LOGGER.fatal(e, e);
		} catch (final NotCompliantMBeanException e) {
			SgbdConnectionProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mObjectName = mxbeanName;
		}

	}

	public void add() {
		this.mProbeInstance.addConnection();
	}

	public void remove() {
		this.mProbeInstance.remove();
	}

	public void registerQuery(long inTimeSpent, String inQuery, List<Object> inVals) {
		this.mProbeInstance.registerQuery(inTimeSpent, inQuery, inVals);
	}

	@Override
	protected void shutdown() {
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			mbs.unregisterMBean(this.mObjectName);
		} catch (final InstanceNotFoundException e) {
			SgbdConnectionProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			SgbdConnectionProbeHandler.LOGGER.fatal(e, e);
		}
	}
}
