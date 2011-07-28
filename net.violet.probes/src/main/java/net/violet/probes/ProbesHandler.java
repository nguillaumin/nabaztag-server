package net.violet.probes;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public abstract class ProbesHandler {


	private static final Logger LOGGER = Logger.getLogger(ProbesHandler.class);

	private static final List<ProbesHandler> PROBE_INSTANCES = new LinkedList<ProbesHandler>();

	public static final SpreadProbeHandler SPREAD;
	static {
		SpreadProbeHandler theProbe = null;
		try {
			theProbe = new SpreadProbeHandler();
		} catch (final Exception e) {
			ProbesHandler.LOGGER.fatal(e, e);
		} finally {
			SPREAD = theProbe;
		}
	}
	public static final CacheProbeHandler CACHE_MAP;
	static {
		CacheProbeHandler theProbe = null;
		try {
			theProbe = new CacheProbeHandler();
		} catch (final Exception e) {
			ProbesHandler.LOGGER.fatal(e, e);
		} finally {
			CACHE_MAP = theProbe;
		}
	}
	public static final EventProbeHandler EVENT;
	static {
		EventProbeHandler theProbe = null;
		try {
			theProbe = new EventProbeHandler();
		} catch (final Exception e) {
			ProbesHandler.LOGGER.fatal(e, e);
		} finally {
			EVENT = theProbe;
		}
	}
	public static final SgbdConnectionProbeHandler SGBDCONNECTION;
	static {
		SgbdConnectionProbeHandler theProbe = null;
		try {
			theProbe = new SgbdConnectionProbeHandler();
		} catch (final Exception e) {
			ProbesHandler.LOGGER.fatal(e, e);
		} finally {
			SGBDCONNECTION = theProbe;
		}
	}

	public static final DownloadProbeHandler DOWNLOAD;
	static {
		DownloadProbeHandler theProbe = null;
		try {
			theProbe = new DownloadProbeHandler();
		} catch (final Exception e) {
			ProbesHandler.LOGGER.fatal(e, e);
		} finally {
			DOWNLOAD = theProbe;
		}
	}

	public static final TTSProbeHandler TTS;
	static {
		TTSProbeHandler theProbe = null;
		try {
			theProbe = new TTSProbeHandler();
		} catch (final Exception e) {
			ProbesHandler.LOGGER.fatal(e, e);
		} finally {
			TTS = theProbe;
		}
	}

	public static final JabberComponentProbeHandler COMPONENT;
	static {
		JabberComponentProbeHandler theProbe = null;
		try {
			theProbe = new JabberComponentProbeHandler();
		} catch (final Exception e) {
			ProbesHandler.LOGGER.fatal(e, e);
		} finally {
			COMPONENT = theProbe;
		}
	}

	public static final JabberClientProbeHandler CLIENT;
	static {
		JabberClientProbeHandler theProbe = null;
		try {
			theProbe = new JabberClientProbeHandler();
		} catch (final Exception e) {
			ProbesHandler.LOGGER.fatal(e, e);
		} finally {
			CLIENT = theProbe;
		}
	}

	public static void init() {
		// This space for rent
	}

	protected ProbesHandler() {
		synchronized (ProbesHandler.PROBE_INSTANCES) {
			ProbesHandler.PROBE_INSTANCES.add(this);
		}
	}

	protected abstract void shutdown();

	public static void shutdownAll() {
		for (final ProbesHandler aProbeHandler : ProbesHandler.PROBE_INSTANCES) {
			aProbeHandler.shutdown();
		}
	}
}
