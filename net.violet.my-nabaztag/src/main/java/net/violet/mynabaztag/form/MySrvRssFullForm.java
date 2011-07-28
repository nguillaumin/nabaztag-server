package net.violet.mynabaztag.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.PeriodServiceDataFactory;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.FeedsTools;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.SessionTools;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class MySrvRssFullForm extends MySrvAbstractForm {

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		final User theUser = SessionTools.getUserFromSession(request);

		if (theUser != null) {
			setLangList(TtsLangData.findAll(theUser));
		}

		final HttpSession session = request.getSession(true);
		final Lang lang = SessionTools.getLangFromSession(session, request);
		setPeriodList(PeriodServiceDataFactory.generateListFrequence(ServiceFactory.SERVICE.RSS.getService(), lang));
		setGood(true);
		super.reset(mapping, request);
	}

	/**
	 * Validate the form server side
	 */
	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		final ActionErrors errors = super.validate(mapping, request);
		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);

		if (null != object) {
			// Only if the user is trying to activate a new full rss
			if ("activate".equals(getDispatch())) {
				final Lang theLang = object.getPreferences().getLangPreferences();
				final VActionFullHandler handler = new VActionFullHandler(ApplicationData.getData(Application.NativeApplication.RSS_FULL.getApplication()));

				final FeedData feed = FeedData.findByUrlAndType(getUrl(), Feed.Type.RSS);
				if (feed != null && feed.isValid() && handler.hasAlreadySubscribed(feed, VObjectData.getData(object))) {
					errors.add("scenarioOwned", new ActionMessage("errors.addRss", DicoTools.dico(theLang, "srv_podcast/scenario_owned")));
				} else {
					if (!FeedsTools.isFeedValid(getUrl(), null, null)) {
						errors.add("crappyFeed", new ActionMessage("errors.addPodcast", DicoTools.dico(theLang, "srv_podcast/bad_feed")));
						setGood(false);
						return errors;
					};
				}

			}
		}
		return errors;
	}
}
