package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.User;

public interface ContactFactory extends RecordFactory<Contact> {

	/**
	 * Recupère tous les amis de l'utilisateur
	 * 
	 * @param inUser given User
	 * @param inSkip number of request to skip
	 * @param inGetCount number of request to get
	 */
	List<Contact> findAllContactByUser(User inUser, int inSkip, int inGetCount);

	/**
	 * Récupère toutes les personnes possédant l'utilisateur spécifié dans leur liste d'amis
	 * @param inUser the given user
	 * @return
	 */
	List<Contact> findAllWhoHaveMeOnTheirFriendList(User inUser);

	/**
	 * Recupère le nombre d'ami de l'utilisateur
	 */
	long countContactByUser(User inUser);

	/**
	 * Recupère les demandes d'ami reçus sur l'utilisateur
	 * 
	 * @param inUser given User
	 * @param inSkip number of request to skip
	 * @param inGetCount number of request to get
	 */
	List<Contact> findAllReceivedContactRequest(User inUser, int inSkip, int inGetCount);

	/**
	 * Recupère le nombre de demandes d'ami reçus sur l'utilisateur
	 */
	long countReceivedContactRequest(User inUser);

	/**
	 * Recupère les demandes d'ami envoyés par l'utilisateur
	 * 
	 * @param inUser given User
	 * @param inSkip number of request to skip
	 * @param inGetCount number of request to get
	 */
	List<Contact> findAllSentContactRequest(User inUser, int inSkip, int inGetCount);

	/**
	 * Recupère le nombre de demandes d'ami envoyés par l'utilisateur
	 */
	long countSentContactRequest(User inUser);

	/**
	 * Create contact
	 * 
	 * @param inUser given User
	 * @param inContact given Contact
	 * @param inStatus given Status request
	 */
	Contact createContact(User inUser, User inContact, Contact.STATUS inStatus);

	/**
	 * recherche le row contact pour un utilisateur et un contact donné
	 * 
	 * @param inUser : l'utilisateur
	 * @param inContact : le contact
	 * @return
	 */
	Contact getContactByUserAndContact(User inUser, User inContact);

}
