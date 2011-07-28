package net.violet.platform.api.exceptions;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.violet.platform.api.converters.YAMLConverter.YAMLTagged;
import net.violet.platform.api.maps.PojoMap;

/**
 * Each Exception in the public API must have a unique numeric code and be
 * serializable in a POJO structure
 */
public class APIException extends Exception implements Map<String, Object>, YAMLTagged {

	private static final String EXCEPTION_YAML_TYPE = "Error";
	private static final String MESSAGE_KEY = "message";
	private final ErrorCode mErrType;
	private final Map<String, Object> mExceptionMap;
	private final String[] mReplacementValues;

	/**
	 * Constructeur à partir d'un message.
	 * 
	 * @param inMsg
	 */
	public APIException(APIErrorMessage inMsg) {
		this(ErrorCode.GenericError, (inMsg != null) ? inMsg.getMessage() : net.violet.common.StringShop.EMPTY_STRING, (Throwable) null, net.violet.common.StringShop.EMPTY_STRING);
	}

	/**
	 * Constructeur à partir d'un message paramétré.
	 * 
	 * @param inMsg
	 */
	public APIException(APIErrorMessage inMsg, String... inReplacementValues) {
		this(ErrorCode.GenericError, (inMsg != null) ? inMsg.getMessage() : net.violet.common.StringShop.EMPTY_STRING, (Throwable) null, inReplacementValues);
	}

	/**
	 * Constructeur à utiliser dans les classe filles
	 * 
	 * @param inMsg
	 */
	protected APIException(ErrorCode err, APIErrorMessage inMsg, String... inReplacementValues) {
		this(err, (inMsg != null) ? inMsg.getMessage() : net.violet.common.StringShop.EMPTY_STRING, (Throwable) null, inReplacementValues);
	}

	/**
	 * A utiliser quand la cause de l'erreur n'a pas été clairement identifiée
	 * 
	 * @param inMsg informations about the context of the error
	 * @param cause the original exception
	 */
	public APIException(APIErrorMessage inMsg, Throwable cause, String... inReplacementValues) {
		this(ErrorCode.GenericError, (inMsg != null) ? inMsg.getMessage() : net.violet.common.StringShop.EMPTY_STRING, cause, inReplacementValues);
	}

	/**
	 * Constructeur à partir d'un message.
	 * 
	 * @param inMsg
	 */
	public APIException(String inMsg) {
		this(ErrorCode.GenericError, inMsg, null);
	}

	/**
	 * Constructeur à utiliser dans les classe filles
	 * 
	 * @param inMsg
	 */
	protected APIException(ErrorCode err, APIErrorMessage inMsg, Throwable cause) {
		this(err, (inMsg != null) ? inMsg.getMessage() : net.violet.common.StringShop.EMPTY_STRING, cause);
	}

	/**
	 * Constructeur à utiliser dans les classe filles
	 * 
	 * @param inMsg
	 */
	private APIException(ErrorCode err, String inMessageStr, Throwable cause, String... inReplacementValues) {
		super(MessageFormat.format(inMessageStr, (Object[]) inReplacementValues), cause);
		this.mErrType = err;
		this.mReplacementValues = inReplacementValues;
		final Map<String, Object> theExceptionMap = new HashMap<String, Object>(8);
		theExceptionMap.put("status", this.mErrType.ERR_CODE);
		theExceptionMap.put("type", this.mErrType.STATUS.getLabel());
		theExceptionMap.put("title", this.mErrType.name());

		final String strErrMsg = MessageFormat.format(inMessageStr, (Object[]) this.mReplacementValues);

		if (strErrMsg != null) {
			theExceptionMap.put(APIException.MESSAGE_KEY, strErrMsg);

		}

		this.mExceptionMap = theExceptionMap;
	}

	/**
	 * Make a serialized exception revive !
	 * 
	 * @param inExceptionMap
	 * @throws InvalidParameterException 
	 */
	public APIException(PojoMap inExceptionMap) throws InvalidParameterException {
		this.mErrType = ErrorCode.findByCode(inExceptionMap.getInt("status", true));
		this.mReplacementValues = new String[0];
		this.mExceptionMap = new HashMap<String, Object>(8);
		this.mExceptionMap.put("status", inExceptionMap.getInt("status", true));
		this.mExceptionMap.put("type", inExceptionMap.getString("type", true));
		this.mExceptionMap.put("title", inExceptionMap.getString("title", true));
		this.mExceptionMap.put(APIException.MESSAGE_KEY, inExceptionMap.getString(APIException.MESSAGE_KEY));
	}

	/**
	 * @return the code of this exception
	 */
	public int getCode() {
		return this.mErrType.ERR_CODE;
	}

	/**
	 * This method seems useless, however it should probably return an ErroStatus object instead
	 * of a simple String object. 
	 * @return the status of this exception
	 */
	@Deprecated
	public String getStatus() {
		return this.mErrType.STATUS.getLabel();
	}

	/**
	 * Modify the value of one of the replacement values (values for the {0},
	 * ..{i} markers in the exception message)
	 * 
	 * @param i
	 * @param inStrvalues
	 */
	public void setReplacementValue(int i, String inStrvalues) {
		if (i < this.mReplacementValues.length) {
			this.mReplacementValues[i] = net.violet.common.StringShop.EMPTY_STRING + inStrvalues;
		}
	}

	public String getReplacementValue(int i) {
		return (i < this.mReplacementValues.length) ? this.mReplacementValues[i] : net.violet.common.StringShop.EMPTY_STRING;
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean containsKey(Object key) {
		return this.mExceptionMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.mExceptionMap.containsValue(value);
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return this.mExceptionMap.entrySet();
	}

	public Object get(Object key) {
		if (this.mExceptionMap != null) {
			return this.mExceptionMap.get(key);
		}
		return null;
	}

	public boolean isEmpty() {
		return this.mExceptionMap.isEmpty();
	}

	public Set<String> keySet() {
		return this.mExceptionMap.keySet();
	}

	public Object put(String key, Object value) {
		return this.mExceptionMap.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> t) {
		this.mExceptionMap.putAll(t);
	}

	public Object remove(Object key) {
		return this.mExceptionMap.remove(key);
	}

	public int size() {
		return this.mExceptionMap.size();
	}

	public Collection<Object> values() {
		return this.mExceptionMap.values();
	}

	@Override
	public String getMessage() {
		final Object theMessage = get(APIException.MESSAGE_KEY);

		return (theMessage != null) ? String.valueOf(theMessage) : super.getMessage();
	}

	/**
	 * The name of the YAML Tag to use to serialize this exception
	 * @see net.violet.platform.api.converters.YAMLConverter.YAMLTagged#getYAMLTag()
	 */
	public String getYAMLTag() {
		return "tag:violet.net:map:" + APIException.EXCEPTION_YAML_TYPE;
	}
}
