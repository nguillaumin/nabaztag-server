package net.violet.platform.xmpp;

import java.util.List;
import java.util.Map;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.xmpp.packet.VioletAppletPacket;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.IQ.Type;

public final class IQAppletQuery extends IQAbstractQuery<VioletAppletPacket, List<Map<String, Object>>> {

	private static final Logger LOGGER = Logger.getLogger(IQAppletQuery.class);

	/*
	protected static IQAppletQuery getInstance(String componentName, VioletAppletPacket inPacket) {
		return IQAppletQuery.getInstance(JabberComponentManager.getComponent(componentName), inPacket);
	}

	protected static IQAppletQuery getInstance(JabberPacketSender inSender, VioletAppletPacket inPacket) {
		IQAppletQuery theResult;
		final Map<String, IQAppletQuery> theInstanceMap = IQAbstractQuery.getInstanceMap(IQAppletQuery.class);
		synchronized (theInstanceMap) {
			theResult = theInstanceMap.get(inPacket.getPacketID());
			if (theResult == null) {
				theResult = new IQAppletQuery(inSender);
				theInstanceMap.put(inPacket.getPacketID(), theResult);
			}
		}
		return theResult;
	}
	*/

	protected IQAppletQuery(JabberPacketSender inSender) {
		super(inSender);
	}

	@Override
	protected void extractResultFromPacket(VioletAppletPacket inPacket, List<Map<String, Object>> inHolder) {

		if (inPacket.getType() == Type.RESULT) {
			try {
				inHolder.addAll(inPacket.getAppResponse());

			} catch (final ConversionException e) {
				IQAppletQuery.LOGGER.fatal(e, e);
			}

		} else if (inPacket.getType() == Type.ERROR) {
			inHolder.add(inPacket.getPojoError());
		}
	}

	protected static String getRequestIDPrefix() {
		return "applet-";
	}

	/**
	 * Sends the packet and returns the result which is a list of messages (i.e.
	 * a list of map objects with strings as keys and objects as values)
	 * 
	 * @param inPacket
	 * @return a list of messages
	 */
	/*
	public static List<Map<String, Object>> sendPacket(String inComponentName, VioletAppletPacket inPacket) {
		final List<Map<String, Object>> theResult = new ArrayList<Map<String, Object>>();
		IQAbstractQuery.doSendPacket(IQAppletQuery.class, IQAppletQuery.getRequestIDPrefix(), JabberComponentManager.getComponent(inComponentName), inPacket, theResult);
		return theResult;
	}
	*/

	public static void simpleSendPacket(String inComponentName, VioletAppletPacket inPacket) {
		inPacket.setPacketID(IQAppletQuery.getRequestIDPrefix() + System.currentTimeMillis() + Math.random());
		(new IQAppletQuery(JabberComponentManager.getComponent(inComponentName))).getSender().sendPacket(inPacket);
	}

	@Override
	protected void setTimeoutError(List<Map<String, Object>> inHolder) {
		inHolder.add(IQAbstractQuery.getPojoTimeoutError());
	}

	/*
	public static void notifyResult(JabberPacketSender inSender, VioletAppletPacket inPacket) {
		IQAppletQuery.getInstance(inSender, inPacket).doNotifyResult(inPacket);
	}
	*/

}
