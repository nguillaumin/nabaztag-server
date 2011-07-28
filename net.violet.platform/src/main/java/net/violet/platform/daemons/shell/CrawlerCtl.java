package net.violet.platform.daemons.shell;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public final class CrawlerCtl {


	private static final Logger LOGGER = Logger.getLogger(CrawlerCtl.class);

	private final XmlRpcClient mXmlRpcClient;

	private static XmlRpcClient createXmlRpcClient(String inHostname, String inPort) {
		final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		XmlRpcClient theClient = null;
		try {
			config.setServerURL(new URL(inHostname + ":" + inPort));
			theClient = new XmlRpcClient();
			theClient.setConfig(config);
		} catch (final MalformedURLException e) {
			CrawlerCtl.LOGGER.fatal(e, e);
		}

		return theClient;
	}

	private CrawlerCtl(String inHostname, String inPort) {

		this.mXmlRpcClient = CrawlerCtl.createXmlRpcClient("http://" + inHostname, inPort);

	}

	public static void main(String[] args) {
		if ((args.length > 0) && args[0].equals("--help")) {
			CrawlerCtl.printHelp();
			return;
		}

		// Analyse des paramètres
		final LongOpt[] theLongOpts = new LongOpt[] { new LongOpt("host", LongOpt.OPTIONAL_ARGUMENT, null, 'h'), new LongOpt("port", LongOpt.REQUIRED_ARGUMENT, null, 'p'), };
		final Getopt theGetOpt = new Getopt(CrawlerCtl.class.getSimpleName(), args, "+h:p:", theLongOpts);
		int theOption;

		String port = null;
		String host = "localhost"; // by default we try to connect on localhost
		theGetOpt.setOpterr(false);

		while ((theOption = theGetOpt.getopt()) != -1) {
			switch (theOption) {
			case 'h':
				host = theGetOpt.getOptarg();
				break;

			case 'p':
				port = theGetOpt.getOptarg();
				break;
			}
		}

		final int theFirstNonOptArg = theGetOpt.getOptind();

		if (port == null) {
			CrawlerCtl.syntaxError();
		} else {
			final CrawlerCtl theCtl = new CrawlerCtl(host, port);

			if (theCtl.checkStatusShell()) {
				if (args.length < theFirstNonOptArg + 1) {
					CrawlerCtl.syntaxError();
				} else {
					final String command = args[theFirstNonOptArg];
					if (command.equals("start")) {
						final String target = args[theFirstNonOptArg + 1];
						final int nbArgs = args.length - theFirstNonOptArg - 2;
						final String[] theArgs = new String[nbArgs];
						System.arraycopy(args, theFirstNonOptArg + 2, theArgs, 0, nbArgs);
						theCtl.start(target, theArgs);
					} else if (command.equals("stop")) {
						final String target = args[theFirstNonOptArg + 1];
						theCtl.stop(target);
					} else if (command.equals("status")) {
						final String target = args[theFirstNonOptArg + 1];
						theCtl.getCrawlerStatus(target);
					} else if (command.equals("stopall")) {
						theCtl.stopAll();
					} else {
						System.err.println("Unknown command : " + command);
						CrawlerCtl.printHelp();
					}
				}
			} else {
				System.err.println("CrawlerShell not started!!!!");
			}
		}
	}

	private boolean checkStatusShell() {
		boolean result = false;
		try {
			result = ((Boolean) this.mXmlRpcClient.execute(CrawlerShell.HANDLER_PREFIX + CrawlerShell.STATUS_CRAWLER_SHELL, new Object[] {})).booleanValue();
		} catch (final XmlRpcException e) {
			CrawlerCtl.LOGGER.fatal(e, e);
		}
		return result;
	}

	private static void syntaxError() {
		System.err.println("Syntax error: missing command.");
		CrawlerCtl.printHelp();
	}

	private static void printHelp() {
		System.out.println("CrawlerCtl [options] command params...");
		System.out.println("Common options : ");
		System.out.println("-h hostname : optional option, indicates the ip or server name where the specified crawler is running");
		System.out.println("-p port number : indicates the port number where the specified crawler is running");
		System.out.println("-t target : ClassName (use for command start and stop)");
		System.out.println("-c command => ");
		System.out.println("start : start specified crawler");
		System.out.println("stop : stop specified crawler");
		System.out.println("quit  : stop all crawlers launched by the crawlerCtl");
	}

	private void start(String inClassName, Object[] inArgs) {
		try {
			final String result = (String) this.mXmlRpcClient.execute(CrawlerShell.HANDLER_PREFIX + CrawlerShell.START_CRAWLER_METHOD, new Object[] { inClassName, inArgs });
			System.out.println(inClassName + " STATUS => " + result);
		} catch (final XmlRpcException e) {
			CrawlerCtl.LOGGER.fatal(e, e);
		}
	}

	private void stop(String inClassName) {
		try {
			final String result = (String) this.mXmlRpcClient.execute(CrawlerShell.HANDLER_PREFIX + CrawlerShell.STOP_CRAWLER_METHOD, new Object[] { inClassName });
			System.out.println(inClassName + " STATUS => " + result);
		} catch (final XmlRpcException e) {
			CrawlerCtl.LOGGER.fatal(e, e);
		}
	}

	private void stopAll() {
		try {
			final String result = (String) this.mXmlRpcClient.execute(CrawlerShell.HANDLER_PREFIX + CrawlerShell.STOP_ALL_CRAWLER_METHOD, new Object[] {});
			System.out.println(" STATUS => " + result);
		} catch (final XmlRpcException e) {
			CrawlerCtl.LOGGER.fatal(e, e);
		}
	}

	private void getCrawlerStatus(String inClassName) {
		try {
			final String result = (String) this.mXmlRpcClient.execute(CrawlerShell.HANDLER_PREFIX + CrawlerShell.CRAWLER_STATUS, new Object[] { inClassName });
			System.out.println(inClassName + " STATUS => " + result);
		} catch (final XmlRpcException e) {
			CrawlerCtl.LOGGER.fatal(e, e);
		}
	}

}
