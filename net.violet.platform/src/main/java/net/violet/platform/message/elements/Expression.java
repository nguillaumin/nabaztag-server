package net.violet.platform.message.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.maps.PojoMap;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectType;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.message.Sequence;
import net.violet.platform.message.SequenceImpl;
import net.violet.platform.voice.TTSServices;
import net.violet.platform.xmpp.serialization.ContentNegociator;
import net.violet.vlisp.services.ManageMessageServices;

import org.apache.log4j.Logger;

/**
 * Expression objects are specific SequencePart implementation. Expression is
 * part of InteractiveSequence.
 */
public class Expression implements SequencePart {

	public static final Double MAX_QUALITY = 1.0;
	private static final Logger LOGGER = Logger.getLogger(Expression.class);

	private PojoMap mPojo;
	private final String modality;
	private Double quality;
	private final List<Expression> mAlternatives;
	private final PojoMap mParams;

	/**
	 * @param inExpressionMap
	 */
	public Expression(Map<String, Object> inExpressionMap) {

		setPojo(inExpressionMap);

		this.mParams = new PojoMap(inExpressionMap);
		this.mParams.remove("alt");

		// Handles the resource inheritance
		if (this.mParams.containsKey("resource")) {
			final Map<String, Object> resource = (Map<String, Object>) this.mParams.remove("resource");
			this.mParams.putAll(resource);
		}

		// get the modality and quality factor and remove them from the map
		this.modality = inExpressionMap.get("modality").toString();
		this.mParams.remove("modality");

		if (inExpressionMap.get("quality") == null) {
			this.quality = Expression.MAX_QUALITY;
		} else {
			this.quality = Double.parseDouble(inExpressionMap.get("quality").toString());
		}
		this.mParams.remove("quality");

		// Builds the list of alternatives
		this.mAlternatives = new ArrayList<Expression>();
		if (inExpressionMap.get("alt") != null) {

			for (final Object alternative : (List<Object>) inExpressionMap.get("alt")) {
				this.mAlternatives.add(new Expression(this, (Map<String, Object>) alternative));
			}
		}

	}

	/**
	 * Creates a specific Expression object. The expression is supposed to be an
	 * alternative for the provided Expression. The Expression will contain all
	 * the parameters of the main Expression and overrides some of them with its
	 * specific values ( which are in the pojo object). This Expression can not
	 * contain alternative choices.
	 * 
	 * @param inAlternative the specific parameters for this expression
	 * @param inBaseExpression the main Expression used to build the alternative
	 *            Expression.
	 * @TODO rewrite all this
	 */
	private Expression(Expression inBaseExpression, Map<String, Object> inAlternative) {

		this.mPojo = new PojoMap(inBaseExpression.getPojo());
		this.mPojo.remove("alt");
		this.mPojo.putAll(inAlternative);

		this.mParams = new PojoMap(inBaseExpression.getParams());
		this.mParams.putAll(inAlternative);

		this.mParams.remove("alt");

		// Handles the resource inheritance
		if (this.mParams.containsKey("resource")) {
			final Map<String, Object> resource = (Map<String, Object>) this.mParams.remove("resource");
			this.mParams.putAll(resource);
		}

		this.modality = inAlternative.get("modality").toString();
		this.mParams.remove("modality");

		if (inAlternative.get("quality") == null) {
			this.quality = Expression.MAX_QUALITY;
		} else {
			this.quality = Double.parseDouble(inAlternative.get("quality").toString());
		}
		this.mParams.remove("quality");

		this.mAlternatives = Collections.emptyList();
	}

	/**
	 * Use this constructor to make a simple copy of the provided expression.
	 * Caution : alternative expressions are not copied.
	 * 
	 * @param inExpression the Expression to copy.
	 */
	public Expression(Expression inExpression) {

		this.modality = inExpression.getModality();
		this.quality = inExpression.getQuality();

		this.mParams = new PojoMap(inExpression.getParams());
		this.mPojo = new PojoMap(inExpression.getPojo());
		this.mPojo.remove("alt");

		this.mAlternatives = Collections.emptyList();
	}

