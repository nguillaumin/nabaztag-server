package net.violet.platform.api.converters.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Wrapper;

/**
 * A List directly usable as a scriptable JavaScript object.
 */
public class ScriptableListWrapper implements List<Object>, Scriptable, Wrapper, Serializable {

	private final List<Object> list;
	private Scriptable prototype;
	private Scriptable parent;

	public ScriptableListWrapper(List<Object> list) {
		this.list = list;
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#getClassName()
	 */
	public String getClassName() {
		return "Object";
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#has(int,
	 * org.mozilla.javascript.Scriptable)
	 */
	public boolean has(int index, Scriptable start) {
		// We catch the ArrayIndexOutOfBoundsException because the parser is
		// probably trying to retrieve
		// the value first(reading the statement left to right) then assign the
		// value to that index...
		// or something like that.
		try {
			return this.list.get(index) != null;
		} catch (final ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#get(int,
	 * org.mozilla.javascript.Scriptable)
	 */
	public Object get(int index, Scriptable start) {
		return this.list.get(index);
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#put(int,
	 * org.mozilla.javascript.Scriptable, java.lang.Object)
	 */
	public void put(int index, Scriptable start, Object value) {
		final int max = index + 1;

		if (max > this.list.size()) {
			for (int i = this.list.size(); i < index; i++) {
				this.list.add(i, null);
			}
			this.list.add(index, value);

		} else {
			this.list.set(index, value);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#delete(int)
	 */
	public void delete(int index) {
		this.list.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#delete(java.lang.String)
	 */
	public void delete(String name) {
		try {
			final int i = Integer.valueOf(name);
			this.list.remove(i);

		} catch (final Exception ignore) {}
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#get(java.lang.String,
	 * org.mozilla.javascript.Scriptable)
	 */
	public Object get(String name, Scriptable start) {

		if (name.equals("length")) {
			return new Integer(this.list.size());
		}

		try {
			final int i = Integer.valueOf(name);
			return this.list.get(i);

		} catch (final Exception ignore) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#getIds()
	 */
	public Object[] getIds() {
		final int len = this.list.size();
		final Integer[] ids = new Integer[len];
		for (int x = 0; x < len; x++) {
			ids[x] = new Integer(x);
		}
		return ids;
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#getDefaultValue(java.lang.Class)
	 */
	public Object getDefaultValue(Class hint) {
		return this.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#has(java.lang.String,
	 * org.mozilla.javascript.Scriptable)
	 */
	public boolean has(String name, Scriptable start) {
		return name.equals("length");
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.mozilla.javascript.Scriptable#hasInstance(org.mozilla.javascript.
	 * Scriptable)
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

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#put(java.lang.String,
	 * org.mozilla.javascript.Scriptable, java.lang.Object)
	 */
	public void put(String name, Scriptable start, Object value) {

	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#getPrototype()
	 */
	public Scriptable getPrototype() {

		return this.prototype;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.mozilla.javascript.Scriptable#setPrototype(org.mozilla.javascript
	 * .Scriptable)
	 */
	public void setPrototype(Scriptable prototype) {

		this.prototype = prototype;
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Scriptable#getParentScope()
	 */
	public Scriptable getParentScope() {

		return this.parent;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.mozilla.javascript.Scriptable#setParentScope(org.mozilla.javascript
	 * .Scriptable)
	 */
	public void setParentScope(Scriptable parent) {

		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * @see org.mozilla.javascript.Wrapper#unwrap()
	 */
	public Object unwrap() {
		return this.list;
	}

	public boolean add(Object o) {
		return this.list.add(o);
	}

	public void add(int index, Object element) {
		this.list.add(index, element);
	}

	public boolean addAll(Collection<? extends Object> c) {
		return this.list.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Object> c) {
		return this.list.addAll(index, c);
	}

	public void clear() {
		this.list.clear();
	}

	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	public Object get(int index) {
		return this.list.get(index);
	}

	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	public Iterator<Object> iterator() {
		return this.list.iterator();
	}

	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	public ListIterator<Object> listIterator() {
		return this.list.listIterator();
	}

	public ListIterator<Object> listIterator(int index) {
		return this.list.listIterator(index);
	}

	public boolean remove(Object o) {
		return this.list.remove(o);
	}

	public Object remove(int index) {
		return this.list.remove(index);
	}

	public boolean removeAll(Collection c) {
		return this.list.removeAll(c);
	}

	public boolean retainAll(Collection c) {
		return this.list.retainAll(c);
	}

	public Object set(int index, Object element) {
		return this.list.set(index, element);
	}

	public int size() {
		return this.list.size();
	}

	public List<Object> subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return this.list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.list.toArray(a);
	}

	@Override
	public String toString() {
		return this.list.toString();
	}

}
