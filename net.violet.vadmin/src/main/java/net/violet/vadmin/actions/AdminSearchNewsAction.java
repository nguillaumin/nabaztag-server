package net.violet.vadmin.actions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.commondev.utils.StringShop;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.news.Delete;
import net.violet.platform.api.actions.news.Get;
import net.violet.platform.api.actions.news.GetNews;
import net.violet.platform.api.actions.news.Update;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchProductException;
import net.violet.platform.api.maps.news.NewsInformationMap;
import net.violet.vadmin.exceptions.InvalidFile;
import net.violet.vadmin.forms.AdminSearchNewsForm;
import net.violet.vadmin.objects.data.GetNewsData;
import net.violet.vadmin.util.SessionTools;
import net.violet.vadmin.util.UploadTools;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public class AdminSearchNewsAction extends AdminAction {

	private static final String DISPLAY_NEWS = "displayNews";
	private static final String INFO_NEWS = "infoNews";

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final AdminSearchNewsForm myForm = (AdminSearchNewsForm) form;

		myForm.setLangList(generateLanguagesList());
		myForm.setProductList(generateProductsList());

		saveErrors(request, myForm.getErrors());
		return mapping.getInputForward();
	}

	public ActionForward displayContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final AdminSearchNewsForm myForm = (AdminSearchNewsForm) form;
		final ActionMessages errors = new ActionMessages();
		final String nameProduct = myForm.getProduct();
		final String theLanguage = myForm.getLanguage();

		final Action theAction = new GetNews();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(GetNews.LANGUAGE_PARAM, theLanguage);
		theParams.put(GetNews.PRODUCT_PARAM, nameProduct);

		try {
			final Object theResult = Admin.processRequest(theAction, theParams);

			if (theResult != null) {
				final List<GetNewsData> newsList = new ArrayList<GetNewsData>();
				for (final NewsInformationMap inNewsInformation : (List<NewsInformationMap>) theResult) {
					newsList.add(buildGetNewsData(inNewsInformation));
				}
				myForm.setNewsList(newsList);
			}
		} catch (final InvalidParameterException e) {
			errors.add("invalidParam", new ActionMessage(StringShop.EMPTY_STRING));
		} catch (final NoSuchProductException e) {
			errors.add("noSuchProduct", new ActionMessage(StringShop.EMPTY_STRING));
		} catch (final APIException e) {
			errors.add("APIException2", new ActionMessage(e.toString()));
		}

		myForm.setLanguage(theLanguage);
		myForm.setProduct(nameProduct);
		myForm.setDisplay(AdminSearchNewsAction.DISPLAY_NEWS);
		myForm.setErrors(errors);

		return load(mapping, form, request, response);
	}

	public ActionForward displayInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final AdminSearchNewsForm myForm = (AdminSearchNewsForm) form;
		final ActionMessages errors = myForm.getErrors();
		final String idNews = myForm.getIdNews();
		final String nameProduct = myForm.getProduct();
		final String theLanguage = myForm.getLanguage();

		final Action theAction = new Get();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", idNews);

		try {
			final NewsInformationMap theInformationMap = (NewsInformationMap) Admin.processRequest(theAction, theParams);
			myForm.setTheNewsData(buildGetNewsData(theInformationMap));
			myForm.setDisplay(AdminSearchNewsAction.INFO_NEWS);
		} catch (final InvalidParameterException e) {
			errors.add("invalidParam", new ActionMessage(StringShop.EMPTY_STRING));
		} catch (final NoSuchProductException e) {
			errors.add("noSuchProduct", new ActionMessage(StringShop.EMPTY_STRING));
		} catch (final APIException e) {
			errors.add("APIException2", new ActionMessage(e.toString()));
		}

		myForm.setErrors(errors);
		myForm.setLanguage(theLanguage);
		myForm.setProduct(nameProduct);
		return load(mapping, myForm, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final String currentSessionId = SessionTools.getSessionId(request.getSession());
		final AdminSearchNewsForm myForm = (AdminSearchNewsForm) form;
		final ActionMessages errors = new ActionMessages();
		final String pubYear = myForm.getPubYear();
		final String pubMonth = myForm.getPubMonth();
		final String pubDay = myForm.getPubDay();
		final String expYear = myForm.getExpYear();
		final String expMonth = myForm.getExpMonth();
		final String expDay = myForm.getExpDay();
		final FormFile smallImageFile = myForm.getSmallImageFile();
		final FormFile bigImageFile = myForm.getBigImageFile();
		String smallFileIdCreated = null;
		String bigFileIdCreated = null;

		if (StringShop.EMPTY_STRING.equals(myForm.getTitle())) {
			errors.add("emptyTitle", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (StringShop.EMPTY_STRING.equals(myForm.getSummary())) {
			errors.add("emptyAbstract", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (StringShop.EMPTY_STRING.equals(myForm.getIntro())) {
			errors.add("emptyIntro", new ActionMessage(StringShop.EMPTY_STRING));
		}
		if (StringShop.EMPTY_STRING.equals(myForm.getBody())) {
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
			final Action theAction = new Update();
			final Map<String, Object> theParams = new HashMap<String, Object>();
			theParams.put(NewsInformationMap.TITLE, myForm.getTitle());
			theParams.put(NewsInformationMap.ABSTRACT, myForm.getSummary());
			theParams.put(NewsInformationMap.INTRO, myForm.getIntro());
			theParams.put(NewsInformationMap.BODY, myForm.getBody());
			theParams.put(NewsInformationMap.DATE_PUB, pubDate);
			theParams.put(NewsInformationMap.DATE_EXP, expDate);
			theParams.put(NewsInformationMap.LANGUAGE, myForm.getLanguage());
			theParams.put(NewsInformationMap.PRODUCT, myForm.getProduct());
			theParams.put(NewsInformationMap.PICTURE_SMALL, smallFileIdCreated);
			theParams.put(NewsInformationMap.PICTURE_BIG, bigFileIdCreated);

			final Map<String, Object> theMap = new HashMap<String, Object>();
			theMap.put("id", myForm.getIdNews());
			theMap.put(Update.NEWS_PARAM, theParams);

			try {
				final NewsInformationMap theInformationMap = (NewsInformationMap) Admin.processRequest(theAction, theMap);
				myForm.setTheNewsData(buildGetNewsData(theInformationMap));
				myForm.setDisplay(AdminSearchNewsAction.INFO_NEWS);
			} catch (final APIException e) {
				errors.add("updateFailed", new ActionMessage(StringShop.EMPTY_STRING));
			}
		}

		myForm.setErrors(errors);
		if (!errors.isEmpty()) {
			myForm.setIdNews(myForm.getIdNews());
			myForm.setProduct(myForm.getProduct());
			myForm.setLanguage(myForm.getLanguage());
			return displayInfo(mapping, myForm, request, response);
		}
		errors.add("updateSucceeded", new ActionMessage(StringShop.EMPTY_STRING));
		myForm.setErrors(errors);
		return load(mapping, myForm, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final AdminSearchNewsForm myForm = (AdminSearchNewsForm) form;
		final String idNews = myForm.getIdNews();
		final String nameProduct = myForm.getProduct();
		final String theLanguage = myForm.getLanguage();

		final Action theAction = new Delete();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("id", idNews);
		try {
			Admin.processRequest(theAction, theParams);
		} catch (final APIException e) {

		}

		myForm.setLanguage(theLanguage);
		myForm.setProduct(nameProduct);

		return displayContent(mapping, form, request, response);
	}

	private GetNewsData buildGetNewsData(NewsInformationMap NewsInformation) {
		final GetNewsData theFormData = new GetNewsData();
		if (NewsInformation != null) {
			theFormData.setId((String) NewsInformation.get(NewsInformationMap.ID));
			theFormData.setLang((String) NewsInformation.get(NewsInformationMap.LANGUAGE));
			theFormData.setBody((String) NewsInformation.get(NewsInformationMap.BODY));
			if (NewsInformation.get(NewsInformationMap.DATE_PUB) != null) {
				Date thePubDate = (Date) NewsInformation.get(NewsInformationMap.DATE_PUB);
				Calendar pubCal = Calendar.getInstance();
				pubCal.setTime(thePubDate);
				theFormData.setPubYear(String.valueOf(pubCal.get(Calendar.YEAR)));
				theFormData.setPubMonth(String.valueOf(pubCal.get(Calendar.MONTH)));
				theFormData.setPubDay(String.valueOf(pubCal.get(Calendar.DAY_OF_MONTH)));
			}
			if (NewsInformation.get(NewsInformationMap.DATE_EXP) != null) {
				Date thePubDate = (Date) NewsInformation.get(NewsInformationMap.DATE_EXP);
				Calendar expCal = Calendar.getInstance();
				expCal.setTime(thePubDate);
				theFormData.setPubYear(String.valueOf(expCal.get(Calendar.YEAR)));
				theFormData.setPubMonth(String.valueOf(expCal.get(Calendar.MONTH)));
				theFormData.setPubDay(String.valueOf(expCal.get(Calendar.DAY_OF_MONTH)));
			}
			theFormData.setDate_exp(NewsInformation.get(NewsInformationMap.DATE_EXP));
			theFormData.setDate_pub(NewsInformation.get(NewsInformationMap.DATE_PUB));
			theFormData.setIntro((String) NewsInformation.get(NewsInformationMap.INTRO));
			theFormData.setNewsabstract((String) NewsInformation.get(NewsInformationMap.ABSTRACT));
			theFormData.setPicture_big((String) NewsInformation.get(NewsInformationMap.PICTURE_BIG));
			theFormData.setPicture_small((String) NewsInformation.get(NewsInformationMap.PICTURE_SMALL));
			theFormData.setProduct((String) NewsInformation.get(NewsInformationMap.PRODUCT));
			theFormData.setTitle((String) NewsInformation.get(NewsInformationMap.TITLE));
		}
		return theFormData;
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
