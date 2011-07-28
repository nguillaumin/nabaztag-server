package net.violet.platform.xmpp.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.User;
import net.violet.platform.message.AddressedMessage;
import net.violet.platform.message.Message;
import net.violet.platform.message.elements.SequencePart;

/**
 * The based class for all serializers using the 2.0 format to serialize
 * {@link Message} objects.
 */
public abstract class AbstractV2Serializer implements Serializer {

	protected Map<String, Object> generatePojo(Message message) {
		final Map<String, Object> pojoMap = new HashMap<String, Object>();

		if (message instanceof AddressedMessage) {
			final User sender = ((AddressedMessage) message).getSender();
			if (sender != null) {
				pojoMap.put("from", sender.getId());
			}
		}

//		final VObject dest = message.getReceiver();
//		pojoMap.put("to", dest.getId());

		if (message.getTitle() != null) {
			pojoMap.put("title", message.getTitle());
		}

		if (message.getDeliveryDate() != null) {
			pojoMap.put("date", message.getDeliveryDate().getTime());
		}

		if (message.getApplication() != null) {
			pojoMap.put("application", message.getApplication().getName());
		}

		if (message.getSources() != null) {
			pojoMap.put("source", message.getSources());
		}

		final List<Map<String, Object>> seqPart = new ArrayList<Map<String, Object>>();
		for (final SequencePart part : message.getSequencePart()) {
			seqPart.add(part.getPojo());
		}
		pojoMap.put("sequence", seqPart);

		return pojoMap;
	}
}
