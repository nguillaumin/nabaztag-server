package net.violet.platform.xmpp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.util.Constantes;
import net.violet.platform.util.Pair;
import net.violet.platform.util.Triplet;

import org.apache.log4j.Logger;

/**
 * Classe pour gérer les clients jabber dans un process java donné.
 */
public class JabberComponentManager {

	private static final Logger LOGGER = Logger.getLogger(JabberComponentManager.class);

	/**
	 * Référence sur les clients, par ressource.
	 */
	private static final Map<String, JabberComponent> JABBER_COMPONENTS = new HashMap<String, JabberComponent>();

	/**
	 * Accesseur sur le component à partir de son nom.
	 * 
	 * @return le component jabber.
	 */
	public static JabberComponent getComponent(String inName) {

		if (JabberComponentManager.LOGGER.isDebugEnabled()) {
			JabberComponentManager.LOGGER.debug("Component to get : " + inName);
		}

		JabberComponent component = null;

		if (inName != null) {
			synchronized (JabberComponentManager.JABBER_COMPONENTS) {
				component = JabberComponentManager.JABBER_COMPONENTS.get(inName);

				if (component == null) {
					final Triplet<List<Pair<String, Integer>>, String, String> config = Constantes.XMPP_COMPONENTS_CONFIG_LIST.get(inName);
					if (config != null) {
						component = new JabberComponent(config.getFirst(), config.getSecond(), config.getThird());
						JabberComponentManager.JABBER_COMPONENTS.put(inName, component);
					}
				}
			}
		}

		if (JabberComponentManager.LOGGER.isDebugEnabled()) {
			JabberComponentManager.LOGGER.debug("Component returned : " + component);
		}
		return component;
	}

	public static void shutdown() {
		synchronized (JabberComponentManager.JABBER_COMPONENTS) {
			for (final JabberComponent theComponent : JabberComponentManager.JABBER_COMPONENTS.values()) {
				theComponent.close();
			}
		}
	}

	public static String getComponentDefaultFromAddress(String inName) {
		return JabberComponent.getDefaultFromAddress(inName);
	}

}
