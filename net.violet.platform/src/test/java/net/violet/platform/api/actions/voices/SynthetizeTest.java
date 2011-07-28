package net.violet.platform.api.actions.voices;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.mock.TtsVoiceMock;
import net.violet.platform.dataobjects.MusicData;

import org.junit.Assert;
import org.junit.Test;

@Deprecated
public class SynthetizeTest extends AbstractTestBase {

	@Test
	// Sur silence il arrive a généré le tts
	public void testSynthetize() throws APIException {

		final Action theAction = new Synthetize();

		final APICaller caller = getPublicApplicationAPICaller();

		final Map<String, Object> theParams = new HashMap<String, Object>();
		Assert.assertTrue(MusicData.countAllItemsOfUser(getPrivateUser(), MusicData.MimeTypes.audio) == 0);
		new TtsVoiceMock(1L, "voix de virgine", "Virginie", getFrLang(), "FR-Anastasie", true, false);
		final Calendar theCalendar = Calendar.getInstance();
		theCalendar.add(Calendar.HOUR, +1);

		theParams.put("text", "Salut");
		theParams.put("voice", "FR-Anastasie");

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);

		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof String);
		Assert.assertTrue(MusicData.countAllItemsOfUser(getPrivateUser(), MusicData.MimeTypes.audio) == 0);
	}
}
