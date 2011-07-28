package net.violet.platform.datamodel.cache;

import net.violet.db.cache.CacheMessage;
import net.violet.db.cache.CacheNode;
import net.violet.db.cache.CacheNodeListener;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test de charge pour les noeuds du cache.
 */
public final class CacheNodeTest extends Thread implements CacheNodeListener {

	private final CacheNode mNode;
	private final long mNbMsg;
	private final long mNbNodes;
	private long mNbCurrentNodes;
	private long mNbReceived;

	private static final class NodeTestMessage implements CacheMessage {

		private NodeTestMessage() {
			// This space for rent.
		}
	}

	private CacheNodeTest(CacheNode inNode, long inNbMsg, long inNbNodes) {
		this.mNode = inNode;
		this.mNbMsg = inNbMsg;
		this.mNbNodes = inNbNodes;
		this.mNode.setListener(this);
	}

	@Override
	public void run() {
		System.out.println("Running...");
		synchronized (this) {
			do {
				this.mNbCurrentNodes = this.mNode.getMembers().length;
				for (final String theNode : this.mNode.getMembers()) {
					System.out.println("Node : " + theNode);
				}
				System.out.println("mNbCurrentNodes = " + this.mNbCurrentNodes + ", mNbNodes = " + this.mNbNodes);
				try {
					wait(1000);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			} while (this.mNbCurrentNodes != this.mNbNodes);
		}
		System.out.println("All members are here");
		try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

		final long totalExpectedMessages = (this.mNbNodes - 1) * this.mNbMsg;

		System.out.println("Sending " + this.mNbMsg + " messages");

		final long before = System.currentTimeMillis();

		for (long indexMsg = 0; indexMsg < this.mNbMsg; indexMsg++) {
			this.mNode.broadcastMessage(new NodeTestMessage());
		}

		System.out.println("Sent all messages");

		synchronized (this) {
			while (this.mNbReceived < totalExpectedMessages) {
				if ((this.mNbReceived % 10000 == 0) && (this.mNbReceived > 0)) {
					System.out.println("Received " + this.mNbReceived + " messages.");
				}
				try {
					wait();
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		final long after = System.currentTimeMillis();

		System.out.println("Total time : " + (after - before) + " - " + (((float) this.mNbReceived / (float) (after - before)) * 1000) + "msg/s");

		try {
			Thread.sleep(10000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

		this.mNode.close();
	}

	public static void main(String[] inArgs) {
		if (inArgs.length < 3) {
			System.err.println("Usage: CacheNodeTest CacheNode nb_msg nb_nodes");
			System.exit(0);
		}

		try {
			final Class theClass = Class.forName(inArgs[0]);
			final long nbMsg = Long.parseLong(inArgs[1]);
			final long nbNodes = Long.parseLong(inArgs[2]);
			final CacheNode theNode = (CacheNode) theClass.newInstance();
			final CacheNodeTest theTest = new CacheNodeTest(theNode, nbMsg, nbNodes);
			theTest.start();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final ClassCastException e) {
			e.printStackTrace();
		}
	}

	public void receiveMessage(CacheMessage inMessage) {
		synchronized (this) {
			this.mNbReceived++;
			notify();
		}
	}

	public void addedMember(String inName) {
		System.out.println("member added : " + inName);
		synchronized (this) {
			this.mNbCurrentNodes = this.mNode.getMembers().length;
			notify();
		}
	}

	public void disappearedMember(String inName) {
		System.out.println("member disappeared : " + inName);
		synchronized (this) {
			this.mNbCurrentNodes = this.mNode.getMembers().length;
		}
	}

	@Test
	public void CacheNode() {
		Assert.assertTrue(true);
	}
}
