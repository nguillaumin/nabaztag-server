package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.util.CCalendar;

import org.apache.log4j.Logger;

public class MessageImpl extends ObjectRecord<Message, MessageImpl> implements Message {

	private static final Logger LOGGER = Logger.getLogger(MessageImpl.class);

	public static final SQLObjectSpecification<MessageImpl> SPECIFICATION = new SQLObjectSpecification<MessageImpl>("message", MessageImpl.class, new SQLKey[] { new SQLKey("id"), new SQLKey("event_id") });

	public static final String XMPP_ID_PREFIX = "message-";

	private static final String[] NEW_COLUMNS = new String[] { "body", "text", "timeOfDelivery", "count", "palette", };

	static final Comparator<Message> DESCENDING_COMPARATOR = new Comparator<Message>() {

		public int compare(Message o1, Message o2) {
			return o2.getTimeOfDelivery().compareTo(o1.getTimeOfDelivery());
		}
	};

	static final Comparator<Message> ASCENDING_COMPARATOR = new Comparator<Message>() {

		public int compare(Message o1, Message o2) {
			return o1.getTimeOfDelivery().compareTo(o2.getTimeOfDelivery());
		}
	};

	protected MessageImpl(long id) throws SQLException {
		init(id);
	}

	protected MessageImpl() {
	}

	public MessageImpl(Files inBody, String inText, CCalendar inTimeOfDelivery, long inCount, Palette inPalette) throws SQLException {
		this((Long) AbstractSQLRecord.getObjectId((FilesImpl) inBody), inText, inTimeOfDelivery, inCount, inPalette);
	}

	protected MessageImpl(long inBodyId, String inText, CCalendar inTimeOfDelivery, long inCount, Palette inPalette) throws SQLException {
		this.body = inBodyId;
		this.text = inText;
		this.timeOfDelivery = new Timestamp((inTimeOfDelivery != null) ? inTimeOfDelivery.getTimeInMillis() : System.currentTimeMillis());
		this.count = inCount;
		this.palette = inPalette.getInternalValue();
		this.anim_id = null;
		this.music_id = null;
		init(MessageImpl.NEW_COLUMNS);
	}

	protected long id;
	protected Long body;
	protected String text;
	protected Timestamp timeOfDelivery;
	protected long count;
	protected Long anim_id;
	protected Long music_id;
	protected Long color;
	protected long palette;
	protected Integer event_id;
	protected Long nabcast;

