package net.violet.platform.message.elements;

import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.VObject;
import net.violet.platform.message.Sequence;

public interface SequencePart {

	String EXPRESSION = "expression";
	String DIRECTIVE = "directive";
	String ANNOTATION = "annotation";

	List<Sequence> getSequence(VObject inReceiver);

	Map<String, Object> getPojo();

}
