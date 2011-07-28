package net.violet.platform.datamodel.factories;

import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageSent;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.common.FilesAccessor;
import net.violet.platform.dataobjects.MessageData;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.dataobjects.MessageData.StatusMessage;
import net.violet.platform.util.CCalendar;

public interface MessageFactory extends RecordFactory<Message>, FilesAccessor {

	/**
	 * Finds a {@link Message} from the given event_id
	 * 
	 * @param inId
	 * @return
	 */
	Message findByEventID(long inId);

	/**
	 * récupère le nombre de message de l'utilisateur dans la inbox selon le
	 * status passé et possibilité de filtrer par objet
	 * 
	 * @param inStatus
	 * @param inUser
	 * @param inObject : si null aucun filtre sur l'objet
	 * @return
	 */
	long countMessageReceivedOrArchivedByStatus(StatusMessage inStatus, User inUser, VObject inObject);

	/**
	 * récupère le nombre de message envoyé par l'utilisateur
	 * 
	 * @param inUser
	 * @return
	 */
	long countMessageSent(User inUser);

	/**
	 * recupère tous les messages envoyés de l'utilisateur
	 * 
	 * @param inUser : user concernés
	 * @param inSkip : nombre de row skipper
	 * @param inGetCount : nombre de row recupèrer
	 * @param inWalker itérateur
	 * @return
	 */
	int walkInMessageSent(User inUser, int inSkip, int inGetCount, JoinRecordsWalker<Message, MessageSent> inWalker);

	/**
	 * recupère tous les messages selon leur status des objets de l'utilisateur
	 * et possibilité de filtrer par objet
	 * 
	 * @param inStatus : {@link MessageData.StatusMessage}
	 * @param inUser : user concernés
	 * @param inObject : si null aucun filtre sur l'objet
	 * @param inSkip : nombre de row skipper
	 * @param inGetCount : nombre de row recupèrer
	 * @param inWalker itérateur
	 * @return
	 */
	int walkInMessageReceivedOrArchivedByStatus(StatusMessage inStatus, User inUser, VObject inObject, int inSkip, int inGetCount, JoinRecordsWalker<Message, MessageReceived> inWalker);

	int walkInMessageReceived(VObject inObject, JoinRecordsWalker<Message, MessageReceived> inWalker);

	Message create(Files inFile, String inText, CCalendar inTimeOfDelivery, Palette inPalette);

	/**
	 * utilisé lors du mode répondeur, afin de récupèrer un par un les messages
	 * en attente
	 * 
	 * @param inObject
	 * @return MessageImpl
	 */
	Message findFirstMessageReceived(VObject inObject);

	Message findLastMessageReceived(VObject inObject);

	Message findByXMPPID(String theID);

}
