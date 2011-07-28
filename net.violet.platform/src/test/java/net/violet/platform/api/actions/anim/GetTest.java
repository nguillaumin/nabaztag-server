package net.violet.platform.api.actions.anim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractTestBase;
import net.violet.platform.api.actions.Action;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.callers.APICaller;
import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.mock.AnimMock;

import org.junit.Assert;
import org.junit.Test;

public class GetTest extends AbstractTestBase {

	@Test
	public void testGet() throws APIException {

		final List<Anim> theAnim = new ArrayList<Anim>();
		theAnim.add(new AnimMock(2, "anim_Get/2.mpeg"));
		theAnim.add(new AnimMock(3, "anim_Get/3.flash"));
		theAnim.add(new AnimMock(4, "anim_Get/4.mpeg"));
		theAnim.add(new AnimMock(5, "anim_Get/5.mpeg"));

		final Action theAction = new Get();
		final APICaller caller = getPublicApplicationAPICaller();
		final Map<String, Object> theParams = new HashMap<String, Object>();

		final ActionParam theActionParam = new ActionParam(caller, theParams);
		final Object theResult = theAction.processRequest(theActionParam);
		Assert.assertNotNull(theResult);
		Assert.assertTrue(theResult instanceof List);
		final List theResultAsList = (List) theResult;
		Assert.assertTrue(theAnim.size() <= theResultAsList.size());
		for (final Anim inName : theAnim) {
			theResultAsList.contains(inName.getAnim_name());
		}
		for (final Anim inAnim : theAnim) {
			inAnim.delete();
		}
	}

}
