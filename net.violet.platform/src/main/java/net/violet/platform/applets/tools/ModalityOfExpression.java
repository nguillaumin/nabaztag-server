package net.violet.platform.applets.tools;

import java.util.List;
import java.util.Map;

/**
 * The modality by which an object can express itself (ex : danse, choregraphy,
 * speech..) A modality has a quality factor whose range is 0..1 (or 0..100%)
 * The quality factor can express two things : - inside a sequence, it expresses
 * how much that modality is the wanted one, or an alternate choice - associated
 * to an object during the content negociation process, it expresses how much
 * that modality is supported by the object
 */
public interface ModalityOfExpression extends Map<String, Object> {

	// the key of this modality corresponding to the type of media/acting
	// sequence
	// ie : "net.violet.video" for a movie
	String getModalityKey();

	void setModalityKey(String key);

	// a value between 0 and 1 representing the "quality" of this
	// media/expression
	// 0 = crap (silence, black screen..?), 1 = top notch, cream of the cream
	// movie or acting part
	Double getQuality();

	void setQuality(double q);

	void setQuality(Double q);

	// public Modality getModality();

	// this maybe a value for some expressions
	String getValue();

	void setValue(String txt);

	// get the value of an optional parameter
	String getParam(String paramName);

	int getIntParam(String string, int defaultValue);

	void setParam(String paramName, String paramValue);

	// An expression can have a set of alternate choices
	List<ModalityOfExpression> getAlternateChoices();

	void resetAlternateChoices();

	ModalityOfExpression clone();

	boolean isOptimal();

}
