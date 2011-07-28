package net.violet.platform.api.actions.dico;

import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.MissingParameterException;
import net.violet.platform.api.maps.DicoInformationMap;
import net.violet.platform.datamodel.Lang;

import org.junit.Assert;
import org.junit.Test;

public class CreateTest extends AbstractTestBase {

	@Test
	public void createDico() throws APIException {

		final Lang frLang = getSiteFrLang();

		final Action theAction = new Create();
		final APICaller theCaller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(Create.LANGUAGE_PARAM, frLang.getIsoCode());
		theParams.put(Create.KEY_PARAM, "scr_mock/test");
		theParams.put(Create.TEXT_PARAM, "Tesct Mock de la Classe applications.Create");
		theParams.put(Create.PAGE_PARAM, "Tests");

		final ActionParam theActionParam = new ActionParam(theCaller, theParams);

		final Object theDicoLine = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theDicoLine);
		Assert.assertTrue(theDicoLine instanceof DicoInformationMap);
		final DicoInformationMap theDicoInformation = (DicoInformationMap) theDicoLine;

		Assert.assertEquals(frLang.getIsoCode(), theDicoInformation.get(DicoInformationMap.LANGUAGE));
		Assert.assertEquals("scr_mock/test", theDicoInformation.get(DicoInformationMap.KEY));
		Assert.assertEquals("Tesct Mock de la Classe applications.Create", theDicoInformation.get(DicoInformationMap.TEXT));
		Assert.assertEquals("Tests", theDicoInformation.get(DicoInformationMap.PAGE));
	}

	@Test(expected = InvalidParameterException.class)
	public void badLanguage() throws APIException {

		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put("language", "JPFR");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

	}

	@Test(expected = MissingParameterException.class)
	public void testMissingParameter() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Action theAction = new Create();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(Create.LANGUAGE_PARAM, frLang.getIsoCode());

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
	}

}
