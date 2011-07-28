package net.violet.platform.datamodel.mock;

import java.util.TimeZone;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.util.CCalendar;

public class MessageMock extends AbstractMockRecord<Message, MessageMock> implements Message {

	private final String mText;
	private final CCalendar mTimeOfDelivery;
	private final Files mFiles;
	private long mCount;
	private long mColor;
	private final Palette mPalette;

	public MessageMock(long inId, Files inFile, String inText, CCalendar inTimeOfDelivery, int count, Palette palette) {
		super(inId);
		this.mFiles = inFile;
		this.mText = inText;
		this.mTimeOfDelivery = inTimeOfDelivery;
		this.mCount = count;
		this.mPalette = palette;
	}

	public Anim getAnim() {
		return null;
	}

	public Files getBody() {
		return this.mFiles;
	}

	public Long getColor() {
		return this.mColor;
	}

	public long getCount() {
		return this.mCount;
	}

	public Integer getEvent_id() {
		return null;
	}

	public Music getMusic() {
		return null;
	}

	public Long getNabcast() {
		return null;
	}

	public Palette getPalette() {
		return this.mPalette;
	}

	public String getText() {
		return this.mText;
	}

	public CCalendar getTimeOfDelivery() {
		return this.mTimeOfDelivery;
	}

	public CCalendar getTimeOfDelivery(TimeZone inTimeZone) {
		throw new UnsupportedOperationException();
	}

	public String getXMPPID() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isOrphan() {
		return ((Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(getId()) == null) && (Factories.MESSAGE_SENT.findMessageSentByMessageId(getId()) == null));
	}

	public void setAllInformation(String text, int eventId, Files file, int count, long color, long palette, CCalendar deliveryTime) {
		// TODO Auto-generated method stub

	}

	public void setCount(long inCount) {
		this.mCount = inCount;
	}

	public void setEventId(Integer event_id) {
		// TODO Auto-generated method stub

	}

	public void setSignatureInfo(Long color, Music music, Anim anim) {
		// TODO Auto-generated method stub

	}

	public void setTheNabcast(Long nabcast) {
		// TODO Auto-generated method stub

	}

	public void deleteEvent() {
		// TODO Auto-generated method stub

	}

	public void deleteScheduledMessage() {
		// TODO Auto-generated method stub

	}

}
