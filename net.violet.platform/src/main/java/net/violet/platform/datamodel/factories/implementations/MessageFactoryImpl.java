package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageImpl;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageReceivedImpl;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.MessageSentImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.MessageFactory;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.dataobjects.MessageData.StatusMessage;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class MessageFactoryImpl extends RecordFactoryImpl<Message, MessageImpl> implements MessageFactory {

	private static final Logger LOGGER = Logger.getLogger(MessageFactoryImpl.class);

	MessageFactoryImpl() {
		super(MessageImpl.SPECIFICATION);
	}

	/**
	 * Finds a {@link Message} from the given event_id
	 * 
	 * @param inId
	 * @return
	 */
	public Message findByEventID(long inId) {
		return findByKey(1, inId);
	}

	public boolean usesFiles(Files inFile) {
		return count(null, "body = ?", Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

	public long countMessageReceivedOrArchivedByStatus(StatusMessage inStatus, User inUser, VObject inObject) {
		String optional_condition = net.violet.common.StringShop.EMPTY_STRING;

		if (inObject != null) {
			optional_condition = " and object.object_id = " + inObject.getId();
		}

		final String[] inJoinTables = new String[] { "messenger", "message_received", "object" };
		return count(inJoinTables, " object.object_owner = ? " + optional_condition + " and messenger.object_id=object.object_id and message_received.recipient_id=messenger.id and message_received.message_state= ? and message.id=message_received.message_id ", Arrays.asList((Object) inUser.getId(), inStatus.name()), null);
	}

	public long countMessageSent(User inUser) {
		final String[] inJoinTables = new String[] { "messenger", "message_sent" };
		return count(inJoinTables, " messenger.user_id = ? and message_sent.sender_id=messenger.id and message_sent.message_state= ? and message.id=message_sent.message_id ", Arrays.asList((Object) inUser.getId(), StatusMessage.SENT.name()), null);
	}

	public int walkInMessageReceivedOrArchivedByStatus(StatusMessage inStatus, User inUser, VObject inObject, int inSkip, int inGetCount, JoinRecordsWalker<Message, MessageReceived> inWalker) {
		String optional_condition = net.violet.common.StringShop.EMPTY_STRING;

		if (inObject != null) {
			optional_condition = " and object.object_id = " + inObject.getId();
		}

		final String condition = " object.object_owner = ? " + optional_condition + " and messenger.object_id=object.object_id and message_received.recipient_id=messenger.id and message_received.message_state= ? and message.id=message_received.message_id ";
		final String[] inJoinTables = new String[] { "messenger", "object" };
		return walk(MessageReceivedImpl.SPECIFICATION, condition, Arrays.asList((Object) inUser.getId(), inStatus.name()), inJoinTables, "timeOfDelivery DESC", 0 /* skip */, inSkip, inGetCount, inWalker);
	}

	public int walkInMessageReceived(VObject inObject, JoinRecordsWalker<Message, MessageReceived> inWalker) {
		final String condition = "object.object_id=? and messenger.object_id=object.object_id and message_received.recipient_id=messenger.id and message_received.message_state= ? and message.id=message_received.message_id ";
		final String[] inJoinTables = new String[] { "messenger", "object" };
		return walk(MessageReceivedImpl.SPECIFICATION, condition, Arrays.asList((Object) inObject.getId(), StatusMessage.INBOX.name()), inJoinTables, null, 0 /* skip */, 0, 0, inWalker);
	}

	public int walkInMessageSent(User inUser, int inSkip, int inGetCount, JoinRecordsWalker<Message, MessageSent> inWalker) {
		final String condition = " messenger.user_id = ? and message_sent.sender_id=messenger.id and message_sent.message_state= ? and message.id=message_sent.message_id ";
		final String[] inJoinTables = new String[] { "messenger" };
		return walk(MessageSentImpl.SPECIFICATION, condition, Arrays.asList((Object) inUser.getId(), StatusMessage.SENT.name()), inJoinTables, "timeOfDelivery DESC", 0 /* skip */, inSkip, inGetCount, inWalker);
	}

	public Message create(Files inFile, String inText, CCalendar inTimeOfDelivery, Palette inPalette) {
		try {
			return new MessageImpl(inFile, inText, inTimeOfDelivery, 0, inPalette);
		} catch (final SQLException e) {
			MessageFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * utilisé lors du mode répondeur, afin de récupèrer un par un les messages
	 * en attente
	 * 
	 * @param inObject
	 * @return MessageImpl
	 */
	public Message findFirstMessageReceived(VObject inObject) {
		final String[] inJoinTables = new String[] { "messenger", "message_received" };
		final String condition = " messenger.object_id = ? and message_received.recipient_id=messenger.id and message_received.message_state='INBOX' and message.id=message_received.message_id ";
		final List<Object> theVals = Collections.singletonList((Object) inObject.getId());
		return find(inJoinTables, condition, theVals, " message.timeOfDelivery, message.event_id ");
	}

	/**
	 * utilisé lors du mode répondeur, afin d'avoir le dernier message (ainsi on
	 * ne recevra pas deux fois le même message si il y a eu un envoi de message
	 * lors de l'écoute du répondeur)
	 * 
	 * @param inObject
	 * @return MessageImpl
	 */
	public Message findLastMessageReceived(VObject inObject) {
		final String[] inJoinTables = new String[] { "messenger", "message_received" };
		final String condition = " messenger.object_id = ? and message_received.recipient_id=messenger.id and message_received.message_state='INBOX' and message.id=message_received.message_id ";
		final List<Object> theVals = Collections.singletonList((Object) inObject.getId());
		return find(inJoinTables, condition, theVals, " message." + StringShop.ID_DESC);
	}

	public Message findByXMPPID(String theID) {
		Message theResult = null;
		if (theID.startsWith(MessageImpl.XMPP_ID_PREFIX)) {
			final String theIDNum = theID.substring(MessageImpl.XMPP_ID_PREFIX.length());
			try {
				final long theIDLong = Long.parseLong(theIDNum);
				theResult = Factories.MESSAGE.find(theIDLong);
			} catch (final NumberFormatException anException) {
				MessageFactoryImpl.LOGGER.fatal(anException, anException);
			}
		}
		return theResult;
	}

}
