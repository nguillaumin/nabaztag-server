package net.violet.platform.api.converters.pojo;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.applets.AppLanguages;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.util.Pair;

import org.junit.Assert;
import org.junit.Test;

public class VioletPojoFixerTest extends MockTestBase {

	// A list of strings with the expected test result
	private static List<Pair<String, Boolean>> getTestSuite() {
		final List<Pair<String, Boolean>> resultList = new ArrayList<Pair<String, Boolean>>();
		resultList.add(new Pair<String, Boolean>("bar", true));
		resultList.add(new Pair<String, Boolean>("Bar", true));
		resultList.add(new Pair<String, Boolean>("_foo", true));
		resultList.add(new Pair<String, Boolean>("foo_bar", true));
		resultList.add(new Pair<String, Boolean>("foo@bar.com", false)); // contains one forbidden symbol
		resultList.add(new Pair<String, Boolean>("foo.bar", true));
		resultList.add(new Pair<String, Boolean>("0.foo", false));
		resultList.add(new Pair<String, Boolean>("foo01", true));
		resultList.add(new Pair<String, Boolean>("a.foo01", true));
		resultList.add(new Pair<String, Boolean>("prototype", false)); // excluded word
		resultList.add(new Pair<String, Boolean>("__proto__", false)); // excluded word
		return resultList;
	}

	@Test
	public void testCheckName() {
		for (final Pair<String, Boolean> test : VioletPojoFixerTest.getTestSuite()) {
			final boolean ysn = VioletPojoFixer.validateAttributeNameFor(test.getFirst(), AppLanguages.JAVASCRIPT);
			Assert.assertEquals(test.getSecond(), ysn);
		}

	}

}
