package net.violet.platform.api.actions.applications;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.ApplicationProfile;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.ApplicationProfileMock;
import net.violet.platform.datamodel.mock.ApplicationTagMock;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.UserMock;
import net.violet.platform.datamodel.mock.VObjectMock;
import net.violet.platform.dataobjects.ApplicationCategoryData;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.ObjectType;

import org.junit.Assert;
import org.junit.Test;

public class GetProfileTest extends MockTestBase {

	@Test
	public void testExistingRecord() throws APIException {

		final ApplicationTag t1 = new ApplicationTagMock(1, "tagUn", getFrLang(), 10);
		final ApplicationTag t2 = new ApplicationTagMock(2, "tagDeux", getFrLang(), 5);
		final Date now = new Date();
		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		new VObjectMock(61009, "F00004000001", "test42", theOwner, HARDWARE.V2, getParisTimezone(), getFrLang(), now.getTime());
		final Files theFiles = new FilesMock("/1", MimeType.MIME_TYPES.A_MPEG);
		final Action theAction = new GetProfile();
		final Application theApplication = getMyFirstApplication();
		final ApplicationCategory applicationCategory = theApplication.getCategory();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final ApplicationProfile theApplicationProfile = new ApplicationProfileMock(theApplication.getId(), "my profile", "desc profile", true, now, theFiles.getId(), theFiles.getId(), theFiles.getId(), theFiles.getId(), theFiles.getId(), null, "http://www.violet.net");

		((ApplicationMock) theApplication).setProfile(theApplicationProfile);
		theApplication.getTags().add(t1);
		theApplication.getTags().add(t2);

		theApplication.addHardware(HARDWARE.V2);

		final ApplicationData appliId = ApplicationData.getData(theApplication);
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		theParams.put(ActionParam.MAIN_PARAM_KEY, appliId.getApiId(caller));

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof Map);

		final Map theResultAsMap = (Map) theResult;

		Assert.assertEquals("my profile", theResultAsMap.get("title"));
		Assert.assertEquals("desc profile", theResultAsMap.get("description"));
		Assert.assertEquals("http://www.violet.net", theResultAsMap.get("url"));
		final List theSupportedHardware = (List) theResultAsMap.get("optimized_for");
		Assert.assertEquals(theSupportedHardware.size(), 1);
		Assert.assertEquals(theSupportedHardware.get(0), ObjectType.NABAZTAG_V2.getTypeName());

		final List theTags = (List) theResultAsMap.get("tags");
		Assert.assertEquals(theTags.size(), 2);
		Assert.assertEquals(theTags.get(0), "tagUn");
		Assert.assertTrue((Boolean) theResultAsMap.get("open_source"));

		Assert.assertEquals(ApplicationCategoryData.getData(applicationCategory).getName(), theResultAsMap.get("category"));
	}

	@Test(expected = NoSuchApplicationException.class)
	public void testInvalidAPIId() throws APIException {
		final Action theAction = new GetProfile();
		final Application theApplication = getMyFirstApplication();
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "ee51O935b3af9zozo");
		final ActionParam theActionParam = new ActionParam(new ApplicationAPICaller(ApplicationCredentialsData.getData(cred)), theParams);
		theAction.processRequest(theActionParam);
	}

}
