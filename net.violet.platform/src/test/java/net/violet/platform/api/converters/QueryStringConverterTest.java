package net.violet.platform.api.converters;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author christophe - Violet
 */
public class QueryStringConverterTest {

	private static final String[] mTestValues = { "0.2", "\"0.2\"", "j'ai pas mis les guillemets", "15", "true", };

	private static final String[] mExpectedClassReturned = { "Double", "String", "String", "Integer", "Boolean", };

	/**
	 * Test method for
	 * {@link net.violet.platform.api.converters.QueryStringConverter#convertParamValue(java.lang.String)}
	 * .
	 */
	@Test
	public void testConvertParamValue() {

		for (int i = 0; i < QueryStringConverterTest.mTestValues.length; i++) {
			final Object result = QueryStringConverter.convertParamValue(QueryStringConverterTest.mTestValues[i]);
			System.out.println(QueryStringConverterTest.mTestValues[i] + ">> " + result);
			Assert.assertTrue(result.getClass().getSimpleName().equals(QueryStringConverterTest.mExpectedClassReturned[i]));
		}
	}

}
