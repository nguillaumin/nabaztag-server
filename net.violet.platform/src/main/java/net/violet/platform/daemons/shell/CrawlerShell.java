package net.violet.platform.daemons.shell;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import net.violet.platform.daemons.AbstractDaemon;
import net.violet.platform.management.ShutdownHook;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcHandler;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.common.XmlRpcInvocationException;
import org.apache.xmlrpc.server.XmlRpcHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcNoSuchHandlerException;
import org.apache.xmlrpc.webserver.WebServer;

import sun.misc.Signal;
import sun.misc.SignalHandler;

public final class CrawlerShell implements XmlRpcHandler {


	private static final Logger LOGGER = Logger.getLogger(CrawlerShell.class);

	public static final String HANDLER_PREFIX = "CrawlerShell.";

	public static final String START_CRAWLER_METHOD = "startCrawler";

	public static final String STOP_CRAWLER_METHOD = "stopCrawler";

	public static final String STOP_ALL_CRAWLER_METHOD = "stopAllCrawler";

	public static final String STATUS_CRAWLER_SHELL = "getStatusShell";

	public static final String CRAWLER_STATUS = "getCrawlerStatus";

	public static enum STATUS_CRAWLER {
		START,
		STOP,
		ALREADY_START,
		ALREADY_STOP,
		STOP_ALL,
		ERROR,
		UP,
		DOWN,
		SLOW
	}

	/**
	 * Map contenant la liste des crawlers en cours
	 */
	private static final Map<Class, AbstractDaemon> mMapCrawler = new HashMap<Class, AbstractDaemon>();

	/**
	 * Référence sur le gestionnaire par défaut.
	 */
	private final SignalHandler mDefaultIntHandler;

	private CrawlerShell() {
		// Gestionnaire pour le signal INT
		final SignalHandler myIntHandler = new SignalHandler() {

			public void handle(Signal inSignal) {
				signalHandler(inSignal);
			}
		};

		this.mDefaultIntHandler = Signal.handle(new Signal("INT"), myIntHandler);
	}

	/**
	 * Gestionnaire pour les interruptions
	 * 
	 * @param inSignal signal reçu (INT).
	 */
	private void signalHandler(Signal inSignal) {
		Signal.handle(new Signal("INT"), this.mDefaultIntHandler);

		for (final AbstractDaemon theAbstractCrawler : CrawlerShell.mMapCrawler.values()) {
			theAbstractCrawler.stopCrawler();
		}

		ShutdownHook.shutdown();

		// Appel du gestionnaire par défaut.
		this.mDefaultIntHandler.handle(inSignal);
	}

	/**
	 * Lancement d'un crawler
	 * 
	 * @param inClassName : nom du crawler a lancé
	 * @param inArgs : les paramètres
	 * @return le status du crawler a lancé
	 */
	private String startCrawler(String inClassName, Object[] inArgs) {

		final int nbArgs = inArgs.length;
		final String[] theArgs = new String[nbArgs];
		final Class[] signature = new Class[] { theArgs.getClass() };

		for (int index = 0; index < nbArgs; index++) {
			theArgs[index] = (String) inArgs[index];
		}

		String result = STATUS_CRAWLER.ERROR.toString();
		try {
			final Class theCrawlerClass = Class.forName(inClassName);

			if (theCrawlerClass != null) {
				AbstractDaemon theDaemon;
				synchronized (CrawlerShell.mMapCrawler) {
					theDaemon = CrawlerShell.mMapCrawler.get(theCrawlerClass);
				}
				if (theDaemon == null) {
					Constructor theConstructor = null;
					try {
						theConstructor = theCrawlerClass.getDeclaredConstructor(signature);
					} catch (final SecurityException anException) {
						CrawlerShell.LOGGER.fatal(anException, anException);
					} catch (final NoSuchMethodException anException) {
						try {
							theConstructor = theCrawlerClass.getConstructor(signature);
						} catch (final SecurityException e) {
							CrawlerShell.LOGGER.fatal(e, e);
						} catch (final NoSuchMethodException e) {
							CrawlerShell.LOGGER.fatal(e, e);
						}
					}

					try {
						if (theConstructor != null) {
							theDaemon = (AbstractDaemon) theConstructor.newInstance((Object) theArgs);
							synchronized (CrawlerShell.mMapCrawler) {
								CrawlerShell.mMapCrawler.put(theCrawlerClass, theDaemon);
							}
							doStartCrawler(theCrawlerClass, theDaemon);
							result = STATUS_CRAWLER.START.toString();
						}
					} catch (final IllegalArgumentException anException) {
						CrawlerShell.LOGGER.fatal(anException, anException);
					} catch (final InstantiationException anException) {
						CrawlerShell.LOGGER.fatal(anException, anException);
					} catch (final IllegalAccessException anException) {
						CrawlerShell.LOGGER.fatal(anException, anException);
					} catch (final InvocationTargetException anException) {
						if (!(anException.getCause() instanceof NoSuchElementException)) {
							CrawlerShell.LOGGER.fatal(anException, anException);
						}
					}
				} else {
					result = STATUS_CRAWLER.ALREADY_START.toString();
				}
			}
		} catch (final ClassNotFoundException e) {}

		return result;
	}

