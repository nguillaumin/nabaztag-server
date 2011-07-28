package net.violet.platform.api.maps;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.util.CCalendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author christophe - Violet
 */
public class PojoMapTest extends MockTestBase {

	private static final String TEST_STRING = "i am a string";
	private static final Date TEST_DATE = CCalendar.parseISODate("2002-02-02T22:22:22 UTC").getTime();

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getString(java.lang.String)}
	 * .
	 * 
	 * @throws InvalidParameterException
	 */
	@Test
	public void testGetStringString() {

		final PojoMap params = new PojoMap(getPojoParams());

		Assert.assertEquals(params.getString("string_here"), PojoMapTest.TEST_STRING);
		Assert.assertNull(params.getString("string_there"));
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#get(java.lang.String, boolean)}
	 * .
	 * 
	 * @throws InvalidParameterException
	 */
	@Test
	public void testGetStringStringBoolean() throws InvalidParameterException {

		final PojoMap params = new PojoMap(getPojoParams());

		Assert.assertEquals(params.getString("string_here", true), PojoMapTest.TEST_STRING);

		try {
			params.getString("string_there", true);
		} catch (final InvalidParameterException e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#get(java.lang.String, java.lang.String)}
	 * .
	 * 
	 * @throws InvalidParameterException
	 */
	@Test
	public void testGetStringStringString() {
		final PojoMap params = new PojoMap(getPojoParams());

		Assert.assertEquals(params.getString("string_here", "default"), PojoMapTest.TEST_STRING);

		Assert.assertEquals(params.getString("string_there", "default"), "default");
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getInteger(java.lang.String)}
	 * .
	 * 
	 * @throws InvalidParameterException
	 */
	@Test
	public void testGetInteger() throws InvalidParameterException {
		final PojoMap params = new PojoMap(getPojoParams());

		Assert.assertNull(params.getInteger("i_am_not_there"));

		Assert.assertEquals(params.getInteger("int_value"), new Integer(1));

		try {
			params.getInteger("too_big");

		} catch (final InvalidParameterException e) {
			Assert.assertTrue(true);
		}

		try {
			params.getInteger("string_here");

		} catch (final InvalidParameterException e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getInt(java.lang.String, boolean)}
	 * .
	 */
	@Test
	public void testGetIntStringBoolean() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			Assert.assertEquals(1, params.getInt("int_value", true).intValue());

		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getInt("no_aint_there", true);

		} catch (final InvalidParameterException e) { // Missing Parameter
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getInt(java.lang.String, int)}
	 * .
	 */
	@Test
	public void testGetIntStringInt() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			Assert.assertEquals(params.getInt("int_value", -2), // default value is not
			// taken into
			// account
			1);

		} catch (final InvalidParameterException e) {
			Assert.assertTrue(true);
		}

		try {
			Assert.assertEquals(params.getInt("no_aint_there", -2), // default value IS
			// taken into
			// account !
			-2);

		} catch (final InvalidParameterException e) { // Missing Parameter
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getInt("string_here", -2);

		} catch (final InvalidParameterException e) {
			Assert.assertTrue(true);
		}

	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getDate(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetDateString() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			Assert.assertEquals(params.getDate("date"), PojoMapTest.TEST_DATE);
			Assert.assertNull(params.getDate("no_date_here"));

		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getDate("string_here");

		} catch (final InvalidParameterException e) {
			Assert.assertTrue(true);
		}

	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getDate(java.lang.String, boolean)}
	 * .
	 */
	@Test
	public void testGetDateStringBoolean() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			Assert.assertEquals(params.getDate("date", true), PojoMapTest.TEST_DATE);
		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getDate("no_date_here", true);

		} catch (final InvalidParameterException e) { // missing
			Assert.assertTrue(true);
		}

		try {
			params.getDate("string_here");

		} catch (final InvalidParameterException e) { // not a date
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getDate(java.lang.String, java.util.Date)}
	 * .
	 */
	@Test
	public void testGetDateStringDate() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			Assert.assertEquals(params.getDate("date", null), PojoMapTest.TEST_DATE);
		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			Assert.assertEquals(params.getDate("no_date_here", PojoMapTest.TEST_DATE), // default
			PojoMapTest.// to
			// TEST_DATE
			TEST_DATE);

		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getDate("string_here", PojoMapTest.TEST_DATE);

		} catch (final InvalidParameterException e) { // not a date
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getBoolean(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetBooleanString() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			Assert.assertEquals(params.getBoolean("bool_et_bil"), new Boolean(true));
			Assert.assertNull(params.getBoolean("no_bool_here"));

		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getBoolean("string_here");

		} catch (final InvalidParameterException e) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getBoolean(java.lang.String, java.lang.Boolean)}
	 * .
	 */
	@Test
	public void testGetBooleanStringBoolean() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			Assert.assertEquals(params.getBoolean("bool_et_bil", true), // required
			new Boolean(true));
		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getBoolean("boulet_bil", true);

		} catch (final InvalidParameterException e) { // missing
			Assert.assertTrue(true);
		}

		try {
			params.getBoolean("string_here");

		} catch (final InvalidParameterException e) { // not a boolean
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getList(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetListString() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			final List<String> actors = params.getList("actors");
			Assert.assertTrue(actors.contains("laurel"));

			Assert.assertNull(params.getList("beetles")); // no beetles here

		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getList("string_here");

		} catch (final InvalidParameterException e) { // not a list
			Assert.assertTrue(true);
		}

	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getList(java.lang.String, boolean)}
	 * .
	 */
	@Test
	public void testGetListStringBoolean() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			final List<String> actors = params.getList("actors", true); // mandatory
			Assert.assertTrue(actors.contains("laurel"));

		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getList("beetles", true);

		} catch (final InvalidParameterException e) { // missing
			Assert.assertTrue(true);
		}

		try {
			params.getList("string_here", true);

		} catch (final InvalidParameterException e) { // not a boolean
			Assert.assertTrue(true);
		}

	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getList(java.lang.String, java.util.List)}
	 * .
	 */
	@Test
	public void testGetListStringListOfT() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			final List<String> actors = params.getList("actors", Collections.<String> emptyList()); // mandatory
			Assert.assertTrue(actors.contains("laurel"));

		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			final List<String> beetles = params.getList("beetles", Collections.<String> emptyList()); // return
			// the
			// empty
			// list
			Assert.assertTrue(!beetles.contains("paul"));

		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getList("string_here", Collections.<String> emptyList());

		} catch (final InvalidParameterException e) { // not a list
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getPojoMap(java.lang.String, boolean)}
	 * .
	 */
	@Test
	public void testGetPojoMapStringBoolean() {
		final PojoMap params = new PojoMap(getPojoParams());

		try {
			final Map<String, Object> actor = params.getMap("mainactor", true);
			Assert.assertTrue(actor.containsKey(ActionParam.MAIN_PARAM_KEY)); // test the presence of 'id' param

			final Map<String, Object> jiminy = params.getMap("beetle", false);
			Assert.assertEquals(null, jiminy); // no beetle named jiminy here > empty map

		} catch (final InvalidParameterException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

		try {
			params.getMap("missing", true);

		} catch (final InvalidParameterException e) { // required
			Assert.assertTrue(true);
		}

		try {
			params.getMap("string_here", true);

		} catch (final InvalidParameterException e) { // not a map
			Assert.assertTrue(true);
		}
	}

	/**
	 * Test method for
	 * {@link net.violet.platform.api.actions.PojoMap#getPojoMap(java.lang.String, java.util.Map)}
	 * .
	 * 
	 * @throws InvalidParameterException
	 */
	@Test
	public void testGetPojoMapStringMapOfStringObject() throws InvalidParameterException {
		final PojoMap params = new PojoMap(getPojoParams());

		PojoMap actor;

		actor = params.getPojoMap("mainactor", Collections.<String, Object> emptyMap());
		Assert.assertTrue(actor.containsKey(ActionParam.MAIN_PARAM_KEY)); // test the presence of 'id' param

		final PojoMap jiminy = params.getPojoMap("beetle", Collections.<String, Object> emptyMap());
		Assert.assertTrue(jiminy.isEmpty()); // no beetle named jiminy here > empty map

		final PojoMap missing_actor = params.getPojoMap("missing", actor); // get the default value ( the initialized actor )
		Assert.assertTrue(missing_actor.containsKey(ActionParam.MAIN_PARAM_KEY)); // test the presence of 'id' param

		try {
			params.getPojoMap("string_here", Collections.<String, Object> emptyMap());

		} catch (final InvalidParameterException e) { // not a map
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testgetPropertyFromJSONPath() throws InvalidParameterException {

		final PojoMap testMap = new PojoMap(getPojoParams());

		final Object date = testMap.getDate("date");
		Assert.assertTrue(date instanceof Date);

		final Object annotation = testMap.getPojoMap("sequence[0].annotation");
		Assert.assertTrue(annotation instanceof Map);

		final Map annotationMap = testMap.getPojoMap("sequence[0].annotation");
		Assert.assertTrue(annotationMap.containsKey("state"));

		final List disableList = testMap.getList("sequence[1].directive.disable");
		Assert.assertTrue(disableList.size() == 1);

		final String disableFirstEvent = testMap.getString("sequence[1].directive.disable[0]");
		Assert.assertEquals(disableFirstEvent, "earMove");

		final String disableSecondEvent = testMap.getString("sequence[1].directive.disable[1]");
		Assert.assertNull(disableSecondEvent);

	}

	@Test
	public void testputPropertyFromJSONPath() throws InvalidParameterException {

		final PojoMap testMap = new PojoMap(6);

		testMap.createProperty("john.birthday", CCalendar.parseTimestamp("1968-10-07 00:00:00"));
		testMap.createProperty("john.firstname", "john");
		testMap.createProperty("john.name", "doe");

		testMap.createProperty("john.surnames", "john john");
		testMap.createProperty("john.surnames", "john the fool");
		testMap.createProperty("john.surnames", "mickey mouse");

		final Object date = testMap.getDate("john.birthday");
		Assert.assertTrue(date instanceof Date);

		final List surnames = testMap.getList("john.surnames");
		Assert.assertTrue(surnames.size() == 3);

	}

}
