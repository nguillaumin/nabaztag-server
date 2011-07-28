package net.violet.vadmin.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.commondev.utils.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.press.Create;
import net.violet.platform.api.exceptions.APIException;
import net.violet.vadmin.exceptions.InvalidFile;
import net.violet.vadmin.forms.AdminAddPressForm;
import net.violet.vadmin.util.SessionTools;
import net.violet.vadmin.util.UploadTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public class AdminAddPressAction extends AdminAction {

	/**
	 * Load the page after any process
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final AdminAddPressForm myForm = (AdminAddPressForm) form;

		myForm.setLangList(generateLanguagesList());
		myForm.setProductList(generateProductsList());

		saveErrors(request, myForm.getErrors());
		return mapping.getInputForward();
	}

	/**
	 * Create a new press
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addPress(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final String currentSessionId = SessionTools.getSessionId(request.getSession());
		final AdminAddPressForm myForm = (AdminAddPressForm) form;
		final ActionMessages errors = new ActionMessages();

		final String title = myForm.getTitle();
		final String summary = myForm.getSummary();
		final String url = myForm.getUrl();
		final String product = myForm.getProduct();
		final String language = myForm.getLanguage();
		final FormFile imageFile = myForm.getImageFile();
		String fileIdCreated = null;

		// Check form
		if (StringShop.EMPTY_STRING.equals(title)) {
			errors.add("emptyTitle", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (StringShop.EMPTY_STRING.equals(summary)) {
			errors.add("emptySummary", new ActionMessage(StringShop.EMPTY_STRING));
		}
		//TODO: regex to check the URL
		if (StringShop.EMPTY_STRING.equals(url)) {
			errors.add("emptyUrl", new ActionMessage(StringShop.EMPTY_STRING));
		}

		// Upload if the user filled the picture
		if (errors.isEmpty()) {
			if (imageFile.getFileSize() != 0 && currentSessionId != null) {
				try {
					fileIdCreated = UploadTools.uploadFile(imageFile, currentSessionId);
				} catch (final FileNotFoundException e1) {
					errors.add("uploadFailed", new ActionMessage(StringShop.EMPTY_STRING));
				} catch (final IOException e1) {
					errors.add("uploadFailed", new ActionMessage(StringShop.EMPTY_STRING));
				} catch (InvalidFile e) {
					errors.add("incorrectSize", new ActionMessage(StringShop.EMPTY_STRING));
				}
			}
		}

		// Call the API and insert the press
		if (errors.isEmpty()) {
			final Action theAction = new Create();
			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put(Create.TITLE_PARAM, title.trim());
			theParams.put(Create.URL_PARAM, url.trim());
			theParams.put(Create.PRODUCT_PARAM, product);
			theParams.put(Create.LANGUAGE_PARAM, language);
			theParams.put(Create.ABSTRACT_PARAM, summary);
			theParams.put(Create.PICTURE_PARAM, fileIdCreated);
			try {
				Admin.processRequest(theAction, theParams);
				errors.add("pressCreated", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (final APIException e) {
				errors.add("pressNotCreated", new ActionMessage(StringShop.EMPTY_STRING));
			}
		} else {
			myForm.setTitle(title);
			myForm.setSummary(summary);
			myForm.setUrl(url);
			myForm.setImageFile(imageFile);
		}

		myForm.setErrors(errors);
		myForm.setProduct(product);
		myForm.setLanguage(language);
		return load(mapping, myForm, request, response);
	}
}
