package net.violet.platform.message;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.MessageCounter.RABBIT_STATE;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.StringShop;

/**
 * Classe de base pour les messages.
 */
abstract class AbstractMessage implements Message {

	/**
	 * Position de l'oreille gauche.
	 */
	private Integer mLeftEarPosition;
	/**
	 * Position de l'oreille droite.
	 */
	private Integer mRightEarPosition;
	/**
	 * Sources (clé: type, valeur: valeur), ou <code>null</code> si on n'inclut
	 * pas les sources.
	 */
	private Map<String, Integer> mSources;
	/**
	 * Nombre de messages.
	 */
	private RABBIT_STATE mNbMessages;
	/**
	 * le statut du lapin actif (0) ou endormi (1)
	 */
	private Integer mStatus;
	/**
	 * Si on envoie une mise à jour des sources ou toutes les sources.
	 */
	private boolean mSourceUpdate;
	/**
	 * Emetteur, ou <code>null</code> si le message n'a pas d'expéditeur.
	 */
	private User mSender;

	private CCalendar deliveryDate;

	/**
	 * The application sending the message
	 */
	private Application mApplication;

	/**
	 * Titre du message.
	 */
	private String mTitle;

	/**
	 * ID de l'événement ou <code>null</code> si l'événement n'a pas d'ID.
	 */
	private Long mEventID;

	/**
	 * ID du MessageImpl ou <code>null</code>
	 */
	private Long mMessageID;

	/**
	 * TTL en secondes (durée de vie dans la file d'attente).
	 */
	private int mTTLInSecond;

	/**
	 * Constructeur par défaut.
	 */
	protected AbstractMessage() {
		// Valeurs par défaut.
		this.mTitle = StringShop.EMPTY_STRING;
		this.mTTLInSecond = Constantes.QUEUE_TTL_DEFAULT;
	}

	/**
	 * Accesseur sur le destinataire.
	 */
	public VObject getReceiver() {
		return null;
	}

	/**
	 * Force la position des oreilles (sinon on envoie la position des oreilles
	 * dans la base).
	 */
	public final void setEars(int inLeftEar, int inRightEar) {
		this.mLeftEarPosition = inLeftEar;
		this.mRightEarPosition = inRightEar;
	}

	/**
	 * Modifie le status du lapin veille ou actif
	 */
	public final void setStatus(int inStatus) {
		this.mStatus = inStatus;
	}

	/**
	 * Modifie la valeur d'une source.
	 * 
	 * @param inSourceValue nouvelle valeur de la source, ou DELETE_SRC_VALUE
	 *            pour supprimer la source.
	 */
	public final void setSourceValue(SOURCE source, int sourceValue) {
		if (this.mSources == null) {
			this.mSources = new HashMap<String, Integer>();
		}
		this.mSources.put(Integer.toString(source.getId()), sourceValue);
	}

	/**
	 * Indique si on met à jour les sources (nouveau format) ou si on envoie
	 * tout (ancien format).
	 */
	public final void setSourceUpdate(boolean inSourceUpdate) {
		this.mSourceUpdate = inSourceUpdate;
	}

	/**
	 * Sélecteur sur l'expéditeur.
	 */
	public void setSender(User inSender) {
		this.mSender = inSender;
	}

	public void setApplication(Application inApplication) {
		this.mApplication = inApplication;
	}

	/**
	 * Sélecteur sur le titre.
	 */
	public void setTitle(String inTitle) {
		this.mTitle = inTitle;
	}

	public void setDeliveryDate(CCalendar inDeliveryDate) {
		this.deliveryDate = inDeliveryDate;
	}

	/**
	 * Sélecteur sur la position de l'oreille gauche.
	 */
	public void setLeftEarPosition(Integer inLeftEarPosition) {
		this.mLeftEarPosition = inLeftEarPosition;
	}

	/**
	 * Sélecteur sur la position de l'oreille droite.
	 */
	public void setRightEarPosition(Integer inRightEarPosition) {
		this.mRightEarPosition = inRightEarPosition;
	}

	/**
	 * Sélecteur sur le nombre de message.
	 */
	public void setNbMessages(RABBIT_STATE inNbMessages) {
		this.mNbMessages = inNbMessages;
	}

	/**
	 * Accesseur sur l'ID du message.
	 * 
	 * @return l'ID du message ou <code>null</code> si le message n'a pas d'ID.
	 */
	public final Long getEventID() {
		return this.mEventID;
	}

	/**
	 * Accesseur sur le titre.
	 */
	public String getTitle() {
		return this.mTitle;
	}

	public CCalendar getDeliveryDate() {
		return this.deliveryDate;
	}

	/**
	 * Accesseur sur la trame "mode du lapin": MODE_ACTIF, MODE_VEILLE ou
	 * <code>null</code> si le message ne contient pas cette trame.
	 */
	public Integer getStatus() {
		return this.mStatus;
	}

	/**
	 * Accesseur sur le mode des sources.
	 * 
	 * @return <code>true</code> si les sources sont une mise à jour,
	 *         <code>false</code> sinon.
	 */
	public boolean isSourceModeUpdate() {
		return this.mSourceUpdate;
	}

	/**
	 * Accesseur sur les sources.
	 * 
	 * @return les sources ou <code>null</code> si le message ne contient pas
	 *         cette trame.
	 */
	public Map<String, Integer> getSources() {
		return this.mSources;
	}

	/**
	 * Accesseur sur la position de l'oreille gauche.
	 * 
	 * @return la position de l'oreille gauche ou <code>null</code> si le
	 *         message ne contient pas cette trame.
	 */
	public Integer getLeftEarPosition() {
		return this.mLeftEarPosition;
	}

	/**
	 * Accesseur sur la position de l'oreille droite.
	 * 
	 * @return la position de l'oreille droite ou <code>null</code> si le
	 *         message ne contient pas cette trame.
	 */
	public Integer getRightEarPosition() {
		return this.mRightEarPosition;
	}

	/**
	 * Accesseur sur le nombre de messages.
	 * 
	 * @return le nombre de messages ou <code>RABBIT_STATE.INVALIDE</code> si le
	 *         message ne contient pas cette trame.
	 */
	public RABBIT_STATE getNbMessages() {
		return this.mNbMessages;
	}

	/**
	 * Accesseur sur l'expediteur.
	 */
	public User getSender() {
		return this.mSender;
	}

	public Application getApplication() {
		return this.mApplication;
	}

	/**
	 * Sélecteur sur l'ID du message.
	 * 
	 * @param inEventID id du message (en base) ou <code>null</code>
	 */
	public final void setEventID(Long inEventID) {
		this.mEventID = inEventID;
	}

	/**
	 * @return the mMessageID
	 */
	public Long getMessageID() {
		return this.mMessageID;
	}

	/**
	 * @param messageID the mMessageID to set
	 */
	public void setMessageID(Long messageID) {
		this.mMessageID = messageID;
	}

	/**
	 * TTL du message dans la file d'attente, en secondes.
	 */
	public int getTTLInSecond() {
		return this.mTTLInSecond;
	}

	/**
	 * TTL du message dans la file d'attente, en secondes.
	 */
	public void setTTLInSecond(int inTTL) {
		this.mTTLInSecond = inTTL;
	}
}
