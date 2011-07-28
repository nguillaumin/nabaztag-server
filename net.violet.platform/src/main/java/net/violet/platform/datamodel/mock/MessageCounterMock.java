/**
 * 
 */
package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.MessageCounter;

public class MessageCounterMock extends AbstractMockRecord<MessageCounter, MessageCounterMock> implements MessageCounter {

	protected Integer rabbit_state;
	protected Integer w_o_nabcast;
	protected Integer w_nabcast;

	public MessageCounterMock(long inId, int inState, Integer inWNabcast) {
		super(inId);
		this.rabbit_state = inState;
		this.w_nabcast = inWNabcast;
	}

	public long getMessenger_id() {
		return getId();
	}

	public RABBIT_STATE getRabbit_state() {
		return RABBIT_STATE.getState(this.rabbit_state);
	}

	public Integer getW_nabcast() {
		return this.w_nabcast;
	}

	public Integer getW_o_nabcast() {
		return this.w_o_nabcast;
	}

	public void setRabbit_state(RABBIT_STATE rabbit_state) {
		this.rabbit_state = rabbit_state.getValue();
	}

	public void resetCount() {
		setRabbit_state(RABBIT_STATE.INVALIDE);
		setW_o_nabcast(0);
		setW_nabcast(0);
	}

	public void setW_nabcast(int w_nabcast) {
		this.w_nabcast = w_nabcast;
	}

	public void setW_o_nabcast(int inWONabcast) {
		this.w_o_nabcast = inWONabcast;
	}

	public void invalidateDecrement() {
		this.rabbit_state = RABBIT_STATE.INVALIDE.getValue();
		this.w_nabcast = null;
		this.w_o_nabcast = null;
	}

	public void invalidateIncrement() {
		// Récupération de l'état.
		if ((getRabbit_state() != RABBIT_STATE.INVALIDE) && (getRabbit_state() != RABBIT_STATE.MORE)) {
			// Tout est incorrect.
			this.rabbit_state = RABBIT_STATE.INVALIDE.getValue();
		}
		this.w_nabcast = null;
		this.w_o_nabcast = null;
	}

}
