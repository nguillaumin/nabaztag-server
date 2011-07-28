package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.List;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.datamodel.factories.Factories;

public class TtsVoiceImpl extends ObjectRecord<TtsVoice, TtsVoiceImpl> implements TtsVoice {

	public static final SQLObjectSpecification<TtsVoiceImpl> SPECIFICATION = new SQLObjectSpecification<TtsVoiceImpl>("ttsvoice", TtsVoiceImpl.class, new SQLKey("ttsvoice_id"));

	private final SingleAssociationNotNull<TtsVoice, Lang, LangImpl> mLang;

	protected TtsVoiceImpl(long id) throws SQLException {
		init(id);
		this.mLang = new SingleAssociationNotNull<TtsVoice, Lang, LangImpl>(this, "ttsvoice_lang", LangImpl.SPECIFICATION);
	}

	protected TtsVoiceImpl() {
		this.mLang = new SingleAssociationNotNull<TtsVoice, Lang, LangImpl>(this, "ttsvoice_lang", LangImpl.SPECIFICATION);
	}

	protected long ttsvoice_id;
	protected String ttsvoice_libelle;
	protected long ttsvoice_lang;
	protected String ttsvoice_command;
	protected String ttsvoice_str;
	protected int ttsvoice_flags;

	@Override
	public Long getId() {
		return getTtsvoice_id();
	}

	@Override
	public SQLObjectSpecification<TtsVoiceImpl> getSpecification() {
		return TtsVoiceImpl.SPECIFICATION;
	}

	public final long getTtsvoice_id() {
		return this.ttsvoice_id;
	}

	public final String getTtsvoice_libelle() {
		return this.ttsvoice_libelle;
	}

	public final long getTtsvoice_lang() {
		return this.ttsvoice_lang;
	}

	public final String getTtsvoice_command() {
		return this.ttsvoice_command;
	}

	public final String getTtsvoice_str() {
		return this.ttsvoice_str;
	}

	public final boolean isDefault() {
		return (this.ttsvoice_flags & TtsVoice.FLAG_DEFAULT) != 0;
	}

	/**
	 * récupère les options d'une voix de tts par son nom (ttsvoice_str)
	 * 
	 * @param inName : ex => Claire
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static TtsVoice findByName(String inName) {
		return TtsVoice.ALL_VOICES_BY_STR.get(inName);
	}

	/**
	 * récupère les options d'une voix de tts par sa commande (ttsvoice_command)
	 * 
	 * @param inCommand : ex => Claire22s
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static TtsVoice findByCommand(String inCommand) {
		return TtsVoice.ALL_VOICES_BY_COMMAND.get(inCommand);
	}

	/**
	 * récupère les options d'une voix de tts par sa commande ou par nom
	 * (ttsvoice_command, ttsvoice_str) Si enregitrement null alors retourne
	 * l'enregistrement par défaut ( 0 = laura22k)
	 * 
	 * @param inValue : ex => Claire22s ou Claire
	 * @param defaultLang : la langue du user par défaut
	 * @return l'enregistrement (ou <code>null</code>).
	 */
	public static TtsVoice findByCommandOrName(String inValue, long defaultLang) {
		TtsVoice theResult;
		theResult = TtsVoiceImpl.findByName(inValue);
		if (theResult == null) {
			theResult = TtsVoiceImpl.findByCommand(inValue);
		}
		if (theResult == null) {
			theResult = Factories.TTSVOICE.findByLang(defaultLang);
		}
		return theResult;
	}

	/**
	 * récupère les différentes voix proposé par le tts
	 * 
	 * @return les enregistrements.
	 */
	public static List<TtsVoice> getAllVoices() {
		return TtsVoice.ALL_VOICES;
	}

	public Lang getLang() {
		return this.mLang.get(this.ttsvoice_lang);
	}

}
