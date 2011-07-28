package net.violet.platform.api.actions.voices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.TtsVoiceMock;

import org.junit.Assert;
import org.junit.Test;

public class GetByLangTest extends AbstractTestBase {

	@Test
	public void testGetByLangTest() throws APIException {

		new TtsVoiceMock(1L, "voix de julie", "julie22k", getFrLang(), "Julie", true, false);
		new TtsVoiceMock(2L, "voix de paul", "paul22k", getUsLang(), "Paul", true, false);
		new TtsVoiceMock(3L, "voix de pierre", "pierre22k", Factories.LANG.findByIsoCode("fr-CA"), "Pierre", true, false);

		final Action theAction = new GetByLang();

		final Map<String, Object> theParams = new HashMap<String, Object>();

		{
			theParams.put(ActionParam.MAIN_PARAM_KEY, "fr-FR");
			final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);

			final Object theResult = theAction.processRequest(theActionParam);

			Assert.assertTrue(theResult instanceof List);

			final List theResultAsList = (List) theResult;
			Assert.assertEquals(1, theResultAsList.size());

			final Object theObject = theResultAsList.get(0);

			Assert.assertTrue(theObject instanceof Map);

			final Map theMap = (Map) theObject;

			Assert.assertEquals("fr-FR", theMap.get("language"));
			Assert.assertEquals("Julie", theMap.get("id"));
			Assert.assertEquals("voix de julie", theMap.get("title"));
		}

		{
			theParams.put(ActionParam.MAIN_PARAM_KEY, "fr");
			final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), theParams);
			final Object theResult = theAction.processRequest(theActionParam);

			Assert.assertTrue(theResult instanceof List);
			Assert.assertEquals(2, ((List) theResult).size());
		}
	}
}
