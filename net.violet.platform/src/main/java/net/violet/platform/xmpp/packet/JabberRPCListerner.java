package net.violet.platform.xmpp.packet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.IQ.Type;
import org.xml.sax.SAXException;

public class JabberRPCListerner implements PacketListener {


	private static final Logger LOGGER = Logger.getLogger(JabberRPCListerner.class);

	/**
	 * Constructeur par défaut.
	 */
	public JabberRPCListerner() {

	}

	public void processPacket(Packet inPacket) {
		doProcessPacket(inPacket);
	}

	/**
	 * @param inPacket
	 */
	private void doProcessPacket(Packet inPacket) {
		if (inPacket instanceof JabberRPC) {
			processJabberRPC((JabberRPC) inPacket);
			JabberRPCListerner.LOGGER.debug("PROCESS JABBERRPC : " + inPacket.toXML());
		}
	}

	/**
	 * Traitement du packet JabberRPC
	 * 
	 * @param inJabberRPC
	 */
	private void processJabberRPC(JabberRPC inJabberRPC) {
		if (inJabberRPC.getType() == Type.SET) {
			try {
				inJabberRPC.process();
			} catch (final SecurityException e) {
				JabberRPCListerner.LOGGER.fatal(e, e);
			} catch (final IllegalArgumentException e) {
				JabberRPCListerner.LOGGER.fatal(e, e);
			} catch (final XmlRpcException e) {
				JabberRPCListerner.LOGGER.fatal(e, e);
			} catch (final SAXException e) {
				JabberRPCListerner.LOGGER.fatal(e, e);
			} catch (final IOException e) {
				JabberRPCListerner.LOGGER.fatal(e, e);
			} catch (final ClassNotFoundException e) {
				JabberRPCListerner.LOGGER.fatal(e, e);
			} catch (final NoSuchMethodException e) {
				JabberRPCListerner.LOGGER.fatal(e, e);
			} catch (final IllegalAccessException e) {
				JabberRPCListerner.LOGGER.fatal(e, e);
			} catch (final InvocationTargetException e) {
				JabberRPCListerner.LOGGER.fatal(e, e);
			} catch (final InstantiationException e) {
				JabberRPCListerner.LOGGER.fatal(e, e);
			}
		}
	}

}
