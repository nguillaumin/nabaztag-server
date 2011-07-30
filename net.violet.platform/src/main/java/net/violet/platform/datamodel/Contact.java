package net.violet.platform.datamodel;

import java.sql.Timestamp;

import net.violet.db.records.Record;

/**
 * Friends management
 * 
 *
 */
public interface Contact extends Record<Contact> {

	/**
	 * Status de la demande d'ami
	 */
	public static enum STATUS {
		PENDING,
		REJECTED,
		ACCEPTED,
		AUTOMATICALLY_ACCEPTED
	};

	/**
	 * Utilisateur qui a fait la demande d'ami
	 */
	User getPerson();

	/**
	 * Utilisateur qui recoit la demande d'ami
	 */
	User getContact();

	/**
	 * Date de de la demande de l'invitation
	 */
	Timestamp getInvitationDate();

	/**
	 * Status de la demande d'ami
	 */
	String getStatus();

	/**
	 * Change le status
	 */
	void changeContact(Contact.STATUS inStatus);

}
