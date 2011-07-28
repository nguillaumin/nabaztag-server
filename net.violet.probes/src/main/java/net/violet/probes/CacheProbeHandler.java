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

public class CacheProbeHandler extends ProbesHandler {

	private static final Logger LOGGER = Logger.getLogger(CacheProbeHandler.class);

	private final CacheProbe mProbeInstance;
	private final ObjectName mObjectName;

	protected CacheProbeHandler() {
		CacheProbe theProbe = null;
		try {
			theProbe = new CacheProbe();
		} catch (final OpenDataException e) {
			CacheProbeHandler.LOGGER.fatal(e, e);
		} finally {
			this.mProbeInstance = theProbe;
		}

		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName mxbeanName = null;
		try {
			mxbeanName = new ObjectName("net.violet.admin.probes:type=CacheProbe");
			mbs.registerMBean(this.mProbeInstance, mxbeanName);
		} catch (final MalformedObjectNameException e) {
			CacheProbeHandler.LOGGER.fatal(e, e);
		} catch (final NullPointerException e) {
			CacheProbeHandler.LOGGER.fatal(e, e);
		} catch (final InstanceAlreadyExistsException e) {
			CacheProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			CacheProbeHandler.LOGGER.fatal(e, e);
		} catch (final NotCompliantMBeanException e) {
			CacheProbeHandler.LOGGER.fatal(e, e);
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
			CacheProbeHandler.LOGGER.fatal(e, e);
		} catch (final MBeanRegistrationException e) {
			CacheProbeHandler.LOGGER.fatal(e, e);
		}
	}

	public void updateCacheMapsStats(Integer[] inCacheMapSizes, Long[] inMessagePulseStats) {
		this.mProbeInstance.updateCacheMapsStats(inCacheMapSizes, inMessagePulseStats);
	}

}
