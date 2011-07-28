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

public class JabberClientProbeHandler extends ProbesHandler {


	private static final Logger LOGGER = Logger.getLogger(JabberClientProbeHandler.class);

	private final JabberClientProbe mProbeInstance;
	private final ObjectName mObjectName;

	protected JabberClientProbeHandler() {
		JabberClientProbe theProbe = null;
		try {
			theProbe = new JabberClientProbe();
		} catch (final OpenDataException e) {
			JabberClientProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mProbeInstance = theProbe;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName mxbeanName = null;
		try {
			mxbeanName = new ObjectName("net.violet.admin.probes:type=JabberClientProbe");
			mbs.registerMBean(this.mProbeInstance, mxbeanName);
		} catch (final MalformedObjectNameException e) {
			JabberClientProbeHandler.LOGGER.fatal(e, e);
		} catch (final NullPointerException e) {
			JabberClientProbeHandler.LOGGER.fatal(e, e);
		} catch (final InstanceAlreadyExistsException e) {
			JabberClientProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			JabberClientProbeHandler.LOGGER.fatal(e, e);
		} catch (final NotCompliantMBeanException e) {
			JabberClientProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mObjectName = mxbeanName;
		}
	}

	public void addConnectedClient(String inUserName) {
		this.mProbeInstance.addConnectedClient(inUserName);
	}

	public void delConnectedClient(String inUserName) {
		this.mProbeInstance.delConnectedClient(inUserName);
	}

	@Override
	protected void shutdown() {
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			mbs.unregisterMBean(this.mObjectName);
		} catch (final InstanceNotFoundException e) {
			JabberClientProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			JabberClientProbeHandler.LOGGER.fatal(e, e);
		}
	}

}
