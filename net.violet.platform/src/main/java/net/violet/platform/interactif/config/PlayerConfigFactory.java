package net.violet.platform.interactif.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory permettant de récupérer les configurations pour l'application lecture
 * d'un audio book.
 */
public class PlayerConfigFactory {

	private static final Map<Long, PlayerConfig> IAPLAYERCONFIG_MAP = new HashMap<Long, PlayerConfig>();

	static {
		// bouquin Gallimard
		PlayerConfigFactory.IAPLAYERCONFIG_MAP.put(GallimardConfig9782070548064.ISBN, new GallimardConfig9782070548064());
		PlayerConfigFactory.IAPLAYERCONFIG_MAP.put(GallimardConfig3760125572246.ISBN, new GallimardConfig3760125572246());
		PlayerConfigFactory.IAPLAYERCONFIG_MAP.put(GallimardConfig3760125572253.ISBN, new GallimardConfig3760125572253());
		PlayerConfigFactory.IAPLAYERCONFIG_MAP.put(GallimardConfig3760125572260.ISBN, new GallimardConfig3760125572260());
		PlayerConfigFactory.IAPLAYERCONFIG_MAP.put(GallimardConfig3760125572307.ISBN, new GallimardConfig3760125572307());

		// bouquin Penguin
		PlayerConfigFactory.IAPLAYERCONFIG_MAP.put(PenguinConfig3760125572345.ISBN, new PenguinConfig3760125572345());
		PlayerConfigFactory.IAPLAYERCONFIG_MAP.put(PenguinConfig3760125572352.ISBN, new PenguinConfig3760125572352());
		PlayerConfigFactory.IAPLAYERCONFIG_MAP.put(PenguinConfig3760125572369.ISBN, new PenguinConfig3760125572369());

		// bouquin "Cube"
		PlayerConfigFactory.IAPLAYERCONFIG_MAP.put(SimplePlayerConfig.ISBN, new SimplePlayerConfig());
	}

	public static PlayerConfig getConfig(Long inISBN) {
		return PlayerConfigFactory.IAPLAYERCONFIG_MAP.get(inISBN);
	}
}
