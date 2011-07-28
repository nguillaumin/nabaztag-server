package net.violet.platform.schedulers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class SchedulingAtomTest {

	@Test
	public void simpleIsValidTest() {

		Assert.assertFalse(SchedulingAtom.isValid(Collections.emptyList(), false, false));

		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 12);
		Assert.assertFalse(SchedulingAtom.isValid(atom, false, false));

		atom.clear();
		atom.put(SchedulingAtom.MINUTE, 12);
		Assert.assertFalse(SchedulingAtom.isValid(atom, false, false));

		atom.clear();
		atom.put(SchedulingAtom.HOUR, 25);
		atom.put(SchedulingAtom.MINUTE, 25);
		Assert.assertFalse(SchedulingAtom.isValid(atom, false, false));

		atom.clear();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 67);
		Assert.assertFalse(SchedulingAtom.isValid(atom, false, false));

		atom.clear();
		atom.put(SchedulingAtom.HOUR, 12);
		atom.put(SchedulingAtom.MINUTE, 35);
		Assert.assertTrue(SchedulingAtom.isValid(atom, false, false));
		final SchedulingAtom theAtom = new SchedulingAtom(atom);
		Assert.assertEquals(12, theAtom.getHour());
		Assert.assertEquals(35, theAtom.getMinute());
		Assert.assertNull(theAtom.getDuration());
		Assert.assertNull(theAtom.getMedia());
	}

	@Test
	public void withDurationTest() {
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 21);
		atom.put(SchedulingAtom.MINUTE, 25);

		Assert.assertFalse(SchedulingAtom.isValid(atom, true, false));

		atom.put(SchedulingAtom.DURATION, "132");
		Assert.assertFalse(SchedulingAtom.isValid(atom, true, false));

		atom.put(SchedulingAtom.DURATION, -5);
		Assert.assertFalse(SchedulingAtom.isValid(atom, true, false));

		atom.put(SchedulingAtom.DURATION, 15);
		Assert.assertTrue(SchedulingAtom.isValid(atom, true, false));

		final SchedulingAtom theAtom = new SchedulingAtom(atom);
		Assert.assertEquals(21, theAtom.getHour());
		Assert.assertEquals(25, theAtom.getMinute());
		Assert.assertEquals(Integer.valueOf(15), theAtom.getDuration());
		Assert.assertNull(theAtom.getMedia());

	}

	@Test
	public void withMediaTest() {
		final Map<String, Object> atom = new HashMap<String, Object>();
		atom.put(SchedulingAtom.HOUR, 21);
		atom.put(SchedulingAtom.MINUTE, 25);

		Assert.assertFalse(SchedulingAtom.isValid(atom, false, true));

		atom.put(SchedulingAtom.MEDIA, "myMedia");
		Assert.assertTrue(SchedulingAtom.isValid(atom, false, true));

		final SchedulingAtom theAtom = new SchedulingAtom(atom);
		Assert.assertEquals(21, theAtom.getHour());
		Assert.assertEquals(25, theAtom.getMinute());
		Assert.assertEquals("myMedia", theAtom.getMedia());
		Assert.assertNull(theAtom.getDuration());

	}

	@Test
	public void isAfterTest() {
		final SchedulingAtom atom1 = new SchedulingAtom(5, 30, null, null);
		final SchedulingAtom atom2 = new SchedulingAtom(8, 20, null, null);
		final SchedulingAtom atom3 = new SchedulingAtom(10, 45, null, null);
		final SchedulingAtom atom4 = new SchedulingAtom(10, 45, null, null);

		Assert.assertTrue(atom3.isAfter(atom2));
		Assert.assertTrue(atom3.isAfter(atom1));
		Assert.assertTrue(atom2.isAfter(atom1));
		Assert.assertFalse(atom2.isAfter(atom3));
		Assert.assertFalse(atom1.isAfter(atom3));
		Assert.assertFalse(atom4.isAfter(atom3));
	}

	@Test
	public void formattedTimeTest() {
		final String time = "18:35:00";
		final SchedulingAtom atom = new SchedulingAtom(18, 35, null, null);
		Assert.assertEquals(time, atom.getFormattedTime());
		final SchedulingAtom atom2 = new SchedulingAtom(time, null, null);
		Assert.assertEquals(18, atom2.getHour());
		Assert.assertEquals(35, atom2.getMinute());
		Assert.assertEquals(time, atom2.getFormattedTime());
	}
}
