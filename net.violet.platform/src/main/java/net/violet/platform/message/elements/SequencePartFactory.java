package net.violet.platform.message.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.util.StringShop;

/**
 * An InteractiveSequence is an easy way to store SequencePart objects.
 */
public class SequencePartFactory {

	/**
	 * Creates an InteractiveSequence object with the provided List, which must
	 * be a list of Map<String,Object> defining SequencePart structure according
	 * to the Violet Specification.
	 * 
	 * @param inPOJOSequence
	 * @throws ClassCastException
	 */
	public static List<SequencePart> buildSequence(List<Map<String, Object>> inPOJOSequence, VObject inRfid, Application inApplication) throws InvalidParameterException {

		final List<SequencePart> seqParts = new ArrayList<SequencePart>();

		for (final Map<String, Object> part : inPOJOSequence) {

			final String type = (String) part.remove("type");

			if (SequencePart.EXPRESSION.equals(type)) {
				seqParts.add(new Expression(part));

			} else if (SequencePart.DIRECTIVE.equals(type)) {
				seqParts.add(new Directive(part, inRfid, inApplication));

			} else if (SequencePart.ANNOTATION.equals(type)) {
				seqParts.add(new Annotation(part));

			} else {
				throw new InvalidParameterException(APIErrorMessage.INVALID_SEQUENCE_PART, StringShop.EMPTY_STRING);
			}
		}

		return seqParts;

	}

}
