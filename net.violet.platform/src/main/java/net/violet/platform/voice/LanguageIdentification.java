package net.violet.platform.voice;

import org.apache.nutch.analysis.lang.LanguageIdentifier;
import org.apache.nutch.util.NutchConfiguration;

/**
 * Classe pour l'identification de la langue.
 */
public final class LanguageIdentification {

	/**
	 * Plug-in.
	 */
	private static final LanguageIdentifier IDENTIFIER = new LanguageIdentifier(NutchConfiguration.create());

	/**
	 * Constructeur par défaut indisponible.
	 */
	private LanguageIdentification() {
		// This space for rent.
	}

	/**
	 * Identifie du texte et retourne la langue de ce texte.
	 * 
	 * @param inString chaîne dont on veut la langue.
	 * @return code IETF (court) de la langue pour cette chaîne.
	 */
	public static String identifyLanguage(String inString) {
		final String theResult;
		synchronized (LanguageIdentification.IDENTIFIER) {
			final String theNutchGuessedLanguage = LanguageIdentification.IDENTIFIER.identify(inString);
			theResult = theNutchGuessedLanguage;
		}
		return theResult;
	}
}
