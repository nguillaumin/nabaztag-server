package net.violet.vadmin.actions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.commondev.utils.StringShop;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.CrawlImpl;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.util.MailTools;
import net.violet.vadmin.forms.AdminAddWeatherForm;
import net.violet.vadmin.objects.data.LanguageData;
import net.violet.vadmin.util.DicoAPI;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AdminAddWeatherAction extends AdminLocationAction {

	
	private static long WEATHER_TYPE = 1;
	private static String WEATHER_PREFIX = "Nmeteo.";
	private static Pattern CHECK_URL = Pattern.compile("(http://fr.weather.com/weather/detail/)(.*)(dayNum=0)"); 
	private static String SOURCE_WEATHER = ".weather"; 
	private static String SOURCE_TEMP = ".temp"; 
	private static String DICO_PREFIX = "source_weather/";
	private static String MAIL_RECIPIENT = "gerard@violet.net";
	
	/**
	 * Register a new city for the weather report. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		final AdminAddWeatherForm myForm = (AdminAddWeatherForm) form;
		final ActionMessages errors = new ActionMessages();
		
		
		final String country = CountryData.findByCode(myForm.getCountry()).getPays_nom();
		final String newCity = myForm.getNewCity();
		final String city = (!StringShop.EMPTY_STRING.equals(newCity)) ? newCity : myForm.getCity();
		final String weatherUrl = myForm.getUrl().trim();
		final String dicoKey = country.toUpperCase()+StringShop.POINT+city;
		final String source = WEATHER_PREFIX+dicoKey;
		
		if(StringShop.EMPTY_STRING.equals(weatherUrl)){
			errors.add("emptyUrl", new ActionMessage(StringShop.EMPTY_STRING));
		}

//		Check if the URL given seems correct
		final Matcher theMatcher = CHECK_URL.matcher(weatherUrl);
		if (!theMatcher.matches()) {
			errors.add("incorrectURL", new ActionMessage(StringShop.EMPTY_STRING));
		}
		
		if(errors.isEmpty()) {
		
			try {
				new CrawlImpl(source, WEATHER_TYPE, weatherUrl, 0);
				Factories.SOURCE.createNewSource(source+SOURCE_WEATHER, 0, 0, DicoAPI.LOC+DICO_PREFIX+dicoKey);
				Factories.SOURCE.createNewSource(source+SOURCE_TEMP, 0, 0, DicoAPI.LOC+DICO_PREFIX+dicoKey);

				for(LanguageData aLanguageData : DicoAPI.theLanguages){
					final Map<String, Object> theParams = new HashMap<String, Object>();
					theParams.put("key", DICO_PREFIX+dicoKey);
					theParams.put("language", aLanguageData.getIso_code());
					theParams.put("text", city);
					theParams.put("page", StringShop.EMPTY_STRING);
					DicoAPI.create(theParams);
				}
				final String mailBody = "A new weather was just created !/nDico Key generated: "+DICO_PREFIX+dicoKey+"./nSource value: "+source+SOURCE_WEATHER;
				MailTools.sendFromAdmin(MAIL_RECIPIENT, "ADMIN: New Weather Source", mailBody);
			} catch (SQLException e1) {
				errors.add("errorSQL", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (InvalidParameterException e) {
				errors.add("errorSQL", new ActionMessage(StringShop.EMPTY_STRING));
			} catch (APIException e) {
				errors.add("errorSQL", new ActionMessage(StringShop.EMPTY_STRING));
			}
		}
		
		myForm.setContinent(myForm.getContinent());
		myForm.setCountry(myForm.getCountry());
		myForm.setUrl(weatherUrl);
		myForm.setNewCity(city);
		myForm.setCity(city);
		
		if(!errors.isEmpty()) {
			myForm.setErrors(errors);
			return displayCities(mapping, myForm, request, response);
		}
		
		errors.add("success", new ActionMessage(StringShop.EMPTY_STRING));
		myForm.setErrors(errors);
		myForm.setDicoCreated(DICO_PREFIX+dicoKey);
		return load(mapping, myForm, request, response);
	}

}
