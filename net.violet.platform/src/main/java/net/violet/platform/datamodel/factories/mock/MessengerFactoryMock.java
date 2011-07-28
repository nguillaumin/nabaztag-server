package net.violet.platform.datamodel.factories.mock;

import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.MessengerFactory;
import net.violet.platform.datamodel.mock.MessengerMock;

public class MessengerFactoryMock extends RecordFactoryMock<Messenger, MessengerMock> implements MessengerFactory {

	public MessengerFactoryMock() {
		super(MessengerMock.class);
	}

	public Messenger getByObject(VObject inObject) {
		return getByObjectId(inObject.getId());
	}

	public Messenger getByObjectId(long inId) {
		for (final Messenger theMessenger : findAll()) {
			if ((theMessenger.getObject() != null) && theMessenger.getObject().getId().equals(inId)) {
				return theMessenger;
			}
		}

		return null;
	}

	public Messenger getByUser(User inUser) {
		return getByUserId(inUser.getId());
	}

	public Messenger getByUserId(long inId) {
		for (final Messenger theMessenger : findAll()) {
			if ((theMessenger.getUser() != null) && theMessenger.getUser().getId().equals(inId)) {
				return theMessenger;
			}
		}

		return null;
	}

	public List<Messenger> findAllOwnedByUser(User inUser) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Messenger> findAllOwnedByUserId(long inId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Messenger findByUser(User inUser) {
		return getByUser(inUser);
	}

	public Messenger findFirstByUserId(long inId) {
		return getByUserId(inId);
	}

	public Messenger findSenderByMessageId(long inId) {
		return null;
	}

	public Messenger findByObject(VObject theObject) {
		return (theObject != null) ? getByObjectId(theObject.getId()) : null;
	}
}
