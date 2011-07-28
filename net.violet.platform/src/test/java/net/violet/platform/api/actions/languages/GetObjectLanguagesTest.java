package net.violet.platform.api.actions.languages;

import java.util.HashMap;
import java.util.List;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.APIException;

import org.junit.Assert;
import org.junit.Test;

public class GetObjectLanguagesTest extends AbstractTestBase {

	@Test
	public void testGetSiteLanguages() throws APIException {

		final Action theAction = new GetObjectLanguages();
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), new HashMap<String, Object>());
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List theList = (List) theResult;
		Assert.assertEquals(theList.size(), 7);
	}
}
