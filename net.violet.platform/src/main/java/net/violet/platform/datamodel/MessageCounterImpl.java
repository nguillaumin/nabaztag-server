package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class MessageCounterImpl extends ObjectRecord<MessageCounter, MessageCounterImpl> implements MessageCounter {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<MessageCounterImpl> SPECIFICATION = new SQLObjectSpecification<MessageCounterImpl>("message_counter", MessageCounterImpl.class, new SQLKey("messenger_id"));

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { "messenger_id", "rabbit_state", "w_o_nabcast", "w_nabcast", };

	/**
	 * Champs de l'enregistrement.
	 */
	protected long messenger_id;
	protected Integer rabbit_state;
	protected Integer w_o_nabcast;
	protected Integer w_nabcast;

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	public MessageCounterImpl(long id, int inRabbitState, int inWNabcast) throws SQLException {
		this.messenger_id = id;
		this.rabbit_state = inRabbitState;

		this.w_o_nabcast = null;
		this.w_nabcast = inWNabcast;

		init(MessageCounterImpl.NEW_COLUMNS);
	}

	protected MessageCounterImpl() {
		// This space for rent.
	}

	@Override
	public SQLObjectSpecification<MessageCounterImpl> getSpecification() {
		return MessageCounterImpl.SPECIFICATION;
	}

	/**
	 * @return the messenger_id
	 */
	public long getMessenger_id() {
		return this.messenger_id;
	}

	/**
	 * @return the rabbit_state
	 */
	public RABBIT_STATE getRabbit_state() {
		return RABBIT_STATE.getState(this.rabbit_state);
	}

	/**
	 * @param rabbit_state the rabbit_state to set
	 */
	public void setRabbit_state(RABBIT_STATE rabbit_state) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setRabbit_state(theUpdateMap, rabbit_state);
		update(theUpdateMap);
	}

	private void setRabbit_state(Map<String, Object> inUpdateMap, RABBIT_STATE inRabbitState) {
		if (inRabbitState.getValue() != this.rabbit_state) {
			this.rabbit_state = inRabbitState.getValue();
			inUpdateMap.put("rabbit_state", this.rabbit_state);
		}

	}

	/**
	 * @return the w_o_nabcast
	 */
	public Integer getW_o_nabcast() {
		return this.w_o_nabcast;
	}

	/**
	 * @return the w_nabcast
	 */
	public Integer getW_nabcast() {
		return this.w_nabcast;
	}

	/**
	 * @param w_nabcast the w_nabcast to set
	 */
	public void setW_nabcast(int w_nabcast) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setW_nabcast(theUpdateMap, w_nabcast);
		update(theUpdateMap);
	}

	private void setW_nabcast(Map<String, Object> inMapUpdate, Integer inWNabcast) {
		if (((inWNabcast == null) && (this.w_nabcast != null)) || ((inWNabcast != null) && !inWNabcast.equals(this.w_nabcast))) {

			if (inWNabcast != null) {
				if (inWNabcast == 0) {
					setRabbit_state(inMapUpdate, RABBIT_STATE.ZERO);
					setW_o_nabcast(inMapUpdate, 0);
				} else if (inWNabcast > 1) {
					setRabbit_state(inMapUpdate, RABBIT_STATE.MORE);
				} else {
					setRabbit_state(inMapUpdate, RABBIT_STATE.ONE);
				}
			}
			this.w_nabcast = inWNabcast;
			inMapUpdate.put("w_nabcast", inWNabcast);
		}

	}

	/**
	 * @param w_o_nabcast the w_o_nabcast to set
	 */
	public void setW_o_nabcast(int inWONabcast) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setW_o_nabcast(theUpdateMap, inWONabcast);
		update(theUpdateMap);
	}

	private void setW_o_nabcast(Map<String, Object> inMapUpdate, Integer inWONabcast) {
		if (((inWONabcast == null) && (this.w_o_nabcast != null)) || ((inWONabcast != null) && !inWONabcast.equals(this.w_o_nabcast))) {
			if ((inWONabcast != null) && (inWONabcast > 1)) {
				setRabbit_state(inMapUpdate, RABBIT_STATE.MORE);
			}
			this.w_o_nabcast = inWONabcast;
			inMapUpdate.put("w_o_nabcast", inWONabcast);
		}

	}

	public void resetCount() {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setW_nabcast(theUpdateMap, 0);
		update(theUpdateMap);
	}

	public void invalidateIncrement() {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		// Récupération de l'état.
		if ((getRabbit_state() != RABBIT_STATE.INVALIDE) && (getRabbit_state() != RABBIT_STATE.MORE)) {
			// Tout est incorrect.
			setRabbit_state(theUpdateMap, RABBIT_STATE.INVALIDE);
		}
		setW_nabcast(theUpdateMap, null);
		setW_o_nabcast(theUpdateMap, null);
		update(theUpdateMap);
	}

	public void invalidateDecrement() {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		// Tout est incorrect.
		setRabbit_state(theUpdateMap, RABBIT_STATE.INVALIDE);
		setW_nabcast(theUpdateMap, null);
		setW_o_nabcast(theUpdateMap, null);
		update(theUpdateMap);
	}
}
