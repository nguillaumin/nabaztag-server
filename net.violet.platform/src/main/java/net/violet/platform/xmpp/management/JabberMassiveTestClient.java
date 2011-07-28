package net.violet.platform.xmpp.management;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;

/**
 * Client XMPP de test.
 */
public final class JabberMassiveTestClient extends Thread implements JabberLightClientHandler {



	private static final Logger LOGGER = Logger.getLogger(JabberMassiveTestClient.class);

	private final Map<SelectionKey, JabberLightClient> mJabberLightClients = new HashMap<SelectionKey, JabberLightClient>();

	private final Selector mSelector;

	private static String mHost;
	private static int mPort;

	private Integer mClientsAlive;
	private Integer mClientsReady;
	private Integer mClientsTotal;
	private final List<String> mClientsNotReady = new LinkedList<String>();

	/**
	 * Constructeur à partir du domaine, du serveur et du port.
	 * 
	 * @throws IOException En cas de problème I/O.
	 * @throws UnknownHostException Si le serveur n'existe pas.
	 */
	private JabberMassiveTestClient(String inDomain, String inHost, int inPort, List<String> inLogins) throws UnknownHostException, IOException {
		JabberMassiveTestClient.mHost = inHost;
		JabberMassiveTestClient.mPort = inPort;
		this.mSelector = Selector.open();
		for (final String login : inLogins) {
			JabberLightClient myJabberLightClient = null;
			while (myJabberLightClient == null) {
				try {
					myJabberLightClient = new JabberLightClient(inDomain, JabberMassiveTestClient.mHost, JabberMassiveTestClient.mPort, this);
				} catch (final ConnectException e) {}
			}
			myJabberLightClient.login(login, "violet");
			this.mJabberLightClients.put(myJabberLightClient.getSocket().register(this.mSelector, SelectionKey.OP_READ), myJabberLightClient);
		}
	}

	/**
	 * Point d'entrée du thread général (qui gère stdin).
	 */
	@Override
	public void run() {
		while (true) {
			try {
				final int nbSockets = this.mSelector.select();
				if (nbSockets >= 0) {
					for (final SelectionKey myKey : this.mSelector.selectedKeys()) {
						final JabberLightClient myClient = this.mJabberLightClients.get(myKey);
						if (myClient != null) {
							try {
								myClient.processSocketData();
							} catch (final IOException e) {
								/* À ce moment là, le client est mort */
								synchronized (this.mJabberLightClients) {
									this.mJabberLightClients.remove(myKey);
									myKey.cancel();
									myClient.getSocket().close();
									final JabberLightClient myJabberLightClient = new JabberLightClient(myClient.getDomain(), JabberMassiveTestClient.mHost, JabberMassiveTestClient.mPort, this);
									myJabberLightClient.login(myClient.getLogin(), "violet");
									this.mJabberLightClients.put(myJabberLightClient.getSocket().register(this.mSelector, SelectionKey.OP_READ), myJabberLightClient);

								}
								/* On essai de le ramener à la vie */
							}
						}
					}
				} else {
					System.err.println("Socket closed = " + nbSockets);
					break;
				}
			} catch (final IOException e) {
				JabberMassiveTestClient.LOGGER.fatal(e, e);
			}
		}
	}

	public void generateClientsStat(int touchClientsReady) {
		this.mClientsAlive = 0;
		this.mClientsReady = 0;
		this.mClientsTotal = 0;
		this.mClientsNotReady.clear();
		synchronized (this.mJabberLightClients) {
			for (final SelectionKey myKey : this.mJabberLightClients.keySet()) {
				final JabberLightClient myLight = this.mJabberLightClients.get(myKey);
				if (myLight != null) {
					this.mClientsAlive++;
					if (myLight.getState() == JabberLightClient.READY) {
						this.mClientsReady++;
						if (touchClientsReady > 0) {
							try {
								if (((int) Math.floor(Math.random() * 100d)) <= touchClientsReady) {
									myLight.doSimpleClick();
								}
							} catch (final IOException e) {}
						}
					} else {
						this.mClientsNotReady.add(myLight.getLogin());
					}
					// Envoi d'un espace toutes les 12 secondes (aléatoire
					// mode).
					try {
						if (((int) Math.floor(Math.random() * 6d)) <= 1) {
							myLight.doPing();
						}
					} catch (final IOException e) {}
				}
				this.mClientsTotal++;
			}
		}
	}

	public Integer getNbClientsAlive() {
		return this.mClientsAlive;
	}

	public Integer getNbClientsReady() {
		return this.mClientsReady;
	}

