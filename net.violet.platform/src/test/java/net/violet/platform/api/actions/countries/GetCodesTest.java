package net.violet.platform.api.actions.countries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.CountryMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;

import org.junit.Assert;
import org.junit.Test;

public class GetCodesTest extends MockTestBase {

	static long i;

	private List<CountryMock> creerBDMock() {
		final List<CountryMock> resultList = new ArrayList<CountryMock>();
		resultList.add(new CountryMock(GetCodesTest.i++, "SE", "Sweden"));
		resultList.add(new CountryMock(GetCodesTest.i++, "SE", "Suede"));
		resultList.add(new CountryMock(GetCodesTest.i++, "AF", "Afghanistan"));
		resultList.add(new CountryMock(GetCodesTest.i++, "AF", "Afghanistan"));
		resultList.add(new CountryMock(GetCodesTest.i++, "AL", "Albanie"));
		resultList.add(new CountryMock(GetCodesTest.i++, "DZ", "Algérie"));
		resultList.add(new CountryMock(GetCodesTest.i++, "DZ", "Algeria"));
		resultList.add(new CountryMock(GetCodesTest.i++, "DE", "Allemagne"));
		resultList.add(new CountryMock(GetCodesTest.i++, "FR", "France"));
		resultList.add(new CountryMock(GetCodesTest.i++, "FR", "Francja"));
		return resultList;
	}

	@Test
	public void testGetCodes() throws APIException {
		final List<CountryMock> testSuite = creerBDMock();
		Collections.sort(testSuite, new Comparator<CountryMock>() {

			public int compare(CountryMock o1, CountryMock o2) {
				return o1.getCode().compareTo(o2.getCode());
			}
		});
		final Date now = new Date();
		final Action theAction = new GetCodes();
		final Application theApplication = new ApplicationMock(43, "My first application", getPrivateUser(), now);
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, net.violet.common.StringShop.EMPTY_STRING);

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		theAction.processRequest(theActionParam);

		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertTrue(theResult instanceof List);
	}
}
