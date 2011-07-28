package net.violet.platform.xmpp;

import org.jivesoftware.smack.util.collections.KeyValue;

public class KeyValueImpl<K, V> implements KeyValue {

	private K theKey;
	private V theValue;

	/**
	 * Constructeurs
	 */
	public KeyValueImpl(K inKey, V inValue) {
		this.theKey = inKey;
		this.theValue = inValue;
	}

	public KeyValueImpl() {
	}

	/**
	 * Accesseurs
	 */
	public K getKey() {
		return this.theKey;
	}

	public V getValue() {
		return this.theValue;
	}

	public void setTheKey(K inKey) {
		this.theKey = inKey;
	}

	public void setTheValue(V inValue) {
		this.theValue = inValue;
	}

	public void put(K inKey, V inValue) {
		this.theKey = inKey;
		this.theValue = inValue;
	}

	@Override
	public String toString() {

		return net.violet.common.StringShop.EMPTY_STRING.concat((this.theKey == null) ? "null" : this.theKey.toString()).concat("=").concat((this.theValue == null) ? "null" : this.theValue.toString());
	}
}
