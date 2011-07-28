package net.violet.common.debug;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;

/**
 * This class is used by log4j. See log4j.properties
 * @author gerard
 *
 */
public class CustomEvaluator implements TriggeringEventEvaluator {

	private static final Timer TIMER = new Timer("log4j:CustomEvaluator", true);
	private static final int BUG_KEEP_ALIVE_FOR = 300000;

	static {
		final TimerTask theTask = new TimerTask() {

			@Override
			public void run() {
				synchronized (CustomEvaluator.mEventMemory) {
					CustomEvaluator.mEventMemory.clear();
				}
			}

		};

		CustomEvaluator.TIMER.scheduleAtFixedRate(theTask, CustomEvaluator.BUG_KEEP_ALIVE_FOR, CustomEvaluator.BUG_KEEP_ALIVE_FOR);
	}

	private static final Map<String, Integer> mEventMemory = new HashMap<String, Integer>();

	/**
	 * Implementions of this interface allow certain appenders to decide when to
	 * perform an appender specific action. For example the SMTPAppender sends
	 * an email when the isTriggeringEvent(org.apache.log4j.spi.LoggingEvent)
	 * method returns true and adds the event to an internal buffer when the
	 * returned result is false.
	 */
	public boolean isTriggeringEvent(LoggingEvent inEvent) {
		final String theLoggerName = inEvent.getLoggerName();
		final boolean theResult;
		synchronized (CustomEvaluator.mEventMemory) {

			final Integer theCurrentCount = CustomEvaluator.mEventMemory.get(theLoggerName);

			if (theCurrentCount == null) {
				CustomEvaluator.mEventMemory.put(theLoggerName, 1);
				theResult = true;
			} else if (theCurrentCount < net.violet.common.Constantes.SEND_MAIL_ALERT_BUG) {
				CustomEvaluator.mEventMemory.put(theLoggerName, theCurrentCount + 1);
				theResult = true;
			} else {
				theResult = false;
			}
		}

		return theResult;
	}

}
