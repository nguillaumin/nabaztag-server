package net.violet.platform.dataobjects;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.TtsVoiceImpl;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class VoiceData extends RecordData<TtsVoice> implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(VoiceData.class);

	public static VoiceData getData(TtsVoice inVoice) {
		try {
			return RecordData.getData(inVoice, VoiceData.class, TtsVoice.class);
		} catch (final InstantiationException e) {
			VoiceData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			VoiceData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			VoiceData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			VoiceData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Liste des voix mappée par langue Cette map n'est pas modifiée, et par
	 * conséquent on peut la garder en cache.
	 */
	private static final Map<Lang, List<VoiceData>> voiceListByLang = VoiceData.createVoiceList();

	/**
	 * Permet de récupérer la liste des voix textToSpeech classées par Langue
	 * 
	 * @return une liste d'objet VoiceData.
	 */
	private static Map<Lang, List<VoiceData>> createVoiceList() {
		final Map<Lang, List<VoiceData>> listeVoix = new HashMap<Lang, List<VoiceData>>();;

		for (final TtsVoice tts : TtsVoiceImpl.getAllVoices()) {
			final VoiceData voiceData = VoiceData.getData(tts);
			final Lang lang = Factories.LANG.find(voiceData.getLangue());
			final List<VoiceData> tempListVoice = new ArrayList<VoiceData>();

			if (listeVoix.containsKey(lang)) {
				tempListVoice.addAll(listeVoix.get(lang));
			}

			tempListVoice.add(voiceData);

			listeVoix.put(lang, tempListVoice);
		}
		return listeVoix;
	}

	/**
	 * Get the list of a selected list of TTS voices in a selection of
	 * languages, in the order of the given list.
	 * 
	 * @param langList : the id(s) of the languages wanted
	 * @return the list
	 */
	public static List<VoiceData> getVoiceList(List<Lang> langList) {
		final List<VoiceData> theResult = new LinkedList<VoiceData>();
		for (final Lang theLang : langList) {
			if (VoiceData.voiceListByLang.containsKey(theLang)) {
				theResult.addAll(VoiceData.voiceListByLang.get(theLang));
			}
		}

		return theResult;
	}

	/**
	 * USED BY REFLECTION
	 * 
	 * @param ttsvoice
	 */
	protected VoiceData(TtsVoice ttsvoice) {
		super(ttsvoice);
	}

	public String getCommand() {
		final TtsVoice theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getTtsvoice_command() != null)) {
			return theRecord.getTtsvoice_command();
		}
		return StringShop.EMPTY_STRING;
	}

	public long getLangue() {
		final TtsVoice theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getTtsvoice_lang();
		}
		return 0;
	}

	public long getIdVoix() {
		final TtsVoice theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getTtsvoice_id();
		}
		return 2;
	}

	public String getLibelles() {
		final TtsVoice theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getTtsvoice_libelle() != null)) {
			return theRecord.getTtsvoice_libelle();
		}
		return StringShop.EMPTY_STRING;
	}
}