	/**
	 * Build the adequate sequence to be played by an object : This method does
	 * some content negociation to determine wich sequence amongst the main and
	 * alternative propositions has the best quality support on the object
	 * 
	 * @return the negociated sequence in a list or an empty list
	 * @see net.violet.platform.message.elements.SequencePart#getSequence(VObject)
	 */
	public List<Sequence> getSequence(VObject inReceiver) {

		final List<Sequence> lstSeq = new ArrayList<Sequence>();
		final Expression negociated = ContentNegociator.negociate(inReceiver, this);
		final String theModality = negociated.getModality();

		try {

			if (theModality.startsWith("net.violet.wait")) { // Wait
				final int timeInMillis = negociated.getInt("time_ms", true);
				lstSeq.add(new SequenceImpl(Sequence.SEQ_WAIT, net.violet.common.StringShop.EMPTY_STRING + timeInMillis));

			} else if (theModality.startsWith("net.violet.tts")) { // TTS

				final String tts = negociated.getString("text");

				// cherche la voix si le parametre est fourni
				final String voiceName = negociated.getString("voice");
				TtsVoice voice = (voiceName != null) ? Factories.TTSVOICE.findByCommandOrName(voiceName) : null;

				if (voice == null) { // on n'a pas la voix pour la synthèse, on
					// va la chercher avec la langue

					final String languageCode = (theModality.length() > "net.violet.tts".length()) ? theModality.substring(15) : null;
					final TtsLangData languageData;

					if (languageCode != null) {
						languageData = TtsLangData.getDefaultTtsLanguage(languageCode);

					} else {// on a pas la langue ni la commande alors on va
						// l'identifier avec le texte
						languageData = TtsLangData.getIdentifyLanguage(tts);
						Expression.LOGGER.warn("Language identification for " + theModality + "(" + tts + ") gave " + languageData.getLang_iso_code());
					}

					voice = Factories.TTSVOICE.findByLang(languageData.getReference());
				}

				if (Expression.LOGGER.isDebugEnabled()) {
					Expression.LOGGER.debug(theModality + " : generating tts message \"" + tts + "\"with voice : " + voice.getTtsvoice_libelle());
				}

				final VObjectData theReceiver = VObjectData.getData(inReceiver);
				final boolean needsTreatment = theReceiver.getObjectType().instanceOf(ObjectType.NABAZTAG_V1);
				final Files ttsFile = TTSServices.getDefaultInstance().postTTS(tts, needsTreatment, needsTreatment, voice);

				if (ttsFile != null) {
					lstSeq.add(new SequenceImpl(Sequence.SEQ_MUSICDOWNLOAD, ttsFile.getPath()));
					// ttsFile.scheduleDeletion();
				}

			} else if (theModality.startsWith("net.violet.sound")) { // sound
				final String url = negociated.getString("url");

				final int code;
				if (negociated.getBoolean("streaming", true)) { // default : streaming=true

					if (negociated.getBoolean("withEar", false)) { // default : withEar=false
						// streaming = true AND withEar = true
						code = Sequence.SEQ_MUSICSHOULDSTREAM;

					} else {
						// streaming = true AND withEar = false
						code = Sequence.SEQ_MUSIC_STREAMING;
					}

				} else {
					// streaming = false and we assume that withEar = true
					code = Sequence.SEQ_MUSICDOWNLOAD;
				}

				lstSeq.add(new SequenceImpl(code, url));

			} else if (theModality.startsWith("net.violet.webradio")) { // webradio

				lstSeq.add(new SequenceImpl(Sequence.SEQ_MUSICSHOULDSTREAM, negociated.getString("url")));

			} else if (theModality.startsWith("net.violet.choreography")) { // Choreography
				//
				final String url;

				final String chorData = negociated.getString("choreography");
				if (chorData != null) {
					final Files chorFile = ManageMessageServices.createChorByApi(chorData);
					url = chorFile.getPath();

				} else {
					url = negociated.getString("url");
				}

				if (negociated.getBoolean("streaming", false) == true) { // default : streaming = false
					lstSeq.add(new SequenceImpl(Sequence.SEQ_CHORSTREAMING, url));
				} else {
					lstSeq.add(new SequenceImpl(Sequence.SEQ_CHORDOWNLOAD, url));
				}

			} else if (theModality.startsWith("net.violet.palette")) { // palette
				// find the encoded value
				final Palette palette = Palette.findPaletteByName(negociated.getString("name"));
				lstSeq.add(new SequenceImpl(Sequence.SEQ_PALETTE, Long.toString(palette.getInternalValue())));
			}

		} catch (final Exception e) {
			Expression.LOGGER.error("An invalid sequence part for object " + inReceiver.getObject_login() + " couldn't be added to the list ! " + negociated, e);
		}

		return lstSeq;
	}

	/**
	 * Extracts the language from the given modality which must starts with
	 * 'net.violet.tts'. If the language can not be found, the id of the
	 * provided Lang object is returned.
	 * 
	 * @param inDefaultLang the default language to return if the language can
	 *            not be extracted.
	 * @return the language id
	 */
	public long getTTSLang(Lang inDefaultLang) {
		final String theModality = getModality();

		if (theModality.startsWith("net.violet.tts.")) {
			final String langCode = theModality.substring(14);
			final TtsLangData lang = TtsLangData.findByISOCode(langCode);
			if (lang != null) {
				return lang.getId();
			}
		}

		return inDefaultLang.getId();
	}

	public Double getQuality() {
		return this.quality;
	}

	public void setQuality(Double inQuality) {
		this.quality = inQuality;
		this.mPojo.put("quality", inQuality);
	}

	public String getModality() {
		return this.modality;
	}

	private Map<String, Object> getParams() {
		return this.mParams;
	}

	public List<Expression> getAlternatives() {
		return this.mAlternatives;
	}

	public String getString(String paramName) {
		return this.mParams.getString(paramName, null);
	}

	public boolean getBoolean(String inParamName, boolean inDefaultValue) throws InvalidParameterException {
		return this.mParams.getBoolean(inParamName, inDefaultValue);
	}

	public int getInt(String inParamName, boolean isMandatory) throws InvalidParameterException {
		return this.mParams.getInt(inParamName, isMandatory);
	}

	public void setPojo(Map<String, Object> inPojo) {
		this.mPojo = new PojoMap(inPojo);
		this.mPojo.put("type", "expression");
	}

	public PojoMap getPojo() {
		return this.mPojo;
	}

	@Override
	public String toString() {
		return this.mPojo.toString();
	}

}
