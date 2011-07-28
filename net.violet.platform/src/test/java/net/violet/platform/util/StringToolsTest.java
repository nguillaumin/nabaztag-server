package net.violet.platform.util;

import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;

public class StringToolsTest extends MockTestBase {

	@Test
	public void testCleanControlChars() {
		final String bad = "\tI'm \r\nbad\r baby\n";

		final String almostgood = StringTools.cleanControlChars(bad, false, false);
		Assert.assertEquals("\tI'm \nbad baby\n", almostgood);

		final String reallygood = StringTools.cleanControlChars(bad, true, true);
		Assert.assertEquals("I'm bad baby", reallygood);
	}

	@Test
	public void testCleanSurroundingQuotes() {
		String bad = "''I'm bad baby'' '";

		String reallygood = StringTools.cleanControlChars(bad, true, true);
		Assert.assertEquals("I'm bad baby", reallygood);

		bad = "''I'm bad ' baby";
		reallygood = StringTools.cleanControlChars(bad, true, true);
		Assert.assertEquals("I'm bad ' baby", reallygood);
	}

	@Test
	public void testFullClean() {
		final String bad = "'\t'I'm\r bad\r baby'' '";

		final String reallygood = StringTools.cleanControlChars(bad, true, true);
		Assert.assertEquals("I'm bad baby", reallygood);
	}

	@Test
	public void getFirstSentenceTest() {
		final int maxLength = 5;
		final String text1 = "abc. def. ghi.";
		final String text2 = "abcdefg. hi.";
		final String text3 = "abcdejdbfkdjbfdkjbdk";
		final String text4 = "av";

		Assert.assertEquals("abc.", StringTools.getFirstSentence(text1, maxLength));
		Assert.assertEquals("abcde", StringTools.getFirstSentence(text2, maxLength));
		Assert.assertEquals("abcde", StringTools.getFirstSentence(text3, maxLength));
		Assert.assertEquals("av", StringTools.getFirstSentence(text4, maxLength));

		Assert.assertEquals("abc.", StringTools.getFirstSentence(text1, -1));
		Assert.assertEquals("abcdefg.", StringTools.getFirstSentence(text2, -1));
		Assert.assertEquals("abcdejdbfkdjbfdkjbdk", StringTools.getFirstSentence(text3, -1));
		Assert.assertEquals("av", StringTools.getFirstSentence(text4, -1));
	}
}