	/**
	 * Démarre le crawler.
	 * 
	 * @param theDaemon crawler à démarrer.
	 */
	private void doStartCrawler(Class inCrawlerClass, final AbstractDaemon inDaemon) {
		final Class theClass = inCrawlerClass;
		final Thread theCrawlerThread = new Thread(inCrawlerClass.getSimpleName()) {

			@Override
			public void run() {
				try {
					inDaemon.run();
				} catch (final Throwable anException) {
					CrawlerShell.LOGGER.fatal(anException, anException);
				}
				crawlerEnded(theClass);
			}
		};
		theCrawlerThread.start();
	}

	/**
	 * Méthode appelée lorsqu'un crawler a terminé (crash, fin normale).
	 * 
	 * @param inClass : classe du crawler qui a terminé.
	 */
	private void crawlerEnded(Class inCrawlerClass) {
		synchronized (CrawlerShell.mMapCrawler) {
			CrawlerShell.mMapCrawler.remove(inCrawlerClass);
		}
	}

	/**
	 * Arrêt d'un crawler
	 * 
	 * @param inClassName : nom du crawler a lancé
	 * @return le status du crawler a lancé
	 */
	private String stopCrawler(String inClassName) {

		String result = STATUS_CRAWLER.ERROR.toString();
		try {
			final Class theCrawlerClass = Class.forName(inClassName);
			if (theCrawlerClass != null) {
				final AbstractDaemon theDaemon;
				synchronized (CrawlerShell.mMapCrawler) {
					theDaemon = CrawlerShell.mMapCrawler.get(theCrawlerClass);
				}
				if (theDaemon != null) {
					theDaemon.stopCrawler();
					synchronized (CrawlerShell.mMapCrawler) {
						CrawlerShell.mMapCrawler.remove(theCrawlerClass);
					}
					result = STATUS_CRAWLER.STOP.toString();
				} else {
					result = STATUS_CRAWLER.ALREADY_STOP.toString();
				}
			}
		} catch (final ClassNotFoundException e) {
			CrawlerShell.LOGGER.fatal(e, e);
		}
		return result;
	}

	/**
	 * Arrêt de tous les crawlers
	 */
	private String stopAllCrawler() {
		String result = STATUS_CRAWLER.ALREADY_STOP.toString();
		synchronized (CrawlerShell.mMapCrawler) {
			for (final AbstractDaemon theAbstractCrawler : CrawlerShell.mMapCrawler.values()) {
				theAbstractCrawler.stopCrawler();
				result = STATUS_CRAWLER.STOP_ALL.toString();
			}
			CrawlerShell.mMapCrawler.clear();
		}

		return result;
	}

