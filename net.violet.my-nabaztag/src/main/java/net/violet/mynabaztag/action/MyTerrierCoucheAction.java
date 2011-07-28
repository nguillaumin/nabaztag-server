package net.violet.mynabaztag.action;

import java.text.ParseException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyTerrierCoucheForm;
import net.violet.platform.datamodel.ObjectSleep;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.SleepTime;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyTerrierCoucheAction extends ActionWithLog {

	private static final Logger LOGGER = Logger.getLogger(MyTerrierCoucheAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MyTerrierCoucheForm myForm = (MyTerrierCoucheForm) form;

		final User theUser = SessionTools.getUserFromSession(request);

		/**
		 * Check if the user really exists, i.e. theUser != null
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		final VObject rabbitObj = SessionTools.getRabbitFromSession(request.getSession(true));
		/**
		 * Check if the user has a rabbit
		 */
		if (rabbitObj == null) {
			return mapping.getInputForward();
		}

		String sleepW = myForm.getEndW();
		String sleepWe = myForm.getEndWe();
		String wakeW = myForm.getStartW();
		String wakeWe = myForm.getStartWe();

		if (myForm.getDelete() == 1) {
			rabbitObj.setVeilleObjetDeactivated();
		}

		if (myForm.getAdd() == 1) {
			try {
				final SleepTime theSleepTime = new SleepTime(wakeW, sleepW, wakeWe, sleepWe);
				rabbitObj.setSleepTime(theSleepTime);
			} catch (final ParseException e) {
				MyTerrierCoucheAction.LOGGER.fatal(e);
			}
		}

		sleepW = StringShop.EMPTY_STRING;
		wakeW = StringShop.EMPTY_STRING;
		sleepWe = StringShop.EMPTY_STRING;
		wakeWe = StringShop.EMPTY_STRING;
		int isReg = 0;

		final ObjectSleep weekInfo = Factories.OBJECT_SLEEP.findInfoByObjectAndDay(rabbitObj, Calendar.TUESDAY);
		final ObjectSleep weekEndInfo = Factories.OBJECT_SLEEP.findInfoByObjectAndDay(rabbitObj, Calendar.SUNDAY);
		if ((weekInfo == null) && (weekEndInfo == null)) {
			isReg = 0;
		} else {
			final CCalendar theCalendar = new CCalendar(false);
			if ((weekInfo != null) && (weekInfo.getWakeTime() != null)) {
				theCalendar.setTime(weekInfo.getWakeTime());
				wakeW = theCalendar.getTimeFormated(true);

				theCalendar.setTime(weekInfo.getSleepTime());
				sleepW = theCalendar.getTimeFormated(true);
			}

			if ((weekEndInfo != null) && (weekEndInfo.getWakeTime() != null)) {
				theCalendar.setTime(weekEndInfo.getWakeTime());
				wakeWe = theCalendar.getTimeFormated(true);

				theCalendar.setTime(weekEndInfo.getSleepTime());
				sleepWe = theCalendar.getTimeFormated(true);
			}
			isReg = 1;
		}

		myForm.setUser_main(rabbitObj.getId().intValue());
		myForm.setIsReg(isReg);
		myForm.setAdd(0);
		myForm.setDelete(0);
		myForm.setStartW(wakeW);
		myForm.setEndW(sleepW);
		myForm.setStartWe(wakeWe);
		myForm.setEndWe(sleepWe);
		return mapping.getInputForward();
	}
}
