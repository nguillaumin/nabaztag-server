package net.violet.mynabaztag.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.violet.platform.applications.VActionFullHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Feed;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.FeedData;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.feeds.FeedsTools;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class MySrvPodcastFullForm extends MySrvPodcastAbstractForm {

	/**
	 * Used to set the encoding for the form this fixes problems with accents
	 * when text's going from the form (JSP) to the form object (JAVA) *
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		final User theUser = SessionTools.getUserFromSession(request);

		if (theUser != null) {
			setLangList(TtsLangData.findAll(theUser));
		}
		super.reset(mapping, request);

		setSrvNbNews(1); // Nb de news pas défaut
		setGood(true);
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
			if (object.getHardware() == HARDWARE.V1) {
				setIsV1("true");
				errors.add("userNotV2", new ActionMessage("errors.key", StringShop.EMPTY_STRING));
				return errors;
			}

			if ("activate".equals(getDispatch())) {
				final Lang theLang = object.getPreferences().getLangPreferences();

				final VActionFullHandler handler = new VActionFullHandler(ApplicationData.getData(Application.NativeApplication.PODCAST_FULL.getApplication()));
//					final VAction action = handler.getVAction(getUrl(), theLang, false);
//
//					if (handler.hasAlreadySubscribed(action, object)) {
//						errors.add("scenarioOwned", new ActionMessage("errors.addRss", DicoTools.dico(theLang, "srv_podcast/scenario_owned")));
//					}

				final FeedData feed = FeedData.findByUrlAndType(getUrl(), Feed.Type.PODCAST);
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
