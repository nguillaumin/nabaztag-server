package net.violet.platform.message.elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.message.Sequence;
import net.violet.platform.message.SequenceImpl;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class Directive implements SequencePart {

	private static final Logger LOGGER = Logger.getLogger(Directive.class);

	private static final String START_INTERACTIVE = "start-interactive";
	private static final String STOP_INTERACTIVE = "stop-interactive";

	// FIXME : this map of events is only for Nabaztag !
	private static final String NAB_SETTINGS = "violet.nabaztag.";
	private static final Map<String, Integer> NABAZTAG_EVENTS = new HashMap<String, Integer>();
	static {
		//Directive.NABAZTAG_EVENTS.put(ButtonEventMap.TYPE, 1);
		//Directive.NABAZTAG_EVENTS.put(EarMovementEventMap.TYPE, 6); // 2 : left ear, 4: right ear -> 6 both of them
		Directive.NABAZTAG_EVENTS.put("ZtampDetectionEvent", 8);
	};

	private final Map<String, Object> mPojo;

	private final String mAction;
	private final VObject mRfid;
	private final Map<String, String> mSettings;
	private final List<String> mMaskedEvents;
	private final Application mApplication;

	/**
	 * Creates a new Directive object according to the provided pojo, rfid
	 * object and application. Rfid and application can be null but if the
	 * application is not null then the rfid MUST NOT be null.
	 * 
	 * @param pojo
	 * @param inRfid
	 * @param inApplication
	 */

	public Directive(Map<String, Object> pojo, VObject inRfid, Application inApplication) {
		try {
			this.mPojo = new HashMap<String, Object>(pojo);
			this.mRfid = inRfid;
			this.mApplication = inApplication;
			this.mAction = (String) pojo.get("action");
			this.mSettings = (Map<String, String>) pojo.get("settings");
			this.mMaskedEvents = (List<String>) pojo.get("disable");

		} catch (final ClassCastException e) {
			throw new IllegalArgumentException("The passed structure cannot be a directive : " + pojo, e);
		}
	}

	public List<Sequence> getSequence(VObject inReceiver) {
		final List<Sequence> result = new ArrayList<Sequence>();

		// Action is used to start/end the interactive mode.
		if (Directive.START_INTERACTIVE.equals(this.mAction)) {
			if ((this.mApplication != null) && (this.mRfid != null)) {
				try {
					final String url = URLEncoder.encode(this.mApplication.getName(), "UTF-8") + "&zn=" + this.mRfid.getObject_serial();
					result.add(new SequenceImpl(Sequence.SEQ_BEGIN_INTERACTIVE_MODE, url));
				} catch (final UnsupportedEncodingException e) {
					Directive.LOGGER.fatal(e, e);
				}
			} else {
				Directive.LOGGER.fatal("Cannot start interactive mode, application and/or rfid null !");
			}
		}
		if (Directive.STOP_INTERACTIVE.equals(this.mAction)) {
			result.add(new SequenceImpl(Sequence.SEQ_END_INTERACTIVE_MODE, StringShop.EMPTY_STRING));
		}

		// The settings change the object behavior, they have to start with the
		// correct prefix
		if ((this.mSettings != null) && !this.mSettings.isEmpty()) {
			for (final String key : this.mSettings.keySet()) {
				if (key.startsWith(Directive.NAB_SETTINGS)) {
					final String value = this.mSettings.get(key);
					result.add(new SequenceImpl(Sequence.SEQ_SETTING, key.substring(Directive.NAB_SETTINGS.length()) + "=" + value));
				}
				// FIXME handle this case
				else {
					final String value = this.mSettings.get(key);
					result.add(new SequenceImpl(Sequence.SEQ_SETTING, key + "=" + value));
				}
			}
		}

		// The events to disable on the object
		if ((this.mMaskedEvents != null) && !this.mMaskedEvents.isEmpty()) {
			int intMask = 0; // interruption mask for Nabaztag
			for (final String maskedEventName : this.mMaskedEvents) {
				final Integer bitMask = Directive.NABAZTAG_EVENTS.get(maskedEventName);
				if (bitMask != null) {
					intMask += bitMask;
				}
			}
			result.add(new SequenceImpl(Sequence.SEQ_SETTING, "int.mask=" + intMask));
		}

		return result;

	}

	public Map<String, Object> getPojo() {
		final Map<String, Object> result = new HashMap<String, Object>(this.mPojo);
		result.put("type", "directive");
		return result;
	}

}
