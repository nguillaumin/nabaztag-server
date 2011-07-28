package net.violet.platform.xmpp;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.packet.IQResourcesPacket;

import org.jivesoftware.smack.packet.IQ.Type;

/**
 * Classe pour déterminer les ressources d'un client XMPP.
 */
public final class IQResourcesQuery extends IQAbstractQuery<IQResourcesPacket, List<String>> {

	/*
	protected static IQResourcesQuery getInstance(IQResourcesPacket inPacket) {
		return IQResourcesQuery.getInstance(Constantes.XMPP_PLATFORM_COMPONENT, inPacket);
	}

	protected static IQResourcesQuery getInstance(String componentName, IQResourcesPacket inPacket) {
		return IQResourcesQuery.getInstance(JabberComponentManager.getComponent(componentName), inPacket);
	}

	protected static IQResourcesQuery getInstance(JabberPacketSender inSender, IQResourcesPacket inPacket) {
		IQResourcesQuery theResult;
		final Map<String, IQResourcesQuery> theInstanceMap = IQAbstractQuery.getInstanceMap(IQResourcesQuery.class);
		synchronized (theInstanceMap) {
			theResult = theInstanceMap.get(inPacket.getPacketID());
			if (theResult == null) {
				theResult = new IQResourcesQuery(inSender);
				theInstanceMap.put(inPacket.getPacketID(), theResult);
			}
		}
		return theResult;
	}
	*/

	protected IQResourcesQuery(JabberPacketSender inSender) {
		super(inSender);
	}

	/**
	 * Envoie une requête.
	 */
	public static List<String> getClientResources(String inXMPPAddress) {
		final List<String> theResult = new ArrayList<String>();

		synchronized (theResult) {
			// Envoi du message.
			final IQResourcesPacket theRequest = new IQResourcesPacket();
			theRequest.setFrom(JabberComponentManager.getComponentDefaultFromAddress(Constantes.XMPP_PLATFORM_COMPONENT));
			theRequest.setTo(inXMPPAddress);
			theRequest.setType(Type.GET);

			IQAbstractQuery.doSendPacket(IQResourcesQuery.class, IQResourcesQuery.getRequestIDPrefix(), JabberComponentManager.getComponent(Constantes.XMPP_PLATFORM_COMPONENT), theRequest, theResult);
		}
		return theResult;
	}

	/*
	public static <T extends Packet> void notifyResult(IQResourcesPacket inPacket) {
		IQResourcesQuery.getInstance(inPacket).doNotifyResult(inPacket);
	}
	*/

	@Override
	protected void extractResultFromPacket(IQResourcesPacket inPacket, List<String> inHolder) {
		if (inPacket.getType() == Type.RESULT) {
			// récupère les ressources
			inHolder.addAll(inPacket.getResources());
		} // en cas d'erreur, rien à faire, la liste est déjà vide.
	}

	protected static String getRequestIDPrefix() {
		return "rsrc-";
	}

	@Override
	protected void setTimeoutError(List<String> inHolder) {
		// TODO Auto-generated method stub
	}
}
