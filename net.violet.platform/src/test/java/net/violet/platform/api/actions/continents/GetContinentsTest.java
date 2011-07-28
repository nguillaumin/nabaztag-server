package net.violet.platform.api.actions.continents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.mock.ContinentMock;

import org.junit.Assert;
import org.junit.Test;

public class GetContinentsTest extends AbstractTestBase {

	static long i = 0L;

	private List<ContinentMock> creerBDMock() {
		final List<ContinentMock> resultList = new ArrayList<ContinentMock>();
		resultList.add(new ContinentMock(++GetContinentsTest.i, "Asia"));
		resultList.add(new ContinentMock(++GetContinentsTest.i, "Europe"));
		resultList.add(new ContinentMock(++GetContinentsTest.i, "Africa"));
		resultList.add(new ContinentMock(++GetContinentsTest.i, "Oceania"));
		resultList.add(new ContinentMock(++GetContinentsTest.i, "South America"));
		resultList.add(new ContinentMock(++GetContinentsTest.i, "North America"));
		resultList.add(new ContinentMock(++GetContinentsTest.i, "Antartica"));

		return resultList;
	}

	
	@Test
	public void testGetCodes() throws APIException {
		final List<ContinentMock> mocksList = creerBDMock();
		final Action theAction = new GetContinents();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();
		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final List<String> theResult = (List<String>) theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertEquals(GetContinentsTest.i, theResult.size());
		final Iterator<ContinentMock> i1 = mocksList.iterator();
		while (i1.hasNext()) {
			Assert.assertTrue(theResult.contains(i1.next().getName()));
		}
	}
}
