package net.violet.platform.xmpp.packet;

import net.violet.common.utils.concurrent.BlockingExecutorLight;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;

public abstract class AbstractPacketListener implements PacketListener {

	private static final Logger LOGGER = Logger.getLogger(AbstractPacketListener.class);

	private final BlockingExecutorLight<Packet> mBlockingExecutor;

	protected AbstractPacketListener(int inNbThreads) {
		this.mBlockingExecutor = new BlockingExecutorLight<Packet>(inNbThreads, new Worker<Packet>() {

			public void process(Packet inPacket) {
				doProcessPacket(inPacket);
			}

		}, "Packet-Listener");
	}

	public final void processPacket(final Packet inPacket) {
		try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Received packet: " + inPacket.toXML());
			}
			this.mBlockingExecutor.put(inPacket);
		} catch (final InterruptedException e) {
			AbstractPacketListener.LOGGER.fatal(e, e);
		}
	}

	protected abstract void doProcessPacket(Packet inPacket);

	public void shutdown() {
		this.mBlockingExecutor.quit();
	}

}
