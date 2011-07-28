package net.violet.platform.otp;

import net.violet.platform.xmpp.IQAbstractQuery;
import net.violet.platform.xmpp.JabberComponent;
import net.violet.platform.xmpp.packet.JabberComponentAppletPacketListener;
import net.violet.platform.xmpp.packet.VioletApiPacket;

import org.apache.log4j.Logger;

import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangAtom;
import com.ericsson.otp.erlang.OtpErlangDecodeException;
import com.ericsson.otp.erlang.OtpErlangExit;
import com.ericsson.otp.erlang.OtpErlangList;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpErlangTuple;
import com.ericsson.otp.erlang.OtpPeer;

public class OtpApiPacketListener implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(OtpApiPacketListener.class);

	final JabberComponent component;

	public OtpApiPacketListener(JabberComponent inComponent) {
		this.component = inComponent;
	}

	public void run() {
		while (true) {
			try {

				final OtpConnection otpConnection = this.component.getErlangNode().connect(new OtpPeer(this.component.getErlangPid().node()));

				final OtpErlangObject received = otpConnection.receive();

				if ((received != null) && (received instanceof OtpErlangTuple)) {

					final OtpErlangTuple msg = (OtpErlangTuple) received;

					if ((msg.arity() == 2)) {
						final String packetId = ((OtpErlangList) msg.elementAt(0)).toString();
						final String xmlPacket = ((OtpErlangList) msg.elementAt(1)).toString();

						// Delete entry into mnesia
						otpConnection.sendRPC("mnesia", "dirty_delete", new OtpErlangObject[] { new OtpErlangAtom(IQAbstractQuery.PENDING_REQUEST_RECORD), new OtpErlangList(packetId) });

						// Processing XMPP packet
						final VioletApiPacket packet = VioletApiPacket.getVioletApiPacket(xmlPacket);
						JabberComponentAppletPacketListener.processVioletApiPacket(this.component, packet);
					}
				}

				otpConnection.close();

			} catch (final OtpErlangExit e) {
				OtpApiPacketListener.LOGGER.fatal(e, e);
			} catch (final OtpErlangDecodeException e) {
				OtpApiPacketListener.LOGGER.fatal(e, e);
			} catch (final Exception e) {
				OtpApiPacketListener.LOGGER.info(e, e);
			}
		}
	}

}
