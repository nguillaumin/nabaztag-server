package net.violet.platform.xmpp.serialization;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.TtsVoice;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.message.elements.Expression;
import net.violet.platform.message.profiles.Profile;
import net.violet.platform.message.profiles.ProfilesManager;
import net.violet.platform.voice.TTSServices;

import org.apache.log4j.Logger;

/**
 * a utility Class to negociate modalities of expression with an object
 */
public class ContentNegociator {

	private static final Logger LOGGER = Logger.getLogger(ContentNegociator.class);

	/**
	 * Uses the provided Expression (which may or not contain alternative
	 * expressions) and builds and returns the most suitable Expression for the
	 * given object's profile.
	 * 
	 * @param inObject the object
	 * @param inExpression the Expression
	 * @return the Expression, or null.
	 */
	public static Expression negociate(VObject inObject, Expression inExpression) {

		if (ContentNegociator.LOGGER.isDebugEnabled()) {
			ContentNegociator.LOGGER.debug("Negociating " + inExpression + " for " + inObject.getObject_login());
		}

		final Profile objectProfile = ProfilesManager.getObjectProfile(inObject);

		final Expression negociated = ContentNegociator.getSupportedExpression(inExpression, objectProfile);

		// the main expression as the maximum support
		if (Expression.MAX_QUALITY.equals(negociated.getQuality())) {
			if (ContentNegociator.LOGGER.isDebugEnabled()) {
				ContentNegociator.LOGGER.debug("Object : " + inObject.getObject_login() + " has 100% support for " + negociated);
			}
			return negociated;
		}

		// create a sorted set of alternatives
		final SortedSet<Expression> sortedAlternatives = new TreeSet<Expression>(new Comparator<Expression>() {

			public int compare(Expression o1, Expression o2) {
				return o1.getQuality().compareTo(o2.getQuality());
			}
		});

		sortedAlternatives.add(negociated);

		for (final Expression expression : inExpression.getAlternatives()) {
			sortedAlternatives.add(ContentNegociator.getSupportedExpression(expression, objectProfile));
		}

		// is it enough ?
		final Expression theBestOne = ContentNegociator.proposeTTSAlternative(sortedAlternatives.last(), inObject);

		if (ContentNegociator.LOGGER.isDebugEnabled()) {
			ContentNegociator.LOGGER.debug("Negociation result : " + theBestOne + " for " + inObject.getObject_login());
		}
		return theBestOne;
	}

	/**
	 * In case of a TTS modality, some objects may not support it allready
	 * (mir:ror) But we can generate it on the platform, and replace the
	 * modality by a streaming alternative
	 * 
	 * @param inExpression
	 * @param inReceiver
	 * @return
	 */
	private static Expression proposeTTSAlternative(Expression inExpression, VObject inReceiver) {

		final String modality = inExpression.getModality();

		if (modality.startsWith("net.violet.tts") && (inExpression.getQuality() == 0.0)) { // TTS not supported
			ContentNegociator.LOGGER.debug("TTS not supported !");

			final String tts = inExpression.getString("text");
			final String voice = inExpression.getString("voice");
			// final String gender = (negociated.getParam("gender") != null )?
			// negociated.getParam("gender").toString() : "F";

			final long langId = inExpression.getTTSLang(inReceiver.getPreferences().getLangPreferences());

			final TtsVoice ttsVoice = Factories.TTSVOICE.findByCommandOrName(voice, langId);
			final Files ttsFile = TTSServices.getDefaultInstance().postTTS(tts, false, false, ttsVoice);

			if (ttsFile != null) {
				final Map<String, Object> streamTheVoice = new HashMap<String, Object>();
				streamTheVoice.put("modality", "net.violet.sound");
				streamTheVoice.put("url", ttsFile.getPath());
				return new Expression(streamTheVoice);
			}
		}

		return inExpression;
	}

	/**
	 * Apply the quality factor of the object's profile to the given expression
	 * Tell us how much this expression is supported by the object
	 * 
	 * @param inExpression
	 * @return the given expression with an altered quality factor
	 */
	private static Expression getSupportedExpression(Expression inExpression, Profile inProfile) {

		final Expression copy = new Expression(inExpression);

		String key = copy.getModality();
		final Map<String, Double> qualities = inProfile.getQualitySupport();

		while ((key != null) && !qualities.containsKey(key)) {
			key = ContentNegociator.getParentKey(key);
		}

		final double supportedLevel = (key == null) ? 0.0 : qualities.get(key);
		final Double negociatedLevel = new Double(supportedLevel * copy.getQuality());
		copy.setQuality(negociatedLevel);

		return copy;
	}

	/**
	 * Just remove the last portion of a key Ex :
	 * getParentKey("net.violet.tts.fr") > "net.violet.tts"
	 * 
	 * @param key
	 * @return a parent key, or NULL if we cannot
	 */
	private static String getParentKey(String key) {

		if ((key == null) || (key.length() == 0)) {
			return null;
		}

		// Find the last position of a punct
		final int lastPos = key.lastIndexOf(".");
		return (lastPos > -1) ? key.substring(0, lastPos) : null;

	}

}
