package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import net.violet.platform.util.StringShop;


public final class BlocForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	/* GENERAL */
	private String _user_lang = StringShop.EMPTY_STRING;
	private String _user_id = StringShop.EMPTY_STRING;
	private int user_main;

	/* BLOC */
	private List _result = new ArrayList();
	private List _vector = new ArrayList();
	private String _title = StringShop.EMPTY_STRING;
	private String _desc = StringShop.EMPTY_STRING;
	private Object _object = new Object();
	private List<TreeMap<String, String>> contentGenericBloc = new ArrayList<TreeMap<String, String>>();
	private String mainTitle = StringShop.EMPTY_STRING;
	private String macAddress = StringShop.EMPTY_STRING;
	private String errors = "none";
	private String nablives = StringShop.EMPTY_STRING;

	/* PARAMETERS */
	private String _param1 = "0";
	private String _param2 = StringShop.EMPTY_STRING;

	/**
	 * @return the param1
	 */
	public String getParam1() {
		return this._param1;
	}

	/**
	 * @param param1 the param1 to set
	 */
	public void setParam1(String param1) {
		this._param1 = param1;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return this._desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this._desc = desc;
	}

	/**
	 * @return the result
	 */
	public List getResult() {
		return this._result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List result) {
		this._result = result;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this._title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this._title = title;
	}

	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return this._user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this._user_id = user_id;
	}

	/**
	 * @return the user_lang
	 */
	public String getUser_lang() {
		return this._user_lang;
	}

	/**
	 * @param user_lang the user_lang to set
	 */
	public void setUser_lang(String user_lang) {
		this._user_lang = user_lang;
	}

	/**
	 * @return the _object
	 */
	public Object getObject() {
		return this._object;
	}

	/**
	 * @param _object the _object to set
	 */
	public void setObject(Object object) {
		this._object = object;
	}

	/**
	 * @return the param2
	 */
	public String getParam2() {
		return this._param2;
	}

	/**
	 * @param param2 the param2 to set
	 */
	public void setParam2(String param2) {
		this._param2 = param2;
	}

	public List getVector() {
		return this._vector;
	}

	public void setVector(List vector) {
		this._vector = vector;
	}

	public int getUser_main() {
		return this.user_main;
	}

	public void setUser_main(int user_main) {
		this.user_main = user_main;
	}

	public List<TreeMap<String, String>> getContentGenericBloc() {
		return this.contentGenericBloc;
	}

	public void setContentGenericBloc(List<TreeMap<String, String>> contentGenericBloc) {
		this.contentGenericBloc = contentGenericBloc;
	}

	public String getMainTitle() {
		return this.mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public String getMacAddress() {
		return this.macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getErrors() {
		return this.errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public String getNablives() {
		return this.nablives;
	}

	public void setNablives(String nablives) {
		this.nablives = nablives;
	}

}
