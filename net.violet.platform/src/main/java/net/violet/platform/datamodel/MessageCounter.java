package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Number of messages in an object's inbox
 * 
 *
 */
public interface MessageCounter extends Record<MessageCounter> {

	public static enum RABBIT_STATE {
		ZERO(0),
		ONE(1),
		MORE(2),
		INVALIDE(null);

		public static RABBIT_STATE getState(Integer inValue) {
			if ((inValue == null) || (inValue < 0)) {
				return INVALIDE;
			}
			if (inValue == 0) {
				return ZERO;
			}
			if (inValue == 1) {
				return ONE;
			}

			return MORE;
		}

		private final Integer value;

		private RABBIT_STATE(Integer inValue) {
			this.value = inValue;
		}

		public Integer getValue() {
			return this.value;
		}

	};

	/**
	 * @return the messenger_id
	 */
	long getMessenger_id();

	/**
	 * @return the rabbit_state
	 */
	RABBIT_STATE getRabbit_state();

	/**
	 * @param rabbit_state the rabbit_state to set
	 */
	void setRabbit_state(RABBIT_STATE rabbit_state);

	/**
	 * @return the w_o_nabcast
	 */
	Integer getW_o_nabcast();

	/**
	 * @return the w_nabcast
	 */
	Integer getW_nabcast();

	/**
	 * @param w_nabcast the w_nabcast to set
	 */
	void setW_nabcast(int w_nabcast);

	/**
	 * @param w_o_nabcast the w_o_nabcast to set
	 */
	void setW_o_nabcast(int inWONabcast);

	void resetCount();

	void invalidateIncrement();

	void invalidateDecrement();

}
