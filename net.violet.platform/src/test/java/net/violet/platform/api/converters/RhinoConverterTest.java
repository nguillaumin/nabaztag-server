package net.violet.platform.api.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.applets.js.helpers.JSDateHelper;
import net.violet.platform.datamodel.MockTestBase;

import org.junit.Assert;
import org.junit.Test;
import org.mozilla.javascript.Scriptable;

public class RhinoConverterTest extends MockTestBase {

	@Test
	public void testMapToJS() throws ParseException {

		final Date sevenOctober1968 = (new SimpleDateFormat("dd/MM/yyyy")).parse("07/10/1968");

		final Map<String, Object> myMap = new HashMap<String, Object>();
		myMap.put("property_string", "string");
		myMap.put("property_long", new Long(0));
		myMap.put("property_double", new Double(1.0));
		myMap.put("property_boolean", Boolean.TRUE);
		myMap.put("property_null", null);
		myMap.put("property_date", sevenOctober1968);

		final List<Object> myList = new ArrayList<Object>();

		myList.add("string"); // 0
		myList.add(new Long(0)); // 1
		myList.add(new Double(1.0)); // 2
		myList.add(Boolean.FALSE); // 3
		myList.add(null); // 4
		myList.add(sevenOctober1968); // 5

		myMap.put("property_list", myList);

		Object theResult = null;

		theResult = RhinoConverter.convertToJS(myMap);

		Assert.assertTrue(theResult instanceof Scriptable);

		final Scriptable theScriptableResult = (Scriptable) theResult;

		Assert.assertEquals("string", theScriptableResult.get("property_string", theScriptableResult));
		Assert.assertEquals(new Long(0), theScriptableResult.get("property_long", theScriptableResult));
		Assert.assertEquals(new Double(1.0), theScriptableResult.get("property_double", theScriptableResult));
		Assert.assertEquals(null, theScriptableResult.get("property_null", theScriptableResult));
		Assert.assertEquals(new Boolean(true), theScriptableResult.get("property_boolean", theScriptableResult));
		Assert.assertEquals(sevenOctober1968, JSDateHelper.convertFromJs((Scriptable) theScriptableResult.get("property_date", theScriptableResult)));

		final Scriptable theScriptableList = (Scriptable) theScriptableResult.get("property_list", theScriptableResult);

		Assert.assertEquals("string", theScriptableList.get(0, theScriptableList));
		Assert.assertEquals(new Long(0), theScriptableList.get(1, theScriptableList));
		Assert.assertEquals(new Double(1.0), theScriptableList.get(2, theScriptableList));
		Assert.assertEquals(new Boolean(false), theScriptableList.get(3, theScriptableList));
		Assert.assertEquals(null, theScriptableList.get(4, theScriptableList));
		Assert.assertEquals(sevenOctober1968, JSDateHelper.convertFromJs((Scriptable) theScriptableList.get(5, theScriptableList)));

	}
}
