package net.violet.vadmin.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.common.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.news.Create;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.vadmin.exceptions.InvalidFile;
import net.violet.vadmin.forms.AdminAddNewsForm;
import net.violet.vadmin.util.SessionTools;
import net.violet.vadmin.util.UploadTools;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public class AdminAddNewsAction extends AdminAction {

	private static final Logger LOGGER = Logger.getLogger(AdminAddNewsAction.class);

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final AdminAddNewsForm myForm = (AdminAddNewsForm) form;

		myForm.setLangList(generateLanguagesList());
		myForm.setProductList(generateProductsList());

		saveErrors(request, myForm.getErrors());
		return mapping.getInputForward();
	}

	public ActionForward addNews(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final String currentSessionId = SessionTools.getSessionId(request.getSession());
		final AdminAddNewsForm myForm = (AdminAddNewsForm) form;
		final ActionMessages errors = new ActionMessages();

		final String title = myForm.getTitle();
		final String intro = myForm.getIntro();
		final String body = myForm.getBody();
		final String extract = myForm.getExtract();
		final String product = myForm.getProduct();
		final String language = myForm.getLanguage();
		final String pubYear = myForm.getPubYear();
		final String pubMonth = myForm.getPubMonth();
		final String pubDay = myForm.getPubDay();
		final String expYear = myForm.getExpYear();
		final String expMonth = myForm.getExpMonth();
		final String expDay = myForm.getExpDay();
		final FormFile smallImageFile = myForm.getSmallImageFile();
		final FormFile bigImageFile = myForm.getBigImageFile();

		if (StringShop.EMPTY_STRING.equals(title)) {
			errors.add("emptyTitle", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (StringShop.EMPTY_STRING.equals(extract)) {
			errors.add("emptyAbstract", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (StringShop.EMPTY_STRING.equals(intro)) {
			errors.add("emptyIntro", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (StringShop.EMPTY_STRING.equals(body)) {
			errors.add("emptyBody", new ActionMessage(StringShop.EMPTY_STRING));
		}

		Date pubDate = checkDate(pubYear, pubMonth, pubDay);
		Date expDate = checkDate(expYear, expMonth, expDay);
		if (pubDate == null) {
			errors.add("invalidDatePub", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (expDate == null) {
			//errors.add("invalidDateExp", new ActionMessage(StringShop.EMPTY_STRING));
		}

		String smallFileIdCreated = null;
		String bigFileIdCreated = null;
		if (errors.isEmpty()) {
			if (smallImageFile.getFileSize() != 0 && bigImageFile.getFileSize() != 0 && currentSessionId != null) {
				try {
					smallFileIdCreated = UploadTools.uploadFile(smallImageFile, currentSessionId);
					bigFileIdCreated = UploadTools.uploadFile(bigImageFile, currentSessionId);
				} catch (final FileNotFoundException e1) {
					errors.add("uploadFailed", new ActionMessage(StringShop.EMPTY_STRING));
				} catch (final IOException e1) {
					errors.add("uploadFailed", new ActionMessage(StringShop.EMPTY_STRING));
				} catch (InvalidFile e) {
					errors.add("incorrectSize", new ActionMessage(StringShop.EMPTY_STRING));
				}
			}
		}

		if (errors.isEmpty()) {
			final Action theAction = new Create();
			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put(Create.TITLE_PARAM, title);
			theParams.put(Create.INTRO_PARAM, intro);
			theParams.put(Create.BODY_PARAM, body);
			theParams.put(Create.ABSTRACT_PARAM, extract);
			theParams.put(Create.PRODUCT_PARAM, product);
			theParams.put(Create.LANGUAGE_PARAM, language);
			theParams.put(Create.DATE_PUB_PARAM, pubDate);
			theParams.put(Create.DATE_EXP_PARAM, expDate);
			theParams.put(Create.SMALL_PARAM, smallFileIdCreated);
			theParams.put(Create.BIG_PARAM, bigFileIdCreated);
			try {
				Admin.processRequest(theAction, theParams);
				errors.add("newsCreated", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (final InvalidParameterException e) {
				AdminAddNewsAction.LOGGER.info("InvalidParameterException = " + e.getMessage());
				errors.add("newsNotCreated", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (final NoSuchProductException e) {
				AdminAddNewsAction.LOGGER.info("NoSuchProductException = " + e.getMessage());
				errors.add("newsNotCreated", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (final APIException e) {
				AdminAddNewsAction.LOGGER.info("APIException = " + e.getMessage());
				errors.add("newsNotCreated", new ActionMessage(StringShop.EMPTY_STRING));
			}
		} else {
			myForm.setTitle(title);
			myForm.setIntro(intro);
			myForm.setBody(body);
			myForm.setExtract(extract);
			myForm.setPubYear(myForm.getPubYear());
			myForm.setPubMonth(myForm.getPubMonth());
			myForm.setPubDay(myForm.getPubDay());
			myForm.setExpYear(myForm.getExpYear());
			myForm.setExpMonth(myForm.getExpMonth());
			myForm.setExpDay(myForm.getExpDay());
		}

		myForm.setErrors(errors);
		myForm.setProduct(product);
		myForm.setLanguage(language);
		return load(mapping, myForm, request, response);
	}

	private Date checkDate(String inYear, String inMonth, String inDay) {
		Integer year = null;
		Integer month = null;
		Integer day = null;
		try {
			year = Integer.parseInt(inYear);
			month = Integer.parseInt(inMonth);
			day = Integer.parseInt(inDay);
		} catch (NumberFormatException e) {
			return null;
		}
		if (inYear != null && inMonth != null && inDay != null) {
			return getDate(year, month, day);
		}
		return null;
	}
}
