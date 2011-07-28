package net.violet.mynabaztag.form;

import javax.servlet.http.HttpServletRequest;

import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionMapping;


public final class MySrvWebRadioFreePlayerForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private String isV1 = "true";

	private int wradioId;
	private String rabbitName;
	private String webRadioUrl;
	private String dispatch;
	private String isPlaying = "false";

	public String getIsPlaying() {
		return this.isPlaying;
	}

	public void setIsPlaying(String isPlaying) {
		this.isPlaying = isPlaying;
	}

	/**
	 * Used to set the encoding for the form this fixes problems with accents
	 * when text's going from the form (JSP) to the form object (JAVA) *
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		setWebRadioUrl(StringShop.EMPTY_STRING);
		setDispatch(StringShop.EMPTY_STRING);

		super.reset(mapping, request);
	}

	/**
	 * @return the webRadioUrl
	 */
	public String getWebRadioUrl() {
		return this.webRadioUrl;
	}

	/**
	 * @param webRadioUrl the webRadioUrl to set
	 */
	public void setWebRadioUrl(String webRadioUrl) {
		this.webRadioUrl = webRadioUrl;
	}

	/**
	 * @return the webradioId
	 */
	public int getWradioId() {
		return this.wradioId;
	}

	/**
	 * @param webradioId the webradioId to set
	 */
	public void setWradioId(int wradioId) {
		this.wradioId = wradioId;
	}

	/**
	 * @return the dispatcher
	 */
	public String getDispatch() {
		return this.dispatch;
	}

	/**
	 * @param dispatcher the dispatcher to set
	 */
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	public String getRabbitName() {
		return this.rabbitName;
	}

	public void setRabbitName(String rabbitName) {
		this.rabbitName = rabbitName;
	}

	public String getIsV1() {
		return this.isV1;
	}

	public void setIsV1(String isV1) {
		this.isV1 = isV1;
	}

}
