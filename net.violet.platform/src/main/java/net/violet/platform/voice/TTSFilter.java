package net.violet.platform.voice;

import net.violet.platform.util.StringShop;
import net.violet.platform.util.StringTools;

/**
 * Classe pour filtrer le texte avant de le passer au TTS.
 */
public final class TTSFilter {

	/**
	 * Filtre pour les titres des news (RSS).
	 * 
	 * @param inString titre de la news
	 * @return la nouvelle chaîne, filtrée.
	 */
	public static String filterNewsTitle(String inString) {
		// Ajout d'un point s'il n'y a pas de ponctuation à la fin.

		final String titleEncoded = StringTools.unescapeString(inString);

		return TTSFilter.removeDash(TTSFilter.addPeriod(titleEncoded));
	}

	/**
	 * Ajout d'un point s'il n'y a pas de ponctuation à la fin.
	 * 
	 * @param inString chaîne à modifier
	 * @return la nouvelle chaîne, filtrée.
	 */
	private static String addPeriod(String inString) {
		String theResult = inString;
		if (!theResult.endsWith(".") && !theResult.endsWith("?") && !theResult.endsWith("!") && !theResult.endsWith(";")) {
			theResult += ".";
		}
		return theResult;
	}

	/**
	 * Suppression des - au milieu.
	 * 
	 * @param inString chaîne à modifier
	 * @return la nouvelle chaîne, filtrée.
	 */
	private static String removeDash(String inString) {
		return inString.replaceAll("(-|\\*)", StringShop.SPACE);
	}
}
