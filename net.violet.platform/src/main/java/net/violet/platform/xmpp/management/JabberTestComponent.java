package net.violet.platform.xmpp.management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public final class JabberTestComponent extends Thread {



	private static final Logger LOGGER = Logger.getLogger(JabberTestComponent.class);

	/**
	 * Regex pour du xml brut.
	 */
	private static final Pattern RAW_CMD_REGEX = Pattern.compile("RAW\\s+(.*)");

	/**
	 * Regex pour la commande HELP.
	 */
	private static final Pattern HELP_CMD_REGEX = Pattern.compile("HELP");

	/**
	 * Regex pour la commande QUIT.
	 */
	private static final Pattern QUIT_CMD_REGEX = Pattern.compile("QUIT");

	/**
	 * Référence sur le client léger.
	 */
	private final JabberLightComponent mJabberLightComponent;

	/**
	 * Si on doit quitter.
	 */
	private volatile boolean mQuit;

	/**
	 * Constructeur à partir du domaine, du serveur et du port.
	 * 
	 * @throws IOException En cas de problème I/O.
	 * @throws UnknownHostException Si le serveur n'existe pas.
	 */
	private JabberTestComponent(String inName, String inPassword, String inHost, int inPort) throws UnknownHostException, IOException {
		// Ouverture de la socket.
		this.mJabberLightComponent = new JabberLightComponent(inName, inPassword, inHost, inPort);

		final Runnable theTask = new Runnable() {

			public void run() {
				Selector theSelector;
				try {
					theSelector = Selector.open();
					JabberTestComponent.this.mJabberLightComponent.getSocket().register(theSelector, SelectionKey.OP_READ);
					while (true) {
						final int nbSockets = theSelector.select();
						if (nbSockets >= 0) {
							JabberTestComponent.this.mJabberLightComponent.processSocketData();
						} else {
							System.err.println("Socket closed = " + nbSockets);
							break;
						}
					}
				} catch (final IOException e) {
					JabberTestComponent.LOGGER.fatal(e, e);
				}
			}
		};
		final Thread theThread = new Thread(theTask);
		theThread.start();
	}

	/**
	 * Point d'entrée du thread général (qui gère stdin).
	 */
	@Override
	public void run() {

		try {
			final BufferedReader theStdinReader = new BufferedReader(new InputStreamReader(System.in));
			while (!this.mQuit) { // TODO voir dans quel cas on quitte
				final String theString = theStdinReader.readLine();
				if (theString == null) {
					break;
				}

				// Analyse de la ligne.
				do {
					Matcher theMatcher = JabberTestComponent.RAW_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						final String theData = theMatcher.group(1);
						this.mJabberLightComponent.write(theData);
						break;
					}

					theMatcher = JabberTestComponent.HELP_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						System.err.println("Commands:");
						System.err.println("RAW data           - send raw data");
						System.err.println("HELP               - show this help");
						System.err.println("QUIT               - quit the client");
						break;
					}

					theMatcher = JabberTestComponent.QUIT_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						this.mJabberLightComponent.write("</stream:stream>");
						this.mQuit = true;
						this.mJabberLightComponent.getSocket().close();
						break;
					}

					System.err.println("Unknown command. Try HELP");
				} while (false);
			}
		} catch (final IOException anException) {
			anException.printStackTrace();
			this.mQuit = true;
		}
	}

	/**
	 * Point d'entrée.
	 * 
	 * @param inArgs paramètres en ligne de commande.
	 * @throws IOException En cas de problème I/O.
	 * @throws UnknownHostException Si le serveur n'existe pas.
	 */
	public static void main(String[] inArgs) throws UnknownHostException, IOException {
		// Récupération des paramètres
		if (inArgs.length == 4) {
			final String name = inArgs[0];
			final String password = inArgs[1];
			final String host = inArgs[2];
			final int port = Integer.parseInt(inArgs[3]);

			// Création du client.
			final JabberTestComponent theClient = new JabberTestComponent(name, password, host, port);
			theClient.start();

		} else {
			System.err.println("Syntaxe: JabberTestComponent name password host port");
		}
	}

}
