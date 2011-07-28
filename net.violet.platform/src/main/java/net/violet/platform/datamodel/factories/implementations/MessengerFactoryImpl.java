package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Collections;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.MessengerImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.MessengerFactory;

import org.apache.log4j.Logger;

public class MessengerFactoryImpl extends RecordFactoryImpl<Messenger, MessengerImpl> implements MessengerFactory {

	private static final Logger LOGGER = Logger.getLogger(MessengerFactoryImpl.class);

	public MessengerFactoryImpl() {
		super(MessengerImpl.SPECIFICATION);
	}

	public Messenger getByObject(VObject inObject) {
		if (inObject != null) {
			return getByObjectId(inObject.getId());
		}
		return null;
	}

	public Messenger getByObjectId(long inId) {
		Messenger theMessenger = findByObjectId(inId);
		if (theMessenger == null) {
			try {
				final VObject theVObject = Factories.VOBJECT.find(inId);
				if (theVObject != null) {
					theMessenger = new MessengerImpl(theVObject, theVObject.getObject_login());
				}
			} catch (final SQLException e) {
				theMessenger = findByObjectId(inId);

				if (theMessenger == null) {
					MessengerFactoryImpl.LOGGER.fatal(e, e);
				}
			}
		}
		return theMessenger;
	}

	public Messenger getByUser(User inUser) {
		if (inUser != null) {
			return getByUserId(inUser.getId());
		}
		return null;
	}

	public Messenger getByUserId(long inId) {
		Messenger theMessenger = findByUserId(inId);

		if (theMessenger == null) {
			try {
				final User theUser = Factories.USER.find(inId);
				if (theUser != null) {
					theMessenger = new MessengerImpl(theUser, theUser.getUser_email());
				}
			} catch (final SQLException e) {
				theMessenger = findByUserId(inId);

				if (theMessenger == null) {
					MessengerFactoryImpl.LOGGER.fatal(e, e);
				}
			}
		}

		return theMessenger;
	}

	/**
	 * Finds a messenger from the given VObjectImpl id
	 * 
	 * @param inId
	 * @return
	 */
	private Messenger findByObjectId(long inId) {
		return findByKey(1, inId);
	}

	/**
	 * Finds the only messenger associated with the given user id
	 * 
	 * @param inUser
	 * @return null if it could not be found, the messenger otherwise
	 */
	private Messenger findByUserId(long inId) {
		return findByKey(2, inId);
	}

	public Messenger findByUser(User inUser) {
		return findByUserId(inUser.getId());
	}

	/**
	 * Find a MessengerImpl from the given MessageImpl id
	 * 
	 * @param inVObject
	 * @return
	 */
	public Messenger findSenderByMessageId(long inId) {
		final Messenger theMessenger = find(new String[] { "message_received" }, "message_received.message_id = ? and message_received.sender_id = messenger.id", Collections.singletonList((Object) inId));

		if (theMessenger == null) {
			return find(new String[] { "message_sent" }, "message_sent.message_id = ? and message_sent.sender_id = messenger.id", Collections.singletonList((Object) inId));
		}

		return theMessenger;
	}

	public Messenger findByObject(VObject theObject) {
		return (theObject != null) ? findByObjectId(theObject.getId()) : null;
	}

}
