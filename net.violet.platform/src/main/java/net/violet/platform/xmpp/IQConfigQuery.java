package net.violet.platform.xmpp;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.util.Constantes;
import net.violet.platform.xmpp.packet.IQCommandPacket;

import org.jivesoftware.smack.packet.IQ.Type;

/**
 * Classe pour déterminer la configuration d'un client xmpp (lapin)
 */
public final class IQConfigQuery extends IQAbstractQuery<IQCommandPacket, Map<String, String>> {

	/**
	 * Les deux types d'infos de configuration qu'on peut demander au lapin :
	 * configuration statique ou état courant
	 * 
	 * @author pgerlach
	 */
	public enum ConfigType {
		staticConfig {

			@Override
			public IQCommandPacket.Type getCommandPacketType() {
				return IQCommandPacket.Type.getConfig;
			}
		},
		runningState {

			@Override
			public IQCommandPacket.Type getCommandPacketType() {
				return IQCommandPacket.Type.getRunningState;
			}
		};

		public abstract IQCommandPacket.Type getCommandPacketType();
	};

	/*
	protected static IQConfigQuery getInstance(IQCommandPacket inPacket) {
		return IQConfigQuery.getInstance(Constantes.XMPP_PLATFORM_COMPONENT, inPacket);
	}

	protected static IQConfigQuery getInstance(String componentName, IQCommandPacket inPacket) {
		return IQConfigQuery.getInstance(JabberComponentManager.getComponent(componentName), inPacket);
	}

	protected static IQConfigQuery getInstance(JabberPacketSender inSender, IQCommandPacket inPacket) {
		IQConfigQuery theResult;
		final Map<String, IQConfigQuery> theInstanceMap = IQAbstractQuery.getInstanceMap(IQConfigQuery.class);
		synchronized (theInstanceMap) {
			theResult = theInstanceMap.get(inPacket.getPacketID());
			if (theResult == null) {
				theResult = new IQConfigQuery(inSender);
				theInstanceMap.put(inPacket.getPacketID(), theResult);
			}
		}
		return theResult;
	}
	*/

	protected IQConfigQuery(JabberPacketSender inSender) {
		super(inSender);
	}

	/**
	 * Envoie un requête de demande de configuration au lapin et attend la
	 * réponse
	 * 
	 * @param inXMPPAddress adresse xmpp du lapin
	 * @param ressource la ressource actuelle du lapin
	 * @param type le type de demande qu'on veut faire, getConfig ou
	 *            getRunningState
	 * @return
	 */
	public static Map<String, String> getClientConfig(String inXMPPAddress, String ressource, ConfigType type) {

		final Map<String, String> theResult = new HashMap<String, String>();

		final IQCommandPacket theRequest = new IQCommandPacket(type.getCommandPacketType());
		theRequest.setFrom(JabberComponentManager.getComponentDefaultFromAddress(Constantes.XMPP_PLATFORM_COMPONENT));
		theRequest.setTo(inXMPPAddress + "/" + ressource);
		theRequest.setType(Type.SET);

		IQAbstractQuery.doSendPacket(IQConfigQuery.class, IQConfigQuery.getRequestIDPrefix(), JabberComponentManager.getComponent(Constantes.XMPP_PLATFORM_COMPONENT), theRequest, theResult);

		return theResult;
	}

	/*
	public static <T extends Packet> void notifyResult(IQCommandPacket inPacket) {
		IQConfigQuery.getInstance(inPacket).doNotifyResult(inPacket);
	}
	*/

	@Override
	protected void extractResultFromPacket(IQCommandPacket inPacket, Map<String, String> inHolder) {
		if (inPacket.getType() == Type.RESULT) {
			// récupère les configs
			inHolder.putAll(inPacket.getInfos());
		} // en cas d'erreur, rien à faire, la liste est déjà vide.

	}

	protected static String getRequestIDPrefix() {
		return "cfg-";
	}

	@Override
	protected void setTimeoutError(Map<String, String> inHolder) {
		// TODO Auto-generated method stub		
	}

}
