package net.violet.platform.api.actions.subscriptions;

import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.InvalidSchedulingsException;
import net.violet.platform.api.exceptions.InvalidSettingException;
import net.violet.platform.api.exceptions.MissingSettingException;
import net.violet.platform.api.exceptions.NoSuchSubscriptionException;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.schedulers.SchedulingHandlerManager;

import org.apache.log4j.Logger;

public class SetScheduling extends AbstractSubscriptionAction {

	private static final Logger LOGGER = Logger.getLogger(SetScheduling.class);

	@Override
	public Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchSubscriptionException {

		final SubscriptionData theSubscriptionData = getRequestedSubscription(inParam);

		final List<Map<String, Object>> schedulingInformation = inParam.getList("scheduling", true);

		// FIXME keep it, don't see the point of this right now but it should be important

//		if (!theSubscriptionData.getApplication().equals(ApplicationData.getData(Application.NativeApplication.MAIL.getApplication()))) {
//			SchedulingType.SCHEDULING_TYPE.createUpdate(theSubscriptionData, theSubscriptionData.getObject(), schedulingInformation, inParam.getCallerAPIKey());
//		} else {
//			final List<Map<String, Object>> settingsInformation = new LinkedList<Map<String, Object>>();
//			for (final SubscriptionSettingsData aSetting : theSubscriptionData.getAllSettings()) {
//				final String key = aSetting.getKey();
//				final String value = (key.equals(AuthentificationHandler.LOGIN) || key.equals(AuthentificationHandler.PASSWORD)) ? CipherTools.uncipher(aSetting.getValue()) : aSetting.getValue();
//				settingsInformation.add(DefaultHandler.ExternalSettingToolBox.createSetting(key, value));
//			}
//
//			SubscriptionHandlerFactory.update(inParam.getCallerAPIKey(), theSubscriptionData, settingsInformation, schedulingInformation);
//		}

		try {
			SubscriptionManager.updateSubscription(theSubscriptionData, null, schedulingInformation, inParam.getCaller());
		} catch (final InvalidSchedulingsException e) {
			SetScheduling.LOGGER.fatal(e, e);
			throw new InvalidParameterException("scheduling");
		} catch (final InvalidSettingException e) {
			SetScheduling.LOGGER.fatal(e, e);
			throw new InvalidParameterException("scheduling");
		} catch (final MissingSettingException e) {
			SetScheduling.LOGGER.fatal(e, e);
			throw new InvalidParameterException("scheduling");
		}

		return SchedulingHandlerManager.getUISettings(theSubscriptionData, inParam.getCaller());
	}

	public long getExpirationTime() {
		return 0;
	}

	public ActionType getType() {
		return ActionType.UPDATE;
	}

	public boolean isCacheable() {
		return false;
	}

}
