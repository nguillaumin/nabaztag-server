package net.violet.platform.xmpp;

import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.packet.JabberComponentApiPacketListener;
import net.violet.platform.xmpp.packet.JabberComponentAppletPacketListener;
import net.violet.platform.xmpp.packet.JabberComponentObjectsPacketListener;
import net.violet.platform.xmpp.packet.JabberComponentPlatformPacketListener;

public class JabberComponentManagerPacket {

	/**
	 * Mutex pour quitter proprement.
	 */
	private final Integer mQuitMutex = new Integer(0);

	/**
	 * Constructeur par défaut.
	 */
	public JabberComponentManagerPacket() {
		// This space for rent.
	}

	public void run(String[] inArgs) throws InterruptedException {
		if (inArgs.length == 0) {
			throw new InterruptedException("Component name not specified");
		}

		for (final String name : inArgs) {
			if (Constantes.XMPP_API_COMPONENT.equals(name)) {
				new JabberComponentApiPacketListener(JabberComponentManager.getComponent(name));
			} else if (Constantes.XMPP_APPLET_COMPONENT.equals(name)) {
				new JabberComponentAppletPacketListener(JabberComponentManager.getComponent(name));
			} else if (Constantes.XMPP_OBJECTS_COMPONENT.equals(name)) {
				new JabberComponentObjectsPacketListener(JabberComponentManager.getComponent(name));
			} else if (Constantes.XMPP_PLATFORM_COMPONENT.equals(name)) {
				new JabberComponentPlatformPacketListener(JabberComponentManager.getComponent(name));
			}
		}

		synchronized (this.mQuitMutex) {
			this.mQuitMutex.wait();
		}
	}

	public static void main(String[] inArgs) throws InterruptedException {
		final JabberComponentManagerPacket theManager = new JabberComponentManagerPacket();
		theManager.run(inArgs);
	}
}
