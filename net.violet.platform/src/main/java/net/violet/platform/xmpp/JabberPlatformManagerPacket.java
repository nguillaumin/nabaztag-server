package net.violet.platform.xmpp;

public class JabberPlatformManagerPacket {

	/**
	 * Mutex pour quitter proprement.
	 */
	private final Integer mQuitMutex = new Integer(0);

	/**
	 * Constructeur par défaut.
	 */
	public JabberPlatformManagerPacket() {
		// This space for rent.
	}

	public void run(String[] inArgs) throws InterruptedException {
		/*
		 * if (inArgs.length == 0) { JabberClientManager.getClient(null); } else
		 * { for (String resource : inArgs) {
		 * JabberClientManager.getClient(resource); } }
		 */

		if (inArgs.length == 0) {
			throw new InterruptedException("Client resource not specified");
		}

		for (final String resourceServer : inArgs) {
			JabberClientManager.getClient(resourceServer);
		}

		synchronized (this.mQuitMutex) {
			this.mQuitMutex.wait();
		}
	}

	public static void main(String[] inArgs) throws InterruptedException {
		final JabberPlatformManagerPacket theManager = new JabberPlatformManagerPacket();
		theManager.run(inArgs);
	}
}
