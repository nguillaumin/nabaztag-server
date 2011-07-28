package net.violet.mynabaztag.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

public abstract class MySrvPodcastAbstractForm extends MySrvAbstractForm {

	private String isV1;

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setIsV1("false");
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		return super.validate(mapping, request);
	}

	public String getIsV1() {
		return this.isV1;
	}

	public void setIsV1(String inV1) {
		this.isV1 = inV1;
	}

	@Override
	public int getSrvNbNews() {
		return 1;
	}
}
