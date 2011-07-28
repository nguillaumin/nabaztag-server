package net.violet.platform.events.maps;

import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.maps.AbstractAPIMap;
import net.violet.platform.dataobjects.AnnuData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.events.InteractionEvent;

public abstract class InteractionEventMap extends EventMap {

	public static final String OBJECT = "object";
	public static final String TARGET = "target";

	public InteractionEventMap(InteractionEvent inEvent, APICaller inCaller) {
		super(inEvent);
		put(InteractionEventMap.OBJECT, new ObjectMap(inEvent.getEmitter(), inCaller));
		put(InteractionEventMap.TARGET, new ObjectMap(inEvent.getTarget(), inCaller));
	}

	protected static class ObjectMap extends AbstractAPIMap {

		public ObjectMap(VObjectData inObject, APICaller inCaller) {
			put("id", inObject.getApiId(inCaller));
			put("name", inObject.getProfile().getLabel());
			put("hw_type", inObject.getObjectType().getTypeName());
			put("serial", inObject.getSerial());
			put("tz", inObject.getTimeZone());
			put("owner", new OwnerMap(inObject.getOwner(), inCaller));
		}
	}

	protected static class OwnerMap extends AbstractAPIMap {

		public OwnerMap(UserData inOwner, APICaller inCaller) {
			put("id", inOwner.getApiId(inCaller));
			final AnnuData annu = inOwner.getAnnu();

			put("firstname", annu.getFirstName());
			put("lastname", annu.getLastName());
			put("city", annu.getAnnu_city());
			put("lang", annu.getLang().getLang_iso_code());
		}
	}

}
