package net.violet.platform.api.actions.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.actions.libraries.GetForSignature;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.mock.FilesMock;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.UserMock;

import org.junit.Assert;
import org.junit.Test;

public class GetForSignatureTest extends AbstractTestBase {

	@Test
	public void testItemInformationMap() throws APIException {

		final User theOwner = new UserMock(42, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final User theOwner43 = new UserMock(43, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getSiteFrLang(), net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, net.violet.common.StringShop.EMPTY_STRING, getParisTimezone());
		final Files theFile = new FilesMock("GetForSignatureTest/1.mpeg", MimeType.MIME_TYPES.A_MPEG);

		new MusicMock(0, "signature ", theFile, theOwner43, MusicStyle.CATEGORIE_MP3_PERSO, 0, Music.TYPE_MP3_SIGNATURE, 0);
		new MusicMock(0, "signature ", theFile, theOwner43, MusicStyle.CATEGORIE_MP3_PERSO, 0, Music.TYPE_MP3_USER_LIBRARY, 0);
		new MusicMock(0, "signature ", theFile, theOwner, MusicStyle.CATEGORIE_MP3_PERSO, 0, Music.TYPE_MP3_USER_LIBRARY, 0);

		final Action theAction = new GetForSignature();
		final ActionParam theActionParam = new ActionParam(getPublicApplicationAPICaller(), new HashMap<String, Object>());
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List theResultAsList = (List) theResult;
		Assert.assertEquals(1, theResultAsList.size());
		Assert.assertTrue(theResultAsList.get(0) instanceof Map);
		Assert.assertEquals("audio", ((Map) theResultAsList.get(0)).get("mime_type"));
	}
}
