package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;

public interface TtsVoiceFactory extends RecordFactory<TtsVoice> {

	List<TtsVoice> findAll();

	/**
	 * Search the tts voice by command and str fields
	 * 
	 * @param inVoiceLibOrCommandName
	 * @return NULL if none of the search succedeed
	 */
	TtsVoice findByCommandOrName(String inVoiceStrOrCommandName);

	/**
	 * Search the tts voice by command and str fields or by language if none is
	 * found, find the default voice for the default language
	 * 
	 * @param voice
	 * @param language default language to use when no correspondance is found
	 * @return NON NULL
	 */
	TtsVoice findByCommandOrName(String inVoiceName, long language);

	/**
	 * on recupère la synthèse grâce à la commande
	 * 
	 * @param inVoice
	 * @return
	 */
	TtsVoice findByName(String inVoice);

	/**
	 * récupère la synthèse grâce à la langue sinon il renvoie la langue us par
	 * défaut
	 * 
	 * @param inLang
	 * @return
	 */
	TtsVoice findByLang(Lang inLang);

	List<TtsVoice> findAllByLang(Lang inLang);

	TtsVoice findByLang(long langId);

}