	private Files mBody;

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getBody()
	 */
	public Files getBody() {
		if (this.body != null) {
			if (this.mBody == null) {
				synchronized (this.body) {
					if (this.mBody == null) {
						this.mBody = Factories.FILES.find(this.body.longValue());
					}
				}
			}
		}
		return this.mBody;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getSpecification()
	 */
	@Override
	public SQLObjectSpecification<MessageImpl> getSpecification() {
		return MessageImpl.SPECIFICATION;
	}

	/**
	 * Find all MessageImpl NabcastImpl in future send by user {@link Message}
	 * 
	 * @param inVObject
	 * @return
	 */
	public static List<Message> findMessagesByNabcastAndState(long inNabcastValId, MessageReceived.MESSAGE_RECEIVED_STATES inState) {
		final List<Message> theList = new ArrayList<Message>();
		try {
			theList.addAll(AbstractSQLRecord.findAll(MessageImpl.SPECIFICATION, new String[] { "message_received" }, "message.id = message_received.message_id AND message.nabcast = ? AND message_received.message_state = ?", Arrays.asList(new Object[] { inNabcastValId, inState.toString() })));
		} catch (final SQLException e) {
			MessageImpl.LOGGER.fatal(e, e);
		}
		return theList;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#isOrphan()
	 */
	public boolean isOrphan() {
		if ((MessageReceivedImpl.countByMessage(this) > 0) || (MessageSentImpl.countByMessage(this) > 0)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getColor()
	 */
	public Long getColor() {
		return this.color;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.Message#setSignatureInfo(java.lang.Long,
	 * net.violet.platform.datamodel.MusicImpl,
	 * net.violet.platform.datamodel.Anim)
	 */
	public void setSignatureInfo(Long color, Music music, Anim anim) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setColor(theUpdateMap, color);
		setMusic(theUpdateMap, music);
		setAnim(theUpdateMap, anim);
		update(theUpdateMap);
	}

	/**
	 * @param color the color to set
	 */
	private void setColor(Map<String, Object> inUpdateMap, Long color) {
		if ((this.color == null) || !this.color.equals(color)) {
			this.color = color;
			inUpdateMap.put("color", color);
		}
	}

	/**
	 * @param music_id the music to set
	 */
	private void setMusic(Map<String, Object> inUpdateMap, Music music) {
		if ((music != null) && ((this.music_id == null) || !this.music_id.equals(music.getId()))) {
			this.music_id = music.getId();
			inUpdateMap.put("music_id", this.music_id);
		}
	}

	/**
	 * @param anim the anim to set
	 */
	private void setAnim(Map<String, Object> inUpdateMap, Anim anim) {
		if (this.anim_id == null) {
			if (anim != null) {
				this.anim_id = anim.getId();
				inUpdateMap.put("anim_id", this.anim_id);
			}
		} else {
			if (anim == null) {
				this.anim_id = null;
				inUpdateMap.put("anim_id", this.anim_id);
			} else if (this.anim_id.longValue() != anim.getId()) {
				this.anim_id = anim.getId();
				inUpdateMap.put("anim_id", this.anim_id);
			}
		}
	}

	public Palette getPalette() {
		return Palette.findPaletteByNum((int) this.palette);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getCount()
	 */
	public long getCount() {
		return this.count;
	}

	/**
	 * @param count the count to set
	 */
	private void setCount(Map<String, Object> inUpdateMap, long inCount) {
		if (this.count != inCount) {
			this.count = inCount;
			inUpdateMap.put("count", inCount);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#setCount(long)
	 */
	public void setCount(long inCount) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setCount(theUpdateMap, inCount);
		update(theUpdateMap);
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getText()
	 */
	public String getText() {
		return this.text;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getTimeOfDelivery()
	 */
	public CCalendar getTimeOfDelivery() {
		return new CCalendar(this.timeOfDelivery.getTime());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.Message#getTimeOfDelivery(java.util.TimeZone
	 * )
	 */
	public CCalendar getTimeOfDelivery(TimeZone inTimeZone) {
		return new CCalendar(this.timeOfDelivery.getTime(), inTimeZone);
	}

	// Special test
	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.datamodel.Message#setAllInformation(java.lang.String,
	 * int, net.violet.platform.datamodel.Files, int, long, long,
	 * net.violet.v2.util.CCalendar)
	 */
	public void setAllInformation(String text, int eventId, Files file, int count, long color, long palette, CCalendar deliveryTime) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setText(theUpdateMap, text);
		setEvent_id(theUpdateMap, eventId);
		setBody(theUpdateMap, file);
		setCount(theUpdateMap, count);
		setColor(theUpdateMap, color);
		setPalette(theUpdateMap, palette);
		setTimeOfDelivery(theUpdateMap, deliveryTime);
		update(theUpdateMap);
	}

	/**
	 * Sets the body associated this message
	 * 
	 * @param inBody the file/body of the message
	 */
	private void setBody(Map<String, Object> inUpdateMap, Files inBody) {
		if (inBody == null) {
			if (this.body != null) {
				this.mBody = null;
				this.body = null;
				inUpdateMap.put("body", this.body);
			}
		} else {
			if ((this.body == null) || (inBody.getId() != this.body.longValue())) {
				this.body = new Long(inBody.getId());
				this.mBody = inBody;
				inUpdateMap.put("body", this.body);
			}
		}
	}

	/**
	 * @param palette the palette color to set
	 */
	private void setPalette(Map<String, Object> inUpdateMap, long palette) {
		if (this.palette != palette) {
			this.palette = palette;
			inUpdateMap.put("palette", palette);
		}
	}

	/**
	 * @param text the text to set
	 */
	private void setText(Map<String, Object> inUpdateMap, String text) {
		if ((this.text == null) || !this.text.equals(text)) {
			this.text = text;
			inUpdateMap.put("text", text);
		}
	}

	/**
	 * @param timeOfDelivery the timeOfDelivery to set
	 */
	private void setTimeOfDelivery(Map<String, Object> inUpdateMap, CCalendar inTimeOfDelivery) {
		final Timestamp timeOfDeliveryTmp = new Timestamp(inTimeOfDelivery.getTimeInMillis());

		if ((this.timeOfDelivery == null) || (this.timeOfDelivery.compareTo(timeOfDeliveryTmp) != 0)) {
			this.timeOfDelivery = timeOfDeliveryTmp;
			inUpdateMap.put("timeOfDelivery", this.timeOfDelivery);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getEvent_id()
	 */
	public Integer getEvent_id() {
		MessageImpl.LOGGER.debug("GET EVENT ID this = " + getId() + " @ " + System.identityHashCode(this) + " , event = " + this.event_id);
		return this.event_id;
	}

	/**
	 * @param event_id the event_id to set
	 */
	private void setEvent_id(Map<String, Object> inUpdateMap, Integer event_id) {
		if ((this.event_id == null) || (this.event_id != event_id)) {
			MessageImpl.LOGGER.debug("SET EVENT ID this = " + getId() + " @ " + System.identityHashCode(this) + " , event = " + event_id);
			this.event_id = event_id;
			inUpdateMap.put("event_id", event_id);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#setEventId(java.lang.Integer)
	 */
	public void setEventId(Integer event_id) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setEvent_id(theUpdateMap, event_id);
		update(theUpdateMap);
	}

	@Override
	protected void doDelete() throws SQLException {
		deleteEvent();
		deleteScheduledMessage();
		final Files theBody = getBody();
		if (theBody != null) {
			theBody.scheduleDeletion();
		}
		super.doDelete();
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#deleteEvent()
	 */
	public void deleteEvent() {
		MessageImpl.LOGGER.debug("delete event :" + getId() + ", event = " + getEvent_id());
		if ((getEvent_id() != null) && (getEvent_id() != 0)) {
			final long theEventId = getEvent_id();
			final Event theEvent = EventImpl.find(theEventId);
			if (theEvent != null) {
				theEvent.delete();
			} else {
				MessageImpl.LOGGER.warn("the event with the id = " + theEventId + " cannot be deleted");
			}
			Factories.EVSEQ.deleteByEventID(theEventId);
			setEventId(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#deleteScheduledMessage()
	 */
	public void deleteScheduledMessage() {
		final ScheduledMessage theScheduledMessage = Factories.SCHEDULED_MESSAGE.findByMessageId(getId());
		if (theScheduledMessage != null) {
			theScheduledMessage.delete();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getAnim()
	 */
	public Anim getAnim() {
		if (this.anim_id != null) {
			return AnimImpl.find(this.anim_id);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getMusic()
	 */
	public Music getMusic() {
		if (this.music_id != null) {
			return Factories.MUSIC.find(this.music_id);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getXMPPID()
	 */
	public String getXMPPID() {
		return MessageImpl.XMPP_ID_PREFIX + getId();
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#getNabcast()
	 */
	public Long getNabcast() {
		return this.nabcast;
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.datamodel.Message#setTheNabcast(java.lang.Long)
	 */
	public void setTheNabcast(Long nabcast) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setNabcast(theUpdateMap, nabcast);
		update(theUpdateMap);
	}

	private void setNabcast(Map<String, Object> inUpdateMap, Long nabcast) {
		if ((this.nabcast == null) || (this.nabcast != nabcast)) {
			this.nabcast = nabcast;
			inUpdateMap.put("nabcast", nabcast);
		}
	}

}
