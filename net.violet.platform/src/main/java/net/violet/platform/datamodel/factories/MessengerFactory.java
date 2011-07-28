package net.violet.platform.datamodel.factories;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;

public interface MessengerFactory extends RecordFactory<Messenger> {

	/**
	 * Finds the only messenger associated with the given {@link VObject} id or
	 * creates it
	 * 
	 * @param inVObject
	 * @return the messenger
	 * @throws IllegalArgumentException
	 */
	Messenger getByObject(VObject inObject);

	/**
	 * Finds the only messenger associated with the given {@link VObject} id or
	 * creates it
	 * 
	 * @param inId
	 * @return the messenger
	 * @throws IllegalArgumentException
	 */
	Messenger getByObjectId(long inId);

	/**
	 * Finds the only messenger associated with the given {@link User} id or
	 * creates it
	 * 
	 * @param inUser
	 * @return the messenger
	 * @throws IllegalArgumentException
	 */
	Messenger getByUserId(long inId);

	/**
	 * Finds the only {@link Messenger} associated with the given {@link User} id or
	 * creates it
	 * 
	 * @param inUser
	 * @return the messenger
	 * @throws IllegalArgumentException
	 */
	Messenger getByUser(User inUser);

	/**
	 * Finds first messenger from the given UserImpl
	 * 
	 * @param inId
	 * @return
	 */
	Messenger findByUser(User inUser);

	/**
	 * Find a {@link Messenger} from the given {@link Message} id
	 * 
	 * @param inVObject
	 * @return
	 */
	Messenger findSenderByMessageId(long inId);

	Messenger findByObject(VObject theObject);

}
