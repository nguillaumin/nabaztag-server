package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.MessageFactory;
import net.violet.platform.datamodel.mock.MessageMock;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.dataobjects.MessageData.StatusMessage;
import net.violet.platform.util.CCalendar;

public class MessageFactoryMock extends RecordFactoryMock<Message, MessageMock> implements MessageFactory {

	MessageFactoryMock() {
		super(MessageMock.class);
	}

	public boolean usesFiles(Files inFile) {

		for (final Message aMessage : findAllMapped().values()) {
			if (aMessage.getBody().getId().equals(inFile.getId())) {
				return true;
			}
		}
		return false;
	}

	public long countMessageReceivedOrArchivedByStatus(StatusMessage inStatus, User inUser, VObject inObject) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long countMessageSent(User inUser) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int walkInMessageReceivedOrArchivedByStatus(StatusMessage inStatus, User inUser, VObject inObject, int inSkip, int inGetCount, JoinRecordsWalker<Message, MessageReceived> inWalker) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int walkInMessageSent(User inUser, int inSkip, int inGetCount, JoinRecordsWalker<Message, MessageSent> inWalker) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int walkInMessageReceived(VObject inObject, JoinRecordsWalker<Message, MessageReceived> inWalker) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Message create(Files inFile, String inText, CCalendar inTimeOfDelivery, Palette inPalette) {
		return new MessageMock(0, inFile, inText, inTimeOfDelivery, 0, inPalette);

	}

	public Message findByEventID(long inId) {
		for (final Message aMessage : findAll()) {
			if (aMessage.getEvent_id().intValue() == inId) {
				return aMessage;
			}
		}
		return null;
	}

	public Message findByXMPPID(String theID) {
		// TODO Auto-generated method stub
		return null;
	}

	public Message findFirstMessageReceived(VObject inObject) {
		// TODO Auto-generated method stub
		return null;
	}

	public Message findLastMessageReceived(VObject inObject) {
		// TODO Auto-generated method stub
		return null;
	}
}
