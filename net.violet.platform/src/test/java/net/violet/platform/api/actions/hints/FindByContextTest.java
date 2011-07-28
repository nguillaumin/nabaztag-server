package net.violet.platform.api.actions.hints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchContextException;
import net.violet.platform.datamodel.Context;
import net.violet.platform.datamodel.mock.ContextMock;
import net.violet.platform.datamodel.mock.HintMock;

import org.junit.Assert;
import org.junit.Test;

public class FindByContextTest extends AbstractTestBase {

	@Test
	public void testFind() throws APIException {
		final Context theContext = new ContextMock(1, "home");
		theContext.addHint(new HintMock(1, "blabla", "content", "picture", null, null, null));
		theContext.addHint(new HintMock(2, "blablabla", "content2", null, 10, 20, "http://www.nabaztag.com"));
		final Action theAction = new FindByContext();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();

		theParams.put(ActionParam.MAIN_PARAM_KEY, "home");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);

		final List theResultAsList = (List) theResult;
		Assert.assertEquals(2, theResultAsList.size());

		final Object HintOneObject = theResultAsList.get(0);
		final Object HintTwoObject = theResultAsList.get(1);

		Assert.assertTrue(HintOneObject instanceof Map);
		Assert.assertTrue(HintTwoObject instanceof Map);

		final Map HintOne = (Map) HintOneObject;
		final Map HintTwo = (Map) HintTwoObject;

		Assert.assertEquals("blabla", HintOne.get("title"));
		Assert.assertEquals("blablabla", HintTwo.get("title"));
		Assert.assertNull(HintTwo.get("image"));
		Assert.assertEquals(20, HintTwo.get("image_height"));
		Assert.assertEquals(null, HintOne.get("image_height"));
		Assert.assertEquals("http://www.nabaztag.com", HintTwo.get("link"));
	}

	@Test(expected = NoSuchContextException.class)
	public void testNoContext() throws APIException {
		final Action theAction = new FindByContext();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9");
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

	@Test(expected = InvalidParameterException.class)
	public void testinvalidParam() throws APIException {
		final Action theAction = new FindByContext();
		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

}
