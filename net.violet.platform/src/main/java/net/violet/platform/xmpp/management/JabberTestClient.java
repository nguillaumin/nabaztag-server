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

/**
 * Client XMPP de test.
 */
public final class JabberTestClient extends Thread {



	private static final Logger LOGGER = Logger.getLogger(JabberTestClient.class);

	/**
	 * Référence sur le client léger.
	 */
	private final JabberLightClient mJabberLightClient;

	/**
	 * Regex pour la commande login.
	 */
	private static final Pattern LOGIN_CMD_REGEX = Pattern.compile("LOGIN\\s+(\\w+)\\s+(\\w+)");

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
	 * Regex pour la commande BIND.
	 */
	private static final Pattern BIND_CMD_REGEX = Pattern.compile("BIND\\s+(\\w+)");

	/**
	 * Regex pour la commande UNBIND.
	 */
	private static final Pattern UNBIND_CMD_REGEX = Pattern.compile("UNBIND\\s+(\\w+)");

	/**
	 * Regex pour la commande SESSION.
	 */
	private static final Pattern SESSION_CMD_REGEX = Pattern.compile("SESSION");

	/**
	 * Regex pour la commande RESOURCES.
	 */
	private static final Pattern RESOURCES_CMD_REGEX = Pattern.compile("RESOURCES\\s+(.+)");

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
	private JabberTestClient(String inDomain, String inHost, int inPort) throws UnknownHostException, IOException {
		// Ouverture de la socket.
		this.mJabberLightClient = new JabberLightClient(inDomain, inHost, inPort);

		final Runnable theTask = new Runnable() {

			public void run() {
				Selector theSelector;
				try {
					theSelector = Selector.open();
					JabberTestClient.this.mJabberLightClient.getSocket().register(theSelector, SelectionKey.OP_READ);
					while (true) {
						final int nbSockets = theSelector.select();
						if (nbSockets >= 0) {
							JabberTestClient.this.mJabberLightClient.processSocketData();
						} else {
							System.err.println("Socket closed = " + nbSockets);
							break;
						}
					}
				} catch (final IOException e) {
					JabberTestClient.LOGGER.fatal(e, e);
				}
			}
		};
		final Thread theThread = new Thread(theTask);
		theThread.start();
	}

	public void login(String inLogin, String inPassword) throws IOException {
		this.mJabberLightClient.login(inLogin, inPassword);
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
					Matcher theMatcher = JabberTestClient.LOGIN_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						this.mJabberLightClient.login(theMatcher.group(1), theMatcher.group(2));
						break;
					}

					theMatcher = JabberTestClient.RAW_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						final String theData = theMatcher.group(1);
						this.mJabberLightClient.write(theData);
						break;
					}

					theMatcher = JabberTestClient.HELP_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						System.err.println("Commands:");
						System.err.println("LOGIN login pass   - do the SASL authentication");
						System.err.println("RAW data           - send raw data");
						System.err.println("HELP               - show this help");
						System.err.println("QUIT               - quit the client");
						System.err.println("BIND resource      - bind a resource");
						System.err.println("UNBIND resource    - unbind a resource");
						System.err.println("SESSION            - opens the session");
						System.err.println("RESOURCES jid      - gets resources");
						break;
					}

					theMatcher = JabberTestClient.QUIT_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						this.mJabberLightClient.write("</stream:stream>");
						this.mQuit = true;
						this.mJabberLightClient.getSocket().close();
						break;
					}

					theMatcher = JabberTestClient.BIND_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						final String theResource = theMatcher.group(1);
						this.mJabberLightClient.bind(theResource);
						break;
					}

					theMatcher = JabberTestClient.UNBIND_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						final String theResource = theMatcher.group(1);
						this.mJabberLightClient.unbind(theResource);
						break;
					}

					theMatcher = JabberTestClient.SESSION_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						this.mJabberLightClient.startSession();
						break;
					}

					theMatcher = JabberTestClient.RESOURCES_CMD_REGEX.matcher(theString);
					if (theMatcher.matches()) {
						final String theTarget = theMatcher.group(1);
						this.mJabberLightClient.unbind(theTarget);
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
		if ((inArgs.length == 3) || (inArgs.length == 5)) {
			final String domain = inArgs[0];
			final String host = inArgs[1];
			final int port = Integer.parseInt(inArgs[2]);

			// Création du client.
			final JabberTestClient theClient = new JabberTestClient(domain, host, port);
			theClient.start();

			if (inArgs.length == 5) {
				final String login = inArgs[3];
				final String password = inArgs[4];
				try {
					Thread.sleep(500);
				} catch (final InterruptedException e) {
					
					e.printStackTrace();
				}
				theClient.login(login, password);
			}
		} else {
			System.err.println("Syntaxe: JabberTestClient domain host port [login password]");
		}
	}
}
