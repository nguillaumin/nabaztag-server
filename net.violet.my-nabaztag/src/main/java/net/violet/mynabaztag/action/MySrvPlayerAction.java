package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.data.PlayerData;
import net.violet.mynabaztag.form.MySrvPlayerForm;
import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.interactif.config.GallimardConfig3760125572246;
import net.violet.platform.interactif.config.GallimardConfig3760125572253;
import net.violet.platform.interactif.config.GallimardConfig3760125572260;
import net.violet.platform.interactif.config.GallimardConfig3760125572307;
import net.violet.platform.interactif.config.GallimardConfig9782070548064;
import net.violet.platform.interactif.config.PenguinConfig3760125572345;
import net.violet.platform.interactif.config.PenguinConfig3760125572352;
import net.violet.platform.interactif.config.PenguinConfig3760125572369;
import net.violet.platform.interactif.config.PlayerConfig;
import net.violet.platform.interactif.config.PlayerConfigFactory;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MySrvPlayerAction extends DispatchActionForLoggedUserWithObject {

	private static final Map<Long, String> dicoRootBooks = new HashMap<Long, String>();

	static {
		MySrvPlayerAction.dicoRootBooks.put(GallimardConfig9782070548064.ISBN, "lblp");
		MySrvPlayerAction.dicoRootBooks.put(GallimardConfig3760125572246.ISBN, "lechatbotte");
		MySrvPlayerAction.dicoRootBooks.put(GallimardConfig3760125572253.ISBN, "alt");
		MySrvPlayerAction.dicoRootBooks.put(GallimardConfig3760125572260.ISBN, "lpcr");
		MySrvPlayerAction.dicoRootBooks.put(GallimardConfig3760125572307.ISBN, "lmp");

		MySrvPlayerAction.dicoRootBooks.put(PenguinConfig3760125572345.ISBN, "theelves");
		MySrvPlayerAction.dicoRootBooks.put(PenguinConfig3760125572352.ISBN, "cinderella");
		MySrvPlayerAction.dicoRootBooks.put(PenguinConfig3760125572369.ISBN, "goldilocks");
	}

	// Affichage de la page

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);
		final MySrvPlayerForm myForm = (MySrvPlayerForm) form;

		final long applet_id = myForm.getAppletId();

		System.out.println("Thierry : applet_id=" + applet_id + " / isbn : " + myForm.getIsbn());

		if ((applet_id > 0) && (myForm.getIsbn() > 0)) {

			final PlayerConfig theConfig = PlayerConfigFactory.getConfig(myForm.getIsbn());
			final List<AppletSettings> theListSetting = Factories.APPLET_SETTINGS.getAllAppletSettingsBySecondaryObjectAndKey(object, applet_id, theConfig.getMarkup());

			if (!theListSetting.isEmpty()) {// il a un marque page pour au moins
				// un livre
				myForm.setIsMarkup(1);
				final List<PlayerData> theListPlayerData = new ArrayList<PlayerData>();
				for (final AppletSettings theApplet : theListSetting) {
					PlayerData thePlayerData = new PlayerData(theApplet, theConfig);
					theListPlayerData.add(thePlayerData);
					thePlayerData = null;
				}
				myForm.setMySetting(theListPlayerData);
				myForm.setSizeVoice(theConfig.getCountVoice());
			}
			if (myForm.getIsbn() != null) {
				myForm.setDicoRoot(MySrvPlayerAction.dicoRootBooks.get(myForm.getIsbn()));
			} else {
				myForm.setDicoRoot(StringShop.EMPTY_STRING);
			}

		}
		return mapping.getInputForward();
	}

	/**
	 * Mise a jour de la voix du bouquin
	 */
	public ActionForward update(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);
		final MySrvPlayerForm myForm = (MySrvPlayerForm) form;

		final long applet_id = myForm.getAppletId();
		final long voice_id = myForm.getVoiceId();
		final long book_id = myForm.getBookId();
		final String book_serial = myForm.getBookSerial();
		final PlayerConfig theConfig = (myForm.getIsbn() > 0) ? PlayerConfigFactory.getConfig(myForm.getIsbn()) : null;

		if ((applet_id > 0) && (voice_id >= 0) && (theConfig != null) && (voice_id < theConfig.getCountVoice()) && (book_id > 0)) {
			final VObject theBook = Factories.VOBJECT.findBySerial(book_serial);

			if (theBook != null) {
				Factories.APPLET_SETTINGS.setAppletSettingsByObjects(theBook, object, applet_id, theConfig.getVoice(), Long.toString(voice_id), theConfig.getIsbn());
			}
			myForm.setAppletId(applet_id);
		}
		return load(mapping, form, request, response);
	}

	/**
	 * reset le marque page
	 */
	public ActionForward resetApplet(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {
		final MySrvPlayerForm myForm = (MySrvPlayerForm) form;

		final long applet_id = myForm.getAppletId();
		final long book_id = myForm.getBookId();
		final String book_serial = myForm.getBookSerial();

		if ((applet_id > 0) && (book_id > 0) && (myForm.getIsbn() > 0)) {
			final PlayerConfig theConfig = PlayerConfigFactory.getConfig(myForm.getIsbn());
			final VObject theBook = Factories.VOBJECT.findBySerial(book_serial);

			if (theBook != null) {
				final AppletSettings theAppletSettings = Factories.APPLET_SETTINGS.getAppletSettingsByObject(theBook, applet_id, theConfig.getMarkup());
				if (theAppletSettings != null) {
					theAppletSettings.setValue(Long.toString(0));
				}
			}
			myForm.setAppletId(applet_id);
		}
		return load(mapping, form, request, response);
	}
}
