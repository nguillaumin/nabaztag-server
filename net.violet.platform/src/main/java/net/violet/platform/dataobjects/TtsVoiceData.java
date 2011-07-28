package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.factories.Factories;

public final class TtsVoiceData extends RecordData<TtsVoice> {

	private static final Map<String, List<TtsVoiceData>> ALL_VOICES_BY_ISOCODE = new HashMap<String, List<TtsVoiceData>>();

	public TtsVoiceData(TtsVoice inTtsVoice) {
		super(inTtsVoice);
	}

	public static TtsVoiceData findTtsVoiceByName(String inVoiceName) {
		return new TtsVoiceData(Factories.TTSVOICE.findByName(inVoiceName));
	}

	public static TtsVoiceData findTtsVoiceByLang(TtsLangData inLang) {
		return new TtsVoiceData(Factories.TTSVOICE.findByLang(inLang.getReference()));
	}

	public Lang getLang() {
		final TtsVoice theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getLang();
		}
		return null;
	}

	public String getVoice() {
		final TtsVoice theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getTtsvoice_str();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getLibelle() {
		final TtsVoice theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getTtsvoice_libelle();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static List<TtsVoiceData> getAllVoiceByLang(TtsLangData inLangData) {
		final Lang theLang = inLangData.getReference();
		List<TtsVoiceData> theResult = TtsVoiceData.ALL_VOICES_BY_ISOCODE.get(theLang.getIsoCode());
		if (theResult == null) {
			theResult = new ArrayList<TtsVoiceData>();
			for (final TtsVoice theTtsvoice : Factories.TTSVOICE.findAllByLang(theLang)) {
				theResult.add(new TtsVoiceData(theTtsvoice));
			}

			TtsVoiceData.ALL_VOICES_BY_ISOCODE.put(theLang.getIsoCode(), theResult);
		}
		return theResult;
	}

	public static List<TtsVoiceData> getAllVoices() {

		final List<TtsVoiceData> lstVoices = new ArrayList<TtsVoiceData>(32);

		for (final TtsVoice theTtsvoice : Factories.TTSVOICE.findAll()) {
			lstVoices.add(new TtsVoiceData(theTtsvoice));
		}

		return lstVoices;
	}

	public TtsVoice getReference() {
		return getRecord();
	}

	/**
	 * Permet de récupèrer une voix de tts avec les différents paramètres passés. Tout d'abord 
	 * on regarde si on peux le trouver grâce au nom de la voix, sinon on essaye avec la langue isocode,
	 * sinon on le détermine avec le texte ou la langue de l'utilisateur qui envoi le message. 
	 *  
	 * @param inVoiceName null ou nom de la voix
	 * @param inIsocode null ou langue isocode court
	 * @param inText null ou texte a synthétiser
	 * @param inUserLang null ou langue de l'utilisateur
	 * @return
	 * @throws InvalidParameterException On n'a pas pu récupèrér une voix de tts avec les paramètres passés
	 */
	public static TtsVoiceData getByParams(String inVoiceName, String inIsocode, String inText, SiteLangData inUserLang) throws InvalidParameterException {
		TtsVoiceData result = null;

		if (inVoiceName != null) { // une voix nous est indiqué, on va la rechercher
			result = TtsVoiceData.findTtsVoiceByName(inVoiceName);
		}

		if ((result == null) || result.isEmpty()) {
			// pas de voix ou mauvaise voix
			final TtsLangData languageData;

			if (inIsocode != null) {
				languageData = TtsLangData.getDefaultTtsLanguage(inIsocode);

			} else {// on a pas la langue ni la commande alors on va
				// l'identifier avec le texte
				// si on arrive pas a définir la langue , on prend celui de
				// l'envoyeur
				if ((inUserLang != null) && inUserLang.isValid()) {
					languageData = TtsLangData.getIdentifyLanguage(inText, inUserLang);
				} else {
					languageData = TtsLangData.getIdentifyLanguage(inText);
				}

			}
			result = TtsVoiceData.findTtsVoiceByLang(languageData);
		}

		if (result.isEmpty()) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_VOICE_COMMAND, net.violet.common.StringShop.EMPTY_STRING);
		}

		return result;
	}
}
