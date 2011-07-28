package net.violet.platform.xmpp.management;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import net.violet.platform.message.MessageSerializer;

import org.jivesoftware.smack.util.Base64;

public class JabberRebootEngine implements JabberLightClientHandler {

	public JabberRebootEngine(String inDomain, String inHost, String inPassword, String inMode, Queue<String> inSerial) {
		try {
			final JabberLightClient theClient = new JabberLightClient(inDomain, inHost, 5222, this, "reboot");
			theClient.login("net.violet.platform", inPassword);
			while (theClient.getState() != JabberLightClient.READY) {
				theClient.processSocketData();
			}
			final String ampRules;
			if (!inMode.equals("urgent")) {
				ampRules = "<rule condition='match-resource' action='other' value='store'/>";
			} else {
				ampRules = net.violet.common.StringShop.EMPTY_STRING;
			}
			while (inSerial.size() > 0) {
				final String thePacket = "<message id='ZeReb00t-" + System.currentTimeMillis() + "' to='" + inSerial.poll() + "@" + inDomain + "/" + inMode + "' from='net.violet.platform@" + inDomain + "/reboot'>" + "<amp xmlns='http://jabber.org/protocol/amp'>" + "<rule condition='deliver' action='drop' value='stored'/>" + ampRules + "</amp>" + "<packet xmlns='violet:packet' format='1.0' ttl='120'>" + Base64.encodeBytes(MessageSerializer.REBOOT_PACKET) + "</packet>" + "</message>";
				theClient.write(thePacket);
			}

			final long endTime = System.currentTimeMillis() + 1000;
			while (System.currentTimeMillis() <= endTime) {
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException e) {}
			}

			System.out.println("OK !");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void handleReceivedPacket(JabberLightClient inFromSocket, String inPacket) {
		System.out.println("S: " + inPacket);
	}

	private static void print_usage() {
		System.err.println("JabberRebootEngine Domain Host Password Mode[urgent/idle/asleep] [MAC]");
		System.err.println("Use MAC argument for one rabbit, or a list of MAC separated by newline in standard input.");
		System.exit(42);
	}

	public static void main(String[] inArgs) {
		if (inArgs.length >= 4) {
			final String theDomain = inArgs[0];
			final String theHost = inArgs[1];
			final String thePassword = inArgs[2];
			final String theMode = inArgs[3];

			if ((!theMode.equals("urgent")) && (!theMode.equals("idle")) && (!theMode.equals("asleep"))) {
				JabberRebootEngine.print_usage();
			}

			final Queue<String> serialList = new LinkedList<String>();
			if (inArgs.length > 4) {
				for (int indexArgs = 4; indexArgs < inArgs.length; indexArgs++) {
					serialList.add(inArgs[indexArgs]);
				}
			} else {
				final byte[] theBytes = new byte[8192];
				final StringBuilder theInput = new StringBuilder();
				try {
					while (System.in.read(theBytes) >= 0) {
						theInput.append(theBytes);
					}
				} catch (final IOException e) {
					e.printStackTrace();
				}
				for (final String serial : theInput.toString().split("\n")) {
					serialList.add(serial);
				}
			}
			new JabberRebootEngine(theDomain, theHost, thePassword, theMode, serialList);
		} else {
			JabberRebootEngine.print_usage();
		}
	}
}
