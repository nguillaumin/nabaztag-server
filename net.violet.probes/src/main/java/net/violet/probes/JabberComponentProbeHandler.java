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

public class JabberComponentProbeHandler extends ProbesHandler {

	private static final Logger LOGGER = Logger.getLogger(JabberComponentProbeHandler.class);

	private final JabberComponentProbe mProbeInstance;
	private final ObjectName mObjectName;

	protected JabberComponentProbeHandler() {
		JabberComponentProbe theProbe = null;
		try {
			theProbe = new JabberComponentProbe();
		} catch (final OpenDataException e) {
			JabberComponentProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mProbeInstance = theProbe;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName mxbeanName = null;
		try {
			mxbeanName = new ObjectName("net.violet.admin.probes:type=JabberComponentProbe");
			mbs.registerMBean(this.mProbeInstance, mxbeanName);
		} catch (final MalformedObjectNameException e) {
			JabberComponentProbeHandler.LOGGER.fatal(e, e);
		} catch (final NullPointerException e) {
			JabberComponentProbeHandler.LOGGER.fatal(e, e);
		} catch (final InstanceAlreadyExistsException e) {
			JabberComponentProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			JabberComponentProbeHandler.LOGGER.fatal(e, e);
		} catch (final NotCompliantMBeanException e) {
			JabberComponentProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mObjectName = mxbeanName;
		}
	}

	public void addConnectedComponent(String inComponentName) {
		this.mProbeInstance.addConnectedComponent(inComponentName);
	}

	public void delConnectedComponent(String inComponentName) {
		this.mProbeInstance.delConnectedComponent(inComponentName);
	}

	@Override
	protected void shutdown() {
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			mbs.unregisterMBean(this.mObjectName);
		} catch (final InstanceNotFoundException e) {
			JabberComponentProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			JabberComponentProbeHandler.LOGGER.fatal(e, e);
		}
	}

}
