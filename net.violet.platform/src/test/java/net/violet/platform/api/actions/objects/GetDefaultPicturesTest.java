package net.violet.platform.api.actions.objects;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.callers.ApplicationAPICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ApplicationCredentialsMock;
import net.violet.platform.datamodel.mock.ApplicationMock;
import net.violet.platform.datamodel.mock.ConfigFilesMock;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectType;

import org.junit.Assert;
import org.junit.Test;

public class GetDefaultPicturesTest extends AbstractTestBase {

	@Test
	public void getPicturesTest() throws APIException {

		final Files V2_1 = Factories.FILES.createFile("v2/image/1.jpg", MimeType.MIME_TYPES.JPEG);
		final Files V2_2 = Factories.FILES.createFile("v2/image/2.jpg", MimeType.MIME_TYPES.JPEG);
		new ConfigFilesMock(V2_1, HARDWARE.V2.getId().toString());
		new ConfigFilesMock(V2_2, HARDWARE.V2.getId().toString());

		final Action theAction = new GetDefaultPictures();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, ObjectType.NABAZTAG_V2.getTypeName());

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);

		Assert.assertTrue(theResult instanceof List);

		final List<String> theResultAsList = (List) theResult;
		Assert.assertEquals(2, theResultAsList.size());
		Assert.assertTrue(theResultAsList.contains(FilesData.getData(V2_1).getApiId(caller)));
		Assert.assertTrue(theResultAsList.contains(FilesData.getData(V2_2).getApiId(caller)));
	}

	@Test(expected = InvalidParameterException.class)
	public void getPicturesWithWrongTypeTest() throws APIException {
		final Action theAction = new GetDefaultPictures();
		final Application theApplication = new ApplicationMock(42, "My first application", getPrivateUser(), new Date());
		final ApplicationCredentials cred = new ApplicationCredentialsMock("6992873d28d86925325dc52d15d6feec30bb2da5", "59e6060a53ab1be5", theApplication);
		final APICaller caller = new ApplicationAPICaller(ApplicationCredentialsData.getData(cred));

		final Map<String, Object> theParams = new HashMap<String, Object>();
		theParams.put(ActionParam.MAIN_PARAM_KEY, "a wonderful but unexisting type");

		final ActionParam theActionParam = new ActionParam(caller, theParams);

		theAction.processRequest(theActionParam);
	}

}
