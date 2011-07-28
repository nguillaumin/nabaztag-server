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

public class SpreadProbeHandler extends ProbesHandler {


	private static final Logger LOGGER = Logger.getLogger(SpreadProbeHandler.class);

	private final SpreadProbe mProbeInstance;
	private final ObjectName mObjectName;

	protected SpreadProbeHandler() {
		SpreadProbe theProbe = null;
		try {
			theProbe = new SpreadProbe();
		} catch (final OpenDataException e) {
			SpreadProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mProbeInstance = theProbe;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName mxbeanName = null;
		try {
			mxbeanName = new ObjectName("net.violet.admin.probes:type=SpreadProbe");
			mbs.registerMBean(this.mProbeInstance, mxbeanName);
		} catch (final MalformedObjectNameException e) {
			SpreadProbeHandler.LOGGER.fatal(e, e);
		} catch (final NullPointerException e) {
			SpreadProbeHandler.LOGGER.fatal(e, e);
		} catch (final InstanceAlreadyExistsException e) {
			SpreadProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			SpreadProbeHandler.LOGGER.fatal(e, e);
		} catch (final NotCompliantMBeanException e) {
			SpreadProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mObjectName = mxbeanName;
		}

	}

	public void addProcessedMessage() {
		this.mProbeInstance.addProcessedMessage();
	}

	public void addReceivedMessage() {
		this.mProbeInstance.addReceivedMessage();
	}

	@Override
	protected void shutdown() {
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			mbs.unregisterMBean(this.mObjectName);
		} catch (final InstanceNotFoundException e) {
			SpreadProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			SpreadProbeHandler.LOGGER.fatal(e, e);
		}
	}

}
