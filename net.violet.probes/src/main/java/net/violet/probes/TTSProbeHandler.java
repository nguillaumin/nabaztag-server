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

public class TTSProbeHandler extends ProbesHandler {


	private static final Logger LOGGER = Logger.getLogger(TTSProbeHandler.class);

	private final TTSProbe mProbeInstance;
	private final ObjectName mObjectName;

	protected TTSProbeHandler() {
		TTSProbe theProbe = null;
		try {
			theProbe = new TTSProbe();
		} catch (final OpenDataException e) {
			TTSProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mProbeInstance = theProbe;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName mxbeanName = null;
		try {
			mxbeanName = new ObjectName("net.violet.admin.probes:type=TTSProbe");
			mbs.registerMBean(this.mProbeInstance, mxbeanName);
		} catch (final MalformedObjectNameException e) {
			TTSProbeHandler.LOGGER.fatal(e, e);
		} catch (final NullPointerException e) {
			TTSProbeHandler.LOGGER.fatal(e, e);
		} catch (final InstanceAlreadyExistsException e) {
			TTSProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			TTSProbeHandler.LOGGER.fatal(e, e);
		} catch (final NotCompliantMBeanException e) {
			TTSProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mObjectName = mxbeanName;
		}
	}

	public void addNewTTS() {
		this.mProbeInstance.addNewTTS();
	}

	public void addProcessed() {
		this.mProbeInstance.addProcessed();
	}

	public void addProcessing() {
		this.mProbeInstance.addProcessing();
	}

	public void addFailed() {
		this.mProbeInstance.addFailed();
	}

	@Override
	protected void shutdown() {
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			mbs.unregisterMBean(this.mObjectName);
		} catch (final InstanceNotFoundException e) {
			TTSProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			TTSProbeHandler.LOGGER.fatal(e, e);
		}
	}

}
