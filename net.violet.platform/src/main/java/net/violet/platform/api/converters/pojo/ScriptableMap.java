package net.violet.platform.api.converters.pojo;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.converters.ConversionException;
import net.violet.platform.api.converters.RhinoConverter;

import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;

/**
 * a Java Map directly usable as a Scriptable object
 * 
 * @author christophe
 */

public class ScriptableMap extends HashMap<String, Object> implements Scriptable {

	private Scriptable prototype;
	private Scriptable parent;

	/**
	 *
	 */
	public ScriptableMap() {
		super();
	}

	/**
	 * @param capacity
	 */
	public ScriptableMap(int capacity) {
		super(capacity);
	}

	/**
	 * @param map
	 */
	public ScriptableMap(Map<String, Object> map) {
		super(map);
	}

	/**
	 * @param obj
	 * @throws ConversionException
	 */
	public ScriptableMap(NativeJavaObject obj) throws ConversionException {

		super();

		final Object[] keys = obj.getIds();

		// Add all the properties inside our map
		for (int i = 0; i < keys.length; i++) {

			this.put((String) keys[i], RhinoConverter.convertFromJS(obj, false));
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

		return this.containsKey(name);
	}

	/**
	 * Map the numeric keys as strings
	 * 
	 * @see org.mozilla.javascript.Scriptable#has(int,
	 *      org.mozilla.javascript.Scriptable)
	 */
	public boolean has(int index, Scriptable start) {

		return this.containsKey(net.violet.common.StringShop.EMPTY_STRING + index);
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#get(java.lang.String,
	 *      org.mozilla.javascript.Scriptable)
	 */
	public Object get(String name, Scriptable start) {

		return (this.containsKey(name)) ? this.get(name) : Scriptable.NOT_FOUND;
	}

	/**
	 * Map the numeric keys as strings
	 * 
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

		this.put(name, value);

		// try {
		// Object adapted = RhinoConverter.convertFromJS(value, false);
		// this.put(name, adapted);
		// } catch (Exception e) {
		// LOGGER.warn("Object " + value + " has been dropped !" + );
		// }
	}

	/**
	 * Map the numeric keys as strings
	 * 
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

		this.remove(id);
	}

	/**
	 * Map the numeric keys as strings
	 * 
	 * @see org.mozilla.javascript.Scriptable#delete(int)
	 */
	public void delete(int index) {

		this.remove(net.violet.common.StringShop.EMPTY_STRING + index);
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

		return this.keySet().toArray();
	}

	/**
	 * @see org.mozilla.javascript.Scriptable#getDefaultValue(java.lang.Class)
	 */
	public Object getDefaultValue(Class typeHint) {

		return this.toString();
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

}
