package net.violet.platform.xmpp;

public class JabberXmlRpcManagerPacket {

	/**
	 * Mutex pour quitter proprement.
	 */
	private final Integer mQuitMutex = new Integer(0);

	/**
	 * Constructeur par défaut.
	 */
	public JabberXmlRpcManagerPacket() {
		// This space for rent.
	}

	public void run(String[] inArgs) throws InterruptedException {
		if (inArgs.length == 0) {
			JabberClientManager.getXmlRpcClient(null);
		} else {
			for (final String resource : inArgs) {
				JabberClientManager.getXmlRpcClient(resource);
			}
		}

		synchronized (this.mQuitMutex) {
			this.mQuitMutex.wait();
		}
	}

	public static void main(String[] inArgs) throws InterruptedException {
		final JabberXmlRpcManagerPacket theManager = new JabberXmlRpcManagerPacket();
		theManager.run(inArgs);
	}
}