	public Integer getNbClientsTotal() {
		return this.mClientsTotal;
	}

	public List<String> getClientsNotReady() {
		return this.mClientsNotReady;
	}

	public void handleReceivedPacket(JabberLightClient inFromSocket, String inPacket) {
		System.out.println("Received packet : " + inPacket);
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
		if (inArgs.length < 5) {
			System.err.println("Syntaxe: JabberMassiveTestClient domain host port number_of_rabbit number_per_thread [mac_file | ((-s | --seed) F2345678)]");
			System.err.println("./JabberMassiveTestClient.sh xmpp-pp.nabaztag.com 192.168.1.160 5222 6144 128 > ./charge_log_`date +%F_%H:%M:%S`");
			System.err.println("JabberMassiveTestClient.bat xmpp-pp.nabaztag.com 192.168.1.160 5222 3072 32 > charge_log");
		} else {
			final String domain = inArgs[0];
			final String host = inArgs[1];
			final int port = Integer.parseInt(inArgs[2]);
			final int number = Integer.parseInt(inArgs[3]);
			final int split = Integer.parseInt(inArgs[4]);
			final Queue<String> listOfSerial = new LinkedList<String>();
			final List<JabberMassiveTestClient> myJabberMassiveClients = new LinkedList<JabberMassiveTestClient>();
			if (inArgs.length == 6) {
				final File mySerialFile;
				mySerialFile = new File(inArgs[3]);
				final FileReader myReader = new FileReader(mySerialFile);
				final CharBuffer myFileContent = CharBuffer.allocate((int) mySerialFile.length());
				if (myReader.read(myFileContent) > 0) {
					myFileContent.rewind();
					final String myFileContentStr = myFileContent.toString();
					for (final String s : myFileContentStr.split("\n")) {
						if (s.length() == 12) {
							listOfSerial.add(s);
						}
					}
				}
				myReader.close();
			} else {
				final String seed;
				if ((inArgs.length == 7) && ((inArgs[5].equals("-s")) || (inArgs[5].equals("--seed"))) && (inArgs[6].length() == 8)) {
					seed = inArgs[6];
				} else {
					seed = String.format("F%7s", Integer.toHexString((int) Math.floor(Math.random() * 268435455d))).replace(' ', '0');
				}
				for (int i = 0; i < number; i++) {
					listOfSerial.add(seed + String.format("%4s", Integer.toHexString(i)).replace(' ', '0'));
				}
			}
			int i = 0;
			int j = 0;
			final int total = listOfSerial.size();
			int nbMassive = total / split;
			if ((total % split) > 0) {
				nbMassive++;
			}
			if (nbMassive > number) {
				nbMassive = number;
			}
			while ((listOfSerial.size() > 0) && (i < number)) {
				final List<String> currentList = new LinkedList<String>();
				do {
					final String currentLogin = listOfSerial.poll();
					if (currentLogin == null) {
						break;
					}
					currentList.add(currentLogin);
				} while (((++i % split) != 0) && (i < number));
				System.err.println("[INIT & START] Massive client " + ++j + "/" + nbMassive);
				final JabberMassiveTestClient theClient = new JabberMassiveTestClient(domain, host, port, currentList);
				myJabberMassiveClients.add(theClient);
				theClient.start();

				int ready = 0;
				int alive = 0;
				for (final JabberMassiveTestClient myClient : myJabberMassiveClients) {
					myClient.generateClientsStat(2);
					ready += myClient.getNbClientsReady();
					alive += myClient.getNbClientsAlive();
				}
				System.err.println("Clients Stat (ready/alive/total) : " + ready + "/" + alive + "/" + total);
			}
			String lastStat = null;
			final int limitNotReady = (int) (total * 0.75);
			while (true) {
				int ready = 0;
				int alive = 0;
				final List<String> notReady = new LinkedList<String>();
				for (final JabberMassiveTestClient myClient : myJabberMassiveClients) {
					myClient.generateClientsStat(1);
					notReady.addAll(myClient.getClientsNotReady());
					ready += myClient.getNbClientsReady();
					alive += myClient.getNbClientsAlive();
				}
				final String actStat = ready + "/" + alive + "/" + total;
				if (!actStat.equals(lastStat)) {
					if (ready > limitNotReady) {
						for (final String in : notReady) {
							System.err.println("Not Ready : " + in);
						}
					}
					System.err.println("Clients Stat (ready/alive/total) : " + ready + "/" + alive + "/" + total);
					lastStat = actStat;
				}
				try {
					Thread.sleep(2000);
				} catch (final InterruptedException e) {}
			}
		}

	}
}
