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
import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.mock.DicoMock;
import net.violet.platform.dataobjects.DicoData;

import org.junit.Assert;
import org.junit.Test;

public class UpdateTest extends AbstractTestBase {

	@Test
	public void updateDico() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Dico theDico = new DicoMock(1, "Loc_mock/test", getSiteEnLang(), "text de DicoTools.dico Mock", "Tests");

		final DicoData theDicoData = DicoData.getData(theDico);

		final Action theAction = new Update();
		final APICaller theCaller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String APIId = theDicoData.getApiId(theCaller);
		theParams.put(ActionParam.MAIN_PARAM_KEY, APIId);
		theParams.put(Update.LANGUAGE_PARAM, frLang.getIsoCode());
		theParams.put(Update.KEY_PARAM, "scr_mock/updated");
		theParams.put(Update.TEXT_PARAM, "Tesct Mock de la Classe applications.Update");
		theParams.put(Update.PAGE_PARAM, "TestsUpdated");

		final ActionParam theActionParam = new ActionParam(theCaller, theParams);

		final Object theDicoLine = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theDicoLine);
		Assert.assertTrue(theDicoLine instanceof DicoInformationMap);
		final DicoInformationMap theDicoInformation = (DicoInformationMap) theDicoLine;

		Assert.assertEquals(frLang.getIsoCode(), theDicoInformation.get(DicoInformationMap.LANGUAGE));
		Assert.assertEquals("scr_mock/updated", theDicoInformation.get(DicoInformationMap.KEY));
		Assert.assertEquals("Tesct Mock de la Classe applications.Update", theDicoInformation.get(DicoInformationMap.TEXT));
		Assert.assertEquals("TestsUpdated", theDicoInformation.get(DicoInformationMap.PAGE));
	}

	@Test(expected = InvalidParameterException.class)
	public void badLanguage() throws APIException {

		final Action theAction = new Update();
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
		final Action theAction = new Update();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(Update.LANGUAGE_PARAM, frLang.getIsoCode());

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
	}

}
