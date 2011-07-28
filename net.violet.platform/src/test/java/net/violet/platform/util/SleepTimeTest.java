package net.violet.platform.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import net.violet.platform.datamodel.MockTestBase;
import net.violet.platform.datamodel.ObjectSleep.SleepAction;
import net.violet.platform.util.SleepTime.SleepRun;
import net.violet.platform.util.SleepTime.SleepSwitch;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SleepTimeTest extends MockTestBase {

	private SleepSwitch mWakeUpSunday0900;
	private SleepSwitch mSleepSunday2300;
	private SleepSwitch mSleepSunday0000;
	private SleepSwitch mWakeUpMonday0800;
	private SleepSwitch mSleepMonday2200;
	private SleepSwitch mWakeUpFriday0800;
	private SleepSwitch mSleepFriday2200;
	private SleepSwitch mWakeUpSaturday0900;
	private SleepSwitch mSleepSaturday2300;
	private SleepSwitch mSleepTuesday0200;
	private SleepSwitch mSleepSaturday0200;
	private SleepSwitch mSleepMonday0100;
	private SleepSwitch mSleepSunday0100;
	private SleepSwitch mSleepSunday2359;
	private SleepSwitch mWakeUpSaturday0000;
	private SleepSwitch mSleepFriday2359;
	private SleepSwitch mWakeUpMonday0000;
	private SleepSwitch mSleepSunday0600;
	private SleepSwitch mWakeUpMonday0500;
	private SleepSwitch mWakeUpTuesday0500;
	private SleepSwitch mSleepTuesday2200;
	private SleepSwitch mWakeUpSunday0500;
	private SleepSwitch mSleepTuesday0600;
	private SleepSwitch mWakeUpTuesday0800;
	private SleepSwitch mWakeUpSaturday0500;
	private SleepSwitch mSleepMonday0000;

	private SleepRun mSleepSundayFrom0000To0900;
	private SleepRun mWakeSundayFrom0900To2300;
	private SleepRun mSleepSundayFrom2300To2359;
	private SleepRun mSleepMondayFrom0000To0800;
	private SleepRun mWakeMondayFrom0800To2200;
	private SleepRun mSleepMondayFrom2200To2359;
	private SleepRun mSleepFridayFrom2200To2359;
	private SleepRun mSleepSaturdayFrom0000To0900;
	private SleepRun mWakeSaturdayFrom0900To2300;
	private SleepRun mWakeSaturdayFrom0900To2359;
	private SleepRun mSleepSaturdayFrom2300To2359;
	private SleepRun mWakeMondayFrom0000To2359;
	private SleepRun mSleepMondayFrom0000To2359;

	@Before
	public void init() throws ParseException {
		this.mWakeUpSunday0900 = new SleepSwitch(CCalendar.getSQLTime("09:00"), Calendar.SUNDAY, SleepAction.WAKE);
		this.mSleepSunday2300 = new SleepSwitch(CCalendar.getSQLTime("23:00"), Calendar.SUNDAY, SleepAction.SLEEP);
		this.mWakeUpMonday0800 = new SleepSwitch(CCalendar.getSQLTime("08:00"), Calendar.MONDAY, SleepAction.WAKE);
		this.mSleepMonday2200 = new SleepSwitch(CCalendar.getSQLTime("22:00"), Calendar.MONDAY, SleepAction.SLEEP);
		this.mWakeUpFriday0800 = new SleepSwitch(CCalendar.getSQLTime("08:00"), Calendar.FRIDAY, SleepAction.WAKE);
		this.mSleepFriday2200 = new SleepSwitch(CCalendar.getSQLTime("22:00"), Calendar.FRIDAY, SleepAction.SLEEP);
		this.mWakeUpSaturday0900 = new SleepSwitch(CCalendar.getSQLTime("09:00"), Calendar.SATURDAY, SleepAction.WAKE);
		this.mSleepSaturday2300 = new SleepSwitch(CCalendar.getSQLTime("23:00"), Calendar.SATURDAY, SleepAction.SLEEP);
		this.mSleepTuesday0200 = new SleepSwitch(CCalendar.getSQLTime("02:00"), Calendar.TUESDAY, SleepAction.SLEEP);
		this.mSleepSaturday0200 = new SleepSwitch(CCalendar.getSQLTime("02:00"), Calendar.SATURDAY, SleepAction.SLEEP);
		this.mSleepMonday0100 = new SleepSwitch(CCalendar.getSQLTime("01:00"), Calendar.MONDAY, SleepAction.SLEEP);
		this.mSleepSunday0100 = new SleepSwitch(CCalendar.getSQLTime("01:00"), Calendar.SUNDAY, SleepAction.SLEEP);
		this.mSleepSunday2359 = new SleepSwitch(CCalendar.getSQLTime("23:59:59"), Calendar.SUNDAY, SleepAction.SLEEP);
		this.mWakeUpSaturday0000 = new SleepSwitch(CCalendar.getSQLTime("00:00"), Calendar.SATURDAY, SleepAction.WAKE);
		this.mSleepFriday2359 = new SleepSwitch(CCalendar.getSQLTime("23:59:59"), Calendar.FRIDAY, SleepAction.SLEEP);
		this.mWakeUpMonday0000 = new SleepSwitch(CCalendar.getSQLTime("00:00"), Calendar.MONDAY, SleepAction.WAKE);
		this.mSleepSunday0600 = new SleepSwitch(CCalendar.getSQLTime("06:00"), Calendar.SUNDAY, SleepAction.SLEEP);
		this.mWakeUpTuesday0500 = new SleepSwitch(CCalendar.getSQLTime("05:00"), Calendar.TUESDAY, SleepAction.WAKE);
		this.mSleepTuesday2200 = new SleepSwitch(CCalendar.getSQLTime("22:00"), Calendar.TUESDAY, SleepAction.SLEEP);
		this.mWakeUpMonday0500 = new SleepSwitch(CCalendar.getSQLTime("05:00"), Calendar.MONDAY, SleepAction.WAKE);
		this.mWakeUpSunday0500 = new SleepSwitch(CCalendar.getSQLTime("05:00"), Calendar.SUNDAY, SleepAction.WAKE);
		this.mSleepTuesday0600 = new SleepSwitch(CCalendar.getSQLTime("06:00"), Calendar.TUESDAY, SleepAction.SLEEP);
		this.mWakeUpTuesday0800 = new SleepSwitch(CCalendar.getSQLTime("08:00"), Calendar.TUESDAY, SleepAction.WAKE);
		this.mWakeUpSaturday0500 = new SleepSwitch(CCalendar.getSQLTime("05:00"), Calendar.SATURDAY, SleepAction.WAKE);
		this.mSleepSunday0000 = new SleepSwitch(CCalendar.getSQLTime("00:00"), Calendar.SUNDAY, SleepAction.SLEEP);
		this.mSleepMonday0000 = new SleepSwitch(CCalendar.getSQLTime("00:00"), Calendar.MONDAY, SleepAction.SLEEP);

		this.mSleepSundayFrom0000To0900 = new SleepRun(CCalendar.getSQLTime("00:00"), CCalendar.getSQLTime("09:00"), Calendar.SUNDAY, SleepAction.SLEEP);
		this.mWakeSundayFrom0900To2300 = new SleepRun(CCalendar.getSQLTime("09:00"), CCalendar.getSQLTime("23:00"), Calendar.SUNDAY, SleepAction.WAKE);
		this.mSleepSundayFrom2300To2359 = new SleepRun(CCalendar.getSQLTime("23:00"), CCalendar.getSQLTime("23:59:59"), Calendar.SUNDAY, SleepAction.SLEEP);
		this.mSleepMondayFrom0000To0800 = new SleepRun(CCalendar.getSQLTime("00:00"), CCalendar.getSQLTime("08:00"), Calendar.MONDAY, SleepAction.SLEEP);
		this.mWakeMondayFrom0800To2200 = new SleepRun(CCalendar.getSQLTime("08:00"), CCalendar.getSQLTime("22:00"), Calendar.MONDAY, SleepAction.WAKE);
		this.mSleepMondayFrom2200To2359 = new SleepRun(CCalendar.getSQLTime("22:00"), CCalendar.getSQLTime("23:59:59"), Calendar.MONDAY, SleepAction.SLEEP);
		this.mSleepFridayFrom2200To2359 = new SleepRun(CCalendar.getSQLTime("22:00"), CCalendar.getSQLTime("23:59:59"), Calendar.FRIDAY, SleepAction.SLEEP);
		this.mSleepSaturdayFrom0000To0900 = new SleepRun(CCalendar.getSQLTime("00:00"), CCalendar.getSQLTime("09:00"), Calendar.SATURDAY, SleepAction.SLEEP);
		this.mWakeSaturdayFrom0900To2300 = new SleepRun(CCalendar.getSQLTime("09:00"), CCalendar.getSQLTime("23:00"), Calendar.SATURDAY, SleepAction.WAKE);
		this.mWakeSaturdayFrom0900To2359 = new SleepRun(CCalendar.getSQLTime("09:00"), CCalendar.getSQLTime("23:59:59"), Calendar.SATURDAY, SleepAction.WAKE);
		this.mSleepSaturdayFrom2300To2359 = new SleepRun(CCalendar.getSQLTime("23:00"), CCalendar.getSQLTime("23:59:59"), Calendar.SATURDAY, SleepAction.SLEEP);
		this.mWakeMondayFrom0000To2359 = new SleepRun(CCalendar.getSQLTime("00:00"), CCalendar.getSQLTime("23:59:59"), Calendar.MONDAY, SleepAction.WAKE);
		this.mSleepMondayFrom0000To2359 = new SleepRun(CCalendar.getSQLTime("00:00"), CCalendar.getSQLTime("23:59:59"), Calendar.MONDAY, SleepAction.SLEEP);
	}

	@Test
	public void testSleepSwitchesNormal() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("08:00", "22:00", "09:00", "23:00", 14);
		Assert.assertEquals(theSwitches.get(0), this.mWakeUpSunday0900);
		Assert.assertEquals(theSwitches.get(1), this.mSleepSunday2300);
		Assert.assertEquals(theSwitches.get(2), this.mWakeUpMonday0800);
		Assert.assertEquals(theSwitches.get(3), this.mSleepMonday2200);
		Assert.assertEquals(theSwitches.get(10), this.mWakeUpFriday0800);
		Assert.assertEquals(theSwitches.get(11), this.mSleepFriday2200);
		Assert.assertEquals(theSwitches.get(12), this.mWakeUpSaturday0900);
		Assert.assertEquals(theSwitches.get(13), this.mSleepSaturday2300);
	}

	@Test
	public void testSleepSwitchesLateWeekdays() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("08:00", "2:00", "09:00", "23:00", 14);
		Assert.assertEquals(theSwitches.get(0), this.mWakeUpSunday0900);
		Assert.assertEquals(theSwitches.get(1), this.mSleepSunday2300);
		Assert.assertEquals(theSwitches.get(2), this.mWakeUpMonday0800);
		Assert.assertEquals(theSwitches.get(3), this.mSleepTuesday0200);
		Assert.assertEquals(theSwitches.get(10), this.mWakeUpFriday0800);
		Assert.assertEquals(theSwitches.get(11), this.mSleepSaturday0200);
		Assert.assertEquals(theSwitches.get(12), this.mWakeUpSaturday0900);
		Assert.assertEquals(theSwitches.get(13), this.mSleepSaturday2300);
	}

	@Test
	public void testSleepSwitchesLateWeekends() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("08:00", "22:00", "09:00", "01:00", 14);
		Assert.assertEquals(theSwitches.get(0), this.mSleepSunday0100);
		Assert.assertEquals(theSwitches.get(1), this.mWakeUpSunday0900);
		Assert.assertEquals(theSwitches.get(2), this.mSleepMonday0100);
		Assert.assertEquals(theSwitches.get(3), this.mWakeUpMonday0800);
		Assert.assertEquals(theSwitches.get(4), this.mSleepMonday2200);
		Assert.assertEquals(theSwitches.get(11), this.mWakeUpFriday0800);
		Assert.assertEquals(theSwitches.get(12), this.mSleepFriday2200);
		Assert.assertEquals(theSwitches.get(13), this.mWakeUpSaturday0900);
	}

	@Test
	public void testSleepSwitchesLateAlways() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("08:00", "02:00", "09:00", "01:00", 14);
		Assert.assertEquals(theSwitches.get(0), this.mSleepSunday0100);
		Assert.assertEquals(theSwitches.get(1), this.mWakeUpSunday0900);
		Assert.assertEquals(theSwitches.get(2), this.mSleepMonday0100);
		Assert.assertEquals(theSwitches.get(3), this.mWakeUpMonday0800);
		Assert.assertEquals(theSwitches.get(4), this.mSleepTuesday0200);
		Assert.assertEquals(theSwitches.get(11), this.mWakeUpFriday0800);
		Assert.assertEquals(theSwitches.get(12), this.mSleepSaturday0200);
		Assert.assertEquals(theSwitches.get(13), this.mWakeUpSaturday0900);
	}

	@Test
	public void testSleepSwitchesOnlyWeekdays() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("08:00", "22:00", StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 12);
		Assert.assertEquals(theSwitches.get(0), this.mSleepSunday2359);
		Assert.assertEquals(theSwitches.get(1), this.mWakeUpMonday0800);
		Assert.assertEquals(theSwitches.get(2), this.mSleepMonday2200);
		Assert.assertEquals(theSwitches.get(9), this.mWakeUpFriday0800);
		Assert.assertEquals(theSwitches.get(10), this.mSleepFriday2200);
		Assert.assertEquals(theSwitches.get(11), this.mWakeUpSaturday0000);
	}

	@Test
	public void testSleepSwitchesOnlyWeekends() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, "09:00", "23:00", 6);
		Assert.assertEquals(theSwitches.get(0), this.mWakeUpSunday0900);
		Assert.assertEquals(theSwitches.get(1), this.mSleepSunday2300);
		Assert.assertEquals(theSwitches.get(2), this.mWakeUpMonday0000);
		Assert.assertEquals(theSwitches.get(3), this.mSleepFriday2359);
		Assert.assertEquals(theSwitches.get(4), this.mWakeUpSaturday0900);
		Assert.assertEquals(theSwitches.get(5), this.mSleepSaturday2300);
	}

	@Test
	public void testSleepSwitchesLateOnlyWeekdays() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("08:00", "02:00", StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 10);
		Assert.assertEquals(theSwitches.get(0), this.mSleepSunday2359);
		Assert.assertEquals(theSwitches.get(1), this.mWakeUpMonday0800);
		Assert.assertEquals(theSwitches.get(2), this.mSleepTuesday0200);
		Assert.assertEquals(theSwitches.get(9), this.mWakeUpFriday0800);
	}

	@Test
	public void testSleepSwitchesLateOnlyWeekends() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, "09:00", "01:00", 4);
		Assert.assertEquals(theSwitches.get(0), this.mSleepSunday0100);
		Assert.assertEquals(theSwitches.get(1), this.mWakeUpSunday0900);
		Assert.assertEquals(theSwitches.get(2), this.mSleepFriday2359);
		Assert.assertEquals(theSwitches.get(3), this.mWakeUpSaturday0900);
	}

	@Test
	public void testSleepSwitchesLateWeekendsOverlap() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("05:00", "22:00", "09:00", "06:00", 13);
		Assert.assertEquals(theSwitches.get(0), this.mSleepSunday0600);
		Assert.assertEquals(theSwitches.get(1), this.mWakeUpSunday0900);
		Assert.assertEquals(theSwitches.get(2), this.mWakeUpMonday0500);
		Assert.assertEquals(theSwitches.get(3), this.mSleepMonday2200);
		Assert.assertEquals(theSwitches.get(4), this.mWakeUpTuesday0500);
		Assert.assertEquals(theSwitches.get(5), this.mSleepTuesday2200);
		Assert.assertEquals(theSwitches.get(11), this.mSleepFriday2200);
		Assert.assertEquals(theSwitches.get(12), this.mWakeUpSaturday0900);
	}

	@Test
	public void testSleepSwitchesLateWeekdaysOverlap() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("08:00", "06:00", "05:00", "23:00", 13);
		Assert.assertEquals(theSwitches.get(0), this.mWakeUpSunday0500);
		Assert.assertEquals(theSwitches.get(1), this.mSleepSunday2300);
		Assert.assertEquals(theSwitches.get(2), this.mWakeUpMonday0800);
		Assert.assertEquals(theSwitches.get(3), this.mSleepTuesday0600);
		Assert.assertEquals(theSwitches.get(4), this.mWakeUpTuesday0800);
		Assert.assertEquals(theSwitches.get(10), this.mWakeUpFriday0800);
		Assert.assertEquals(theSwitches.get(11), this.mWakeUpSaturday0500);
		Assert.assertEquals(theSwitches.get(12), this.mSleepSaturday2300);
	}

	@Test
	public void testSleepSwitchesSameTimeOnWeek() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("08:00", "08:00", "09:00", "23:00", 6);
		Assert.assertEquals(theSwitches.get(0), this.mWakeUpSunday0900);
		Assert.assertEquals(theSwitches.get(1), this.mSleepSunday2300);
		Assert.assertEquals(theSwitches.get(2), this.mSleepMonday0000);
		Assert.assertEquals(theSwitches.get(3), this.mWakeUpSaturday0900);
		Assert.assertEquals(theSwitches.get(5), this.mSleepSaturday2300);
	}

	@Test
	public void testSleepSwitchesSameTimeOnWeekEnd() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("08:00", "22:00", "07:00", "07:00", 13);
		Assert.assertEquals(theSwitches.get(0), this.mSleepSunday0000);
		Assert.assertEquals(theSwitches.get(1), this.mWakeUpMonday0800);
		Assert.assertEquals(theSwitches.get(2), this.mWakeUpMonday0800);
		Assert.assertEquals(theSwitches.get(3), this.mSleepMonday2200);
		Assert.assertEquals(theSwitches.get(11), this.mSleepFriday2200);
	}

	@Test
	public void testSleepSwitchesSameTime() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches("07:00", "07:00", "08:00", "08:00", 3);
		for (final SleepSwitch theResult : theSwitches) {
			Assert.assertEquals(theResult.getAction().toString(), SleepAction.SLEEP.toString());
		}
	}

	@Test
	public void testSleepSwitchesOnlySameTimeOnWeekEnd() throws ParseException {
		testSleepSwitches(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, "07:00", "07:00", 4);
	}

	@Test
	public void testSleepSwitchesOnlySameTimeOnWeek() throws ParseException {
		testSleepSwitches("08:00", "08:00", StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 4);
	}

	@Test
	public void testSleepSwitchesNoTime() throws ParseException {
		final List<SleepSwitch> theSwitches = testSleepSwitches(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, 0);
		Assert.assertEquals(0, theSwitches.size());
	}

	@Test
	public void test3CommandByDay() throws ParseException { // trois switch par jour
		final List<SleepRun> theRuns = testSleepRuns("08:00", "22:00", "09:00", "23:00");
		Assert.assertEquals(theRuns.get(0), this.mSleepSundayFrom0000To0900);
		Assert.assertEquals(theRuns.get(1), this.mWakeSundayFrom0900To2300);
		Assert.assertEquals(theRuns.get(2), this.mSleepSundayFrom2300To2359);
		Assert.assertEquals(theRuns.get(3), this.mSleepMondayFrom0000To0800);
		Assert.assertEquals(theRuns.get(4), this.mWakeMondayFrom0800To2200);
		Assert.assertEquals(theRuns.get(5), this.mSleepMondayFrom2200To2359);
		Assert.assertEquals(theRuns.get(17), this.mSleepFridayFrom2200To2359);
		Assert.assertEquals(theRuns.get(18), this.mSleepSaturdayFrom0000To0900);
		Assert.assertEquals(theRuns.get(19), this.mWakeSaturdayFrom0900To2300);
		Assert.assertEquals(theRuns.get(20), this.mSleepSaturdayFrom2300To2359);

	}

	@Test
	public void test2CommandByDay() throws ParseException {// deux switch par
		// jour
		final List<SleepRun> theRuns = testSleepRuns("08:00", "22:00", "09:00", "01:00");
		Assert.assertEquals(theRuns.get(0).getDay(), Calendar.SUNDAY);
		Assert.assertEquals(theRuns.get(18).getDay(), Calendar.FRIDAY);
		Assert.assertEquals(theRuns.get(19), this.mSleepSaturdayFrom0000To0900);
		Assert.assertEquals(theRuns.get(20), this.mWakeSaturdayFrom0900To2359);
	}

	@Test
	public void test1CommandByDay() throws ParseException { // un seul switch
		final List<SleepRun> theRuns = testSleepRuns(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, "09:00", "01:00");
		Assert.assertEquals(theRuns.get(2).getDay(), Calendar.SUNDAY);
		Assert.assertEquals(theRuns.get(3), this.mWakeMondayFrom0000To2359);
		Assert.assertEquals(theRuns.get(4).getDay(), Calendar.TUESDAY);
	}

	@Test
	public void testCommandOverride() throws ParseException {
		final List<SleepRun> theRuns = testSleepRuns("08:00", "08:00", "09:00", "23:00");
		Assert.assertEquals(theRuns.get(0), this.mSleepSundayFrom0000To0900);
		Assert.assertEquals(theRuns.get(1), this.mWakeSundayFrom0900To2300);
		Assert.assertEquals(theRuns.get(2), this.mSleepSundayFrom2300To2359);
		Assert.assertEquals(theRuns.get(3), this.mSleepMondayFrom0000To2359);
		Assert.assertEquals(theRuns.get(8), this.mSleepSaturdayFrom0000To0900);
		Assert.assertEquals(theRuns.get(9), this.mWakeSaturdayFrom0900To2300);
		Assert.assertEquals(theRuns.get(10), this.mSleepSaturdayFrom2300To2359);
	}

	@Test
	public void testSleepRunSameTimeOnWeekEndAndWeek() throws ParseException {
		final List<SleepRun> theRuns = testSleepRuns("08:00", "08:00", "07:00", "07:00");
		int i = Calendar.SUNDAY;
		for (final SleepRun theRun : theRuns) {
			Assert.assertEquals(theRun.getAction().toString(), SleepAction.SLEEP.toString());
			Assert.assertEquals(theRun.getDay(), i);
			Assert.assertEquals(theRun.getFrom(), CCalendar.getSQLTime("00:00"));
			Assert.assertEquals(theRun.getTo(), CCalendar.getSQLTime("23:59:59"));
			i++;
		}
		Assert.assertEquals(7, theRuns.size());
	}

	@Test
	public void testSleepRunNotimeOnWeekAndSameTimeOnWeekEnd() throws ParseException {
		final List<SleepRun> theRuns = testSleepRuns(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, "07:00", "07:00");
		Assert.assertEquals(theRuns.get(0).getAction().toString(), SleepAction.SLEEP.toString());
		Assert.assertEquals(theRuns.get(0).getDay(), Calendar.SUNDAY);
		Assert.assertEquals(theRuns.get(1).getAction().toString(), SleepAction.WAKE.toString());
		Assert.assertEquals(theRuns.get(1).getDay(), Calendar.MONDAY);
		Assert.assertEquals(theRuns.get(4).getAction().toString(), SleepAction.WAKE.toString());
		Assert.assertEquals(theRuns.get(4).getDay(), Calendar.THURSDAY);
		Assert.assertEquals(theRuns.get(6).getAction().toString(), SleepAction.SLEEP.toString());
		Assert.assertEquals(theRuns.get(6).getDay(), Calendar.SATURDAY);
		Assert.assertEquals(theRuns.get(6).getFrom(), CCalendar.getSQLTime("00:00"));
		Assert.assertEquals(theRuns.get(6).getTo(), CCalendar.getSQLTime("23:59:59"));
		Assert.assertEquals(7, theRuns.size());
	}

	@Test
	public void testSleepRunNotimeOnWeekEndAndSameTimeOnWeek() throws ParseException {
		final List<SleepRun> theRuns = testSleepRuns("08:00", "08:00", StringShop.EMPTY_STRING, StringShop.EMPTY_STRING);
		Assert.assertEquals(theRuns.get(0).getAction().toString(), SleepAction.WAKE.toString());
		Assert.assertEquals(theRuns.get(0).getDay(), Calendar.SUNDAY);
		Assert.assertEquals(theRuns.get(1).getAction().toString(), SleepAction.SLEEP.toString());
		Assert.assertEquals(theRuns.get(1).getDay(), Calendar.MONDAY);
		Assert.assertEquals(theRuns.get(4).getAction().toString(), SleepAction.SLEEP.toString());
		Assert.assertEquals(theRuns.get(4).getDay(), Calendar.THURSDAY);
		Assert.assertEquals(theRuns.get(6).getAction().toString(), SleepAction.WAKE.toString());
		Assert.assertEquals(theRuns.get(6).getDay(), Calendar.SATURDAY);
		Assert.assertEquals(theRuns.get(6).getFrom(), CCalendar.getSQLTime("00:00"));
		Assert.assertEquals(theRuns.get(6).getTo(), CCalendar.getSQLTime("23:59:59"));
		Assert.assertEquals(7, theRuns.size());
	}

	@Test
	public void testSleepRunsNoTime() throws ParseException {
		final Collection<SleepRun> theRuns = testSleepRuns(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, StringShop.EMPTY_STRING);
		Assert.assertEquals(0, theRuns.size());
	}

	private List<SleepSwitch> testSleepSwitches(String inWakeW, String inSleepW, String inWakeWe, String inSleepWe, int inNbSwitches) throws ParseException {
		final SleepTime theSleepTime = new SleepTime(inWakeW, inSleepW, inWakeWe, inSleepWe);
		final List<SleepSwitch> theSwitches = theSleepTime.getSleepSwitches();
		Assert.assertEquals(inNbSwitches, theSwitches.size());
		return theSwitches;
	}

	private List<SleepRun> testSleepRuns(String inWakeW, String inSleepW, String inWakeWe, String inSleepWe) throws ParseException {
		final SleepTime theSleepTime = new SleepTime(inWakeW, inSleepW, inWakeWe, inSleepWe);
		final List<SleepRun> theRuns = theSleepTime.getSleepRuns();
		return theRuns;

	}
}
