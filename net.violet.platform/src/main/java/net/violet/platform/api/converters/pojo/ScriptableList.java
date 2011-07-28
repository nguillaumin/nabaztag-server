package net.violet.platform.api.converters.pojo;

import java.util.ArrayList;
import java.util.Set;

import org.mozilla.javascript.Scriptable;

/**
 * @author christophe
 */

public class ScriptableList extends ArrayList<Object> implements Scriptable {

	private Scriptable prototype;
	private Scriptable parent;

	public ScriptableList() {
		super();
	}

	public ScriptableList(int capacity) {
		super(capacity);
	}

	public ScriptableList(Set<String> set) {
		super();
		this.addAll(set);
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#getClassName()
	 */
	public String getClassName() {
		return "Object";
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#has(int,
	 *      org.mozilla.javascript.Scriptable)
	 */
	public boolean has(int index, Scriptable start) {
		// We catch the ArrayIndexOutOfBoundsException because the parser is
		// probably trying to retrieve
		// the value first(reading the statement left to right) then assign the
		// value to that index...
		// or something like that.
		try {
			return this.get(index) != null;
		} catch (final ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#get(int,
	 *      org.mozilla.javascript.Scriptable)
	 */
	public Object get(int index, Scriptable start) {
		return this.get(index);
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#put(int,
	 *      org.mozilla.javascript.Scriptable, java.lang.Object)
	 */
	public void put(int index, Scriptable start, Object value) {
		final int max = index + 1;

		if (max > this.size()) {
			for (int i = this.size(); i < index; i++) {
				this.add(i, null);
			}
			this.add(index, value);

		} else {
			this.set(index, value);
		}
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#delete(int)
	 */
	public void delete(int index) {
		this.remove(index);
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#delete(java.lang.String)
	 */
	public void delete(String name) {
		try {
			final int i = Integer.valueOf(name);
			this.remove(i);

		} catch (final Exception ignore) {}
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#get(java.lang.String,
	 *      org.mozilla.javascript.Scriptable)
	 */
	public Object get(String name, Scriptable start) {

		if (name.equals("length")) {
			return new Integer(this.size());
		}

		try {
			final int i = Integer.valueOf(name);
			return this.get(i);

		} catch (final Exception ignore) {
			return null;
		}
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#getIds()
	 */
	public Object[] getIds() {
		final int len = this.size();
		final Integer[] ids = new Integer[len];
		for (int x = 0; x < len; x++) {
			ids[x] = new Integer(x);
		}
		return ids;
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#getDefaultValue(java.lang.Class)
	 */
	public Object getDefaultValue(Class hint) {
		return this.toString();
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#has(java.lang.String,
	 *      org.mozilla.javascript.Scriptable)
	 */
	public boolean has(String name, Scriptable start) {
		return name.equals("length");
	}

	public void put(String name, Scriptable start, Object value) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see org.mozilla.javascript.Scriptable#hasInstance(org.mozilla.javascript.Scriptable)
	 */
	public boolean hasInstance(Scriptable value) {

		Scriptable proto = value.getPrototype();

		while (proto != null) {

			if (proto.equals(this)) {
				return true;
			}

			proto = proto.getPrototype();
		}

		return false;
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#getPrototype()
	 */
	public Scriptable getPrototype() {

		return this.prototype;
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#setPrototype(org.mozilla.javascript.Scriptable)
	 */
	public void setPrototype(Scriptable prototype) {

		this.prototype = prototype;
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#getParentScope()
	 */
	public Scriptable getParentScope() {

		return this.parent;
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#setParentScope(org.mozilla.javascript.Scriptable)
	 */
	public void setParentScope(Scriptable parent) {

		this.parent = parent;
	}

}
