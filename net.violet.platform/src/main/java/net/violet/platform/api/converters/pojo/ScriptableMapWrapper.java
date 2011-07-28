package net.violet.platform.api.converters.pojo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.RhinoConverter;

import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Wrapper;

/**
 * a wrapper around a Java Map to use it a Scriptable object
 * 
 * @author christophe
 */

public class ScriptableMapWrapper implements Map<String, Object>, Scriptable, Wrapper {

	private final Map<String, Object> map;
	private Scriptable prototype;
	private Scriptable parent;

	/**
	 * @param map
	 */
	public ScriptableMapWrapper(Map<String, Object> map) {
		this.map = map;
	}

	/**
	 * @param obj
	 * @throws ConversionException
	 */
	public ScriptableMapWrapper(NativeJavaObject obj) throws ConversionException {
		final Object[] keys = obj.getIds();
		final int len = keys.length;
		this.map = new HashMap<String, Object>(len, 1);

		for (int i = 0; i < len; i++) {

			this.put((String) keys[i], obj, RhinoConverter.convertFromJS(obj, false));
		}
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#getClassName()
	 */
	public String getClassName() {
		return "Object";
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#has(java.lang.String,
	 *      org.mozilla.javascript.Scriptable)
	 */
	public boolean has(String name, Scriptable start) {

		return this.map.containsKey(name);
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#has(int,
	 *      org.mozilla.javascript.Scriptable)
	 */
	public boolean has(int index, Scriptable start) {

		return this.map.containsKey(net.violet.common.StringShop.EMPTY_STRING + index);
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#get(java.lang.String,
	 *      org.mozilla.javascript.Scriptable)
	 */
	public Object get(String name, Scriptable start) {

		return (this.map.containsKey(name)) ? this.map.get(name) : Scriptable.NOT_FOUND;
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#get(int,
	 *      org.mozilla.javascript.Scriptable)
	 */
	public Object get(int index, Scriptable start) {

		return this.get(net.violet.common.StringShop.EMPTY_STRING + index, start);
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#put(java.lang.String,
	 *      org.mozilla.javascript.Scriptable, java.lang.Object)
	 */
	public void put(String name, Scriptable start, Object value) {

		this.map.put(name, value);

		// try {
		// Object adapted = RhinoConverter.convertFromJS(value, false);
		// map.put(name, adapted);
		// } catch (Exception e) {
		// LOGGER.warn("Object " + value + " has been dropped !" + );
		// }
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#put(int,
	 *      org.mozilla.javascript.Scriptable, java.lang.Object)
	 */
	public void put(int index, Scriptable start, Object value) {
		this.put(net.violet.common.StringShop.EMPTY_STRING + index, start, value);
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#delete(java.lang.String)
	 */
	public void delete(String id) {

		this.map.remove(id);
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#delete(int)
	 */
	public void delete(int index) {

		this.map.remove(net.violet.common.StringShop.EMPTY_STRING + index);
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

	/**
	 * @see org.mozilla.javascript.Scriptable#getIds()
	 */
	public Object[] getIds() {

		return this.map.keySet().toArray();
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#getDefaultValue(java.lang.Class)
	 */
	public Object getDefaultValue(Class typeHint) {

		return this.map.toString();
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
	 * @see org.mozilla.javascript.Wrapper#unwrap()
	 */
	public Object unwrap() {

		return this.map;
	}

	/**
	 *
	 */
	public void clear() {
		this.map.clear();
	}

	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	public Set<Map.Entry<String, Object>> entrySet() {
		return this.map.entrySet();
	}

	public Object get(Object key) {
		return this.map.get(key);
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public Set<String> keySet() {
		return this.map.keySet();
	}

	public Object put(String key, Object value) {
		return this.map.put(key.toString(), value);
	}

	public void putAll(Map<? extends String, ? extends Object> t) {
		this.map.putAll(t);
	}

	public Object remove(Object key) {
		return this.map.remove(key);
	}

	public int size() {
		return this.map.size();
	}

	public Collection<Object> values() {
		return this.map.values();
	}

	@Override
	public String toString() {
		return this.map.toString();
	}
}
