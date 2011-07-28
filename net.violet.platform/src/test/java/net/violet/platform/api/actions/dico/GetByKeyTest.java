package net.violet.platform.api.actions.dico;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.MissingParameterException;
import net.violet.platform.api.maps.DicoInformationMap;
import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.mock.DicoMock;
import net.violet.platform.dataobjects.DicoData;

import org.junit.Assert;
import org.junit.Test;

public class GetByKeyTest extends AbstractTestBase {

	@Test
	public void GetByKey() throws APIException {

		final Lang frLang = getSiteFrLang();
		final Lang enLang = getSiteFrLang();
		final Dico theDico = new DicoMock(1, "Loc_mock/test", frLang, "text of Dico Mock in en", "Tests");
		new DicoMock(2, "Loc_mock/test", enLang, "text de Dico Mock en fr", "Tests");

		final DicoData theDicoData = DicoData.getData(theDico);

		final Action theAction = new GetByKey();
		final APICaller theCaller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final String theDicoKey = theDicoData.getKey();
		theParams.put(ActionParam.MAIN_PARAM_KEY, theDicoKey);

		final ActionParam theActionParam = new ActionParam(theCaller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof List);
		final List theDicoLines = (List) theResult;
		Assert.assertEquals(2, theDicoLines.size());
		for (final Object inDicoLine : theDicoLines) {
			Assert.assertTrue(inDicoLine instanceof DicoInformationMap);
			final DicoInformationMap theDicoText = (DicoInformationMap) inDicoLine;
			final String inISO = (String) theDicoText.get(DicoInformationMap.LANGUAGE);
			Assert.assertTrue(frLang.getIsoCode().equals(inISO) || enLang.getIsoCode().equals(inISO));
			final String inId = (String) theDicoText.get(DicoInformationMap.ID);
			Assert.assertTrue(inId.contains("D"));
			final String inText = (String) theDicoText.get(DicoInformationMap.TEXT);
			Assert.assertTrue(inText.startsWith("text "));
		}
	}

	@Test
	public void badKey() throws APIException {

		final Action theAction = new GetByKey();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "JPFR");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(((List) theResult).size() == 0);
	}

	@Test(expected = MissingParameterException.class)
	public void testMissingParameter() throws APIException {

		final Action theAction = new Update();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
	}

}
