package net.violet.platform.xmpp;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.util.Singleton;
import net.violet.platform.xmpp.packet.VioletApiPacket;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.packet.IQ.Type;

public final class IQApiQuery extends IQAbstractQuery<VioletApiPacket, Singleton<Object>> {

	private static final Logger LOGGER = Logger.getLogger(IQApiQuery.class);

	/*
	private static IQApiQuery getInstance(JabberPacketSender inSender, VioletApiPacket inApiPacket) {
		IQApiQuery theResult;
		final Map<String, IQApiQuery> theInstanceMap = IQAbstractQuery.getInstanceMap(IQApiQuery.class);
		synchronized (theInstanceMap) {
			theResult = theInstanceMap.get(inApiPacket.getPacketID());
			if (theResult == null) {
				theResult = new IQApiQuery(inSender);
				theInstanceMap.put(inApiPacket.getPacketID(), theResult);
			}
		}
		return theResult;
	}
	*/

	protected IQApiQuery(JabberPacketSender inComponent) {
		super(inComponent);
	}

	@Override
	protected void extractResultFromPacket(VioletApiPacket inApiPacket, Singleton<Object> inHolder) {

		if (inApiPacket.getType() == Type.RESULT) {
			try {
				inHolder.setElement(inApiPacket.getAPIResponse());

			} catch (final ConversionException e) {
				IQApiQuery.LOGGER.fatal(e, e);
			}

		} else if (inApiPacket.getType() == Type.ERROR) {
			inHolder.setElement(inApiPacket.getPojoError());
		}
	}

	protected static String getRequestIDPrefix() {
		return "api-";
	}

	/**
	 * Sends the packet using the given component and returns the result which
	 * is a Singleton containing an Object.
	 * 
	 * @param inComponentName
	 * @param inPacket
	 * @return
	 */
	public static Singleton<Object> sendPacket(String inComponentName, VioletApiPacket inPacket) {
		if (IQApiQuery.LOGGER.isDebugEnabled()) {
			IQApiQuery.LOGGER.debug("SENDING API PACKET :\n" + inPacket.toXML());
		}
		final Singleton<Object> theResult = new Singleton<Object>();
		IQAbstractQuery.doSendPacket(IQApiQuery.class, IQApiQuery.getRequestIDPrefix(), JabberComponentManager.getComponent(inComponentName), inPacket, theResult);
		return theResult;
	}

	@Override
	protected void setTimeoutError(Singleton<Object> inHolder) {
		inHolder.setElement(IQAbstractQuery.getPojoTimeoutError());
	}

	/*
	public static void notifyResult(JabberPacketSender inComponent, VioletApiPacket inPacket) {
		IQApiQuery.getInstance(inComponent, inPacket).doNotifyResult(inPacket);
	}
	*/

}
