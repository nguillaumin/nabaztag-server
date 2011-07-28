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

public class DownloadProbeHandler extends ProbesHandler {


	private static final Logger LOGGER = Logger.getLogger(DownloadProbeHandler.class);

	private final DownloadProbe mProbeInstance;
	private final ObjectName mObjectName;

	protected DownloadProbeHandler() {
		DownloadProbe theProbe = null;
		try {
			theProbe = new DownloadProbe();
		} catch (final OpenDataException e) {
			DownloadProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mProbeInstance = theProbe;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName mxbeanName = null;
		try {
			mxbeanName = new ObjectName("net.violet.admin.probes:type=DownloadProbe");
			mbs.registerMBean(this.mProbeInstance, mxbeanName);
		} catch (final MalformedObjectNameException e) {
			DownloadProbeHandler.LOGGER.fatal(e, e);
		} catch (final NullPointerException e) {
			DownloadProbeHandler.LOGGER.fatal(e, e);
		} catch (final InstanceAlreadyExistsException e) {
			DownloadProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			DownloadProbeHandler.LOGGER.fatal(e, e);
		} catch (final NotCompliantMBeanException e) {
			DownloadProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mObjectName = mxbeanName;
		}

	}

	public void addNewDownload(boolean isFree) {
		this.mProbeInstance.addNewDownload(isFree);
	}

	public void addProcessed(boolean isFree) {
		this.mProbeInstance.addProcessed(isFree);
	}

	public void addProcessing(boolean isFree) {
		this.mProbeInstance.addProcessing(isFree);
	}

	@Override
	protected void shutdown() {
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			mbs.unregisterMBean(this.mObjectName);
		} catch (final InstanceNotFoundException e) {
			DownloadProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			DownloadProbeHandler.LOGGER.fatal(e, e);
		}
	}

}