	/**
	 * Permet de savoir si le Shell de crawler est actif
	 * 
	 * @return <code> true </code> si ok
	 */
	private boolean getStatusShell() {
		return true;
	}

	/**
	 * Permet de savoir si le crawler est actif
	 * 
	 * @param inClassName : nom du crawler a vérifié
	 * @return le status du crawler
	 */
	private String getCrawlerStatus(String inClassName) {

		String result = STATUS_CRAWLER.ERROR.toString();
		try {
			final Class theCrawlerClass = Class.forName(inClassName);
			if (theCrawlerClass != null) {
				final AbstractDaemon theDaemon;
				synchronized (CrawlerShell.mMapCrawler) {
					theDaemon = CrawlerShell.mMapCrawler.get(theCrawlerClass);
				}
				if (theDaemon != null) {
					// TODO je pense que le mieux serait de passer en argument
					// lors du lancement du crawler
					// le temps moyen d'un cycle de traitement
					final long timeTask = System.currentTimeMillis() - theDaemon.getStartTime();
					if (timeTask > theDaemon.getMaxDelay()) { // le traitement est long
						result = STATUS_CRAWLER.SLOW.toString() + " time : " + timeTask + " ms";
					} else {
						result = STATUS_CRAWLER.UP.toString();
					}
				} else {
					result = STATUS_CRAWLER.DOWN.toString();
				}
			}
		} catch (final ClassNotFoundException e) {
			CrawlerShell.LOGGER.fatal(e, e);
		}
		return result;
	}

	public Object execute(XmlRpcRequest inRequest) throws XmlRpcException {
		Object theResult = "NoSuchMethod";
		try {
			final String theMethod = inRequest.getMethodName().substring(CrawlerShell.HANDLER_PREFIX.length());
			if (theMethod.equals(CrawlerShell.START_CRAWLER_METHOD)) {
				final String theClassName = (String) inRequest.getParameter(0);
				theResult = startCrawler(theClassName, (Object[]) inRequest.getParameter(1));
			} else if (theMethod.equals(CrawlerShell.STOP_CRAWLER_METHOD)) {
				theResult = stopCrawler((String) inRequest.getParameter(0));
			} else if (theMethod.equals(CrawlerShell.STOP_ALL_CRAWLER_METHOD)) {
				theResult = stopAllCrawler();
			} else if (theMethod.equals(CrawlerShell.STATUS_CRAWLER_SHELL)) {
				theResult = getStatusShell();
			} else if (theMethod.equals(CrawlerShell.CRAWLER_STATUS)) {
				theResult = getCrawlerStatus((String) inRequest.getParameter(0));
			}
		} catch (final Exception e) {
			CrawlerShell.LOGGER.fatal(e, e);
			throw new XmlRpcInvocationException(e.getMessage(), e);
		}

		return theResult;
	}

	public static void main(String[] args) {
		try {

			if (args.length == 0) {
				CrawlerShell.LOGGER.info("Mauvais argument!! Renseignez le port.");
				return;
			}

			final int port = Integer.parseInt(args[0]);
			CrawlerShell.LOGGER.info("Starting server");
			final CrawlerShell theCrawlerShell = new CrawlerShell();
			final WebServer theWebServer = new WebServer(port);
			theWebServer.getXmlRpcServer().setHandlerMapping(new XmlRpcHandlerMapping() {

				public XmlRpcHandler getHandler(String inHandler) throws XmlRpcNoSuchHandlerException {
					final XmlRpcHandler theResult;
					if (inHandler.startsWith(CrawlerShell.HANDLER_PREFIX)) {
						theResult = theCrawlerShell;
					} else {
						throw new XmlRpcNoSuchHandlerException(inHandler);
					}
					return theResult;
				}
			});
			theWebServer.start();
			CrawlerShell.LOGGER.info("Server ready");
		} catch (final Throwable aThrowable) {
			CrawlerShell.LOGGER.fatal(aThrowable, aThrowable);
		}
	}
}
