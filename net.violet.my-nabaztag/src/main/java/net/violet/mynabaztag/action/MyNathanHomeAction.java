package net.violet.mynabaztag.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.data.PlayerData;
import net.violet.mynabaztag.form.MyNathanHomeForm;
import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationImpl;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.NathanTagData;
import net.violet.platform.dataobjects.NathanVersionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.interactif.config.NathanPlayerConfig;
import net.violet.platform.interactif.config.PlayerConfig;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyNathanHomeAction extends DispatchActionForLoggedUserWithObject {

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);

		final MyNathanHomeForm myForm = (MyNathanHomeForm) form;

		final long applet_id = myForm.getAppletId();
		final Long isbn = myForm.getIsbn();
		myForm.setObjectLogin(object.getObject_login());

		if ((isbn != null) && (applet_id > 0) && (isbn > 0)) {

			// We are displaying the page for the first time : default result
			// list is the official one
			if (request.getParameter("dispatch").equals("load")) {
				myForm.setResultList(NathanVersionData.findAllOfficial(Long.toString(isbn)));
			}

			// we try to find a markup key for this book and this user, if there
			// is at least one, he/she can access the config page
			final PlayerConfig theConfig = new NathanPlayerConfig(isbn);
			final List<AppletSettings> theListSetting = Factories.APPLET_SETTINGS.getAllAppletSettingsBySecondaryObjectAndKey(object, applet_id, theConfig.getMarkup());

			if (!theListSetting.isEmpty()) {
				myForm.setIsMarkup(theListSetting.size());

				final List<PlayerData> theListPlayerData = new ArrayList<PlayerData>();
				for (final AppletSettings theApplet : theListSetting) {
					final PlayerData thePlayerData = new PlayerData(theApplet, theConfig);
					theListPlayerData.add(thePlayerData);
				}
				myForm.setMySetting(theListPlayerData);

				if ((myForm.getSerial() == null) || myForm.getSerial().equals("")) {
					myForm.setSerial(theListPlayerData.get(0).getZtampSerial());
				}

				if ((myForm.getPicture() == 0) || (myForm.getPicture() == 0)) {
					myForm.setPicture(theListPlayerData.get(0).getPictureObject());
				}

			} else {
				// retrieve information for the new bloc on the page
				final List<OtherNathanData> list = new ArrayList<OtherNathanData>();
				for (final long anIsbn : NathanPlayerConfig.BOOKSROOT.keySet()) {
					if (anIsbn != isbn) { // only for the other books

						final PlayerConfig theOtherConfig = new NathanPlayerConfig(anIsbn);
						final List<AppletSettings> theOtherListSetting = Factories.APPLET_SETTINGS.getAllAppletSettingsBySecondaryObjectAndKey(object, applet_id, theOtherConfig.getMarkup());

						final Application theService = ApplicationImpl.findByAppletId(applet_id, anIsbn);
						if (theService != null) {
							final int nbVersions = Factories.NATHAN_VERSION.countByBook(anIsbn);
							int hasBook;
							final String author = NathanPlayerConfig.LIST_PATH_STREAM_OFFICIAL_VERSION.get(anIsbn).get("author");
							final String title = NathanPlayerConfig.LIST_PATH_STREAM_OFFICIAL_VERSION.get(anIsbn).get("title");

							// The user has this other book
							if (!theOtherListSetting.isEmpty()) {
								hasBook = 1;
							} else {
								hasBook = 0;
							}

							list.add(new OtherNathanData(hasBook, theService.getImg(), nbVersions, theService.getShortcut(), author, title));
						}
					}
				}

				myForm.setOtherBooksList(list);

			}

			myForm.setDicoRoot(NathanPlayerConfig.BOOKSROOT.get(isbn));
		}

		return mapping.getInputForward();
	}

	// This method fills the resultList with the official versions
	public ActionForward official(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MyNathanHomeForm myForm = (MyNathanHomeForm) form;
		final long isbn = myForm.getIsbn();
		myForm.setResultList(NathanVersionData.findAllOfficial(Long.toString(isbn)));
		return load(mapping, form, request, response);
	}

	// This method fills the resultList with the most used versions
	public ActionForward popular(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MyNathanHomeForm myForm = (MyNathanHomeForm) form;
		final long isbn = myForm.getIsbn();
		myForm.setResultList(NathanVersionData.getPopularVersions(Long.toString(isbn)));
		return load(mapping, form, request, response);
	}

	// This method fills in the resultList with the most recently created
	// versions
	public ActionForward recent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MyNathanHomeForm myForm = (MyNathanHomeForm) form;
		final Long isbn = myForm.getIsbn();
		myForm.setResultList(NathanVersionData.getRecentVersions(Long.toString(isbn)));
		return load(mapping, form, request, response);
	}

	// This method fills in the resultList according to the requested tags
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MyNathanHomeForm myForm = (MyNathanHomeForm) form;

		final long isbn = myForm.getIsbn();

		myForm.setSexTags(NathanTagData.findTagsByCategory(0));
		myForm.setInterpretationTags(NathanTagData.findTagsByCategory(1));
		myForm.setEffectsTags(NathanTagData.findTagsByCategory(2));
		myForm.setAccentTags(NathanTagData.findTagsByCategory(3));

		final List<Long> checkedTags = new ArrayList<Long>();
		for (final String aTagId : myForm.getCheckedTags()) {
			checkedTags.add(Long.parseLong(aTagId));
		}

		if (!checkedTags.isEmpty()) {
			myForm.setResultList(NathanVersionData.lookForVersions(checkedTags, Long.toString(isbn)));
		}

		if (myForm.getIsbn() != null) {
			myForm.setDicoRoot(NathanPlayerConfig.BOOKSROOT.get(myForm.getIsbn()));
		} else {
			myForm.setDicoRoot(StringShop.EMPTY_STRING);
		}
		load(mapping, form, request, response);

		return mapping.findForward("searchResult");
	}

	// This method fills in the resultList with the versions belonging to the
	// book object selection
	public ActionForward selection(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final User user = SessionTools.getUserFromSession(request);

		final MyNathanHomeForm myForm = (MyNathanHomeForm) form;

		final long isbn = myForm.getIsbn();
		final String bookSerial = myForm.getBookSerial();

		final VObject theBook = Factories.VOBJECT.findBySerial(bookSerial);

		// Checks if the book really exists and if it's owned by the user
		if ((theBook != null) && theBook.getOwner().equals(user)) {
			myForm.setResultList(NathanVersionData.getObjectSelection(VObjectData.getData(theBook), Long.toString(isbn)));
		}

		load(mapping, form, request, response);

		return mapping.findForward("selection");
	}

	// This method fills in the resultList with the versions created by the user
	public ActionForward myversion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);

		final MyNathanHomeForm myForm = (MyNathanHomeForm) form;

		final long isbn = myForm.getIsbn();

		myForm.setResultList(NathanVersionData.findAllByAuthorAndIsbn(VObjectData.getData(object), Long.toString(isbn)));

		load(mapping, form, request, response);

		return mapping.findForward("myVersions");
	}

	// This method deletes the provided version and calls the 'myversion' method
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);

		final MyNathanHomeForm myForm = (MyNathanHomeForm) form;

		final long versionId = myForm.getVersionId();
		final NathanVersion theVersion = Factories.NATHAN_VERSION.find(versionId);

		// we check if the user really is the version's creator
		if ((theVersion != null) && theVersion.getAuthor().equals(object)) {
			NathanVersionData.deleteVersion(versionId);
		}

		return myversion(mapping, form, request, response);
	}

	// This method is used to add selected versions to the user selection
	public ActionForward subscribe(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final User user = SessionTools.getUserFromSession(request);
		final VObject object = SessionTools.getRabbitFromSession(session);

		final MyNathanHomeForm myForm = (MyNathanHomeForm) form;

		final String[] selectedVersions = myForm.getSelectedVersions();

		final String bookSerial = myForm.getBookSerial();
		final VObject theBook = Factories.VOBJECT.findBySerial(bookSerial);

		if ((theBook != null) && theBook.getOwner().equals(user) && (selectedVersions != null) && (selectedVersions.length > 0)) {
			for (final String idVersion : selectedVersions) {
				final NathanVersionData theVersion = NathanVersionData.find(Long.parseLong(idVersion));

				// The version exists
				if (theVersion != null) {
					// The version was created by the user or is shared and
					// authorized
					if (theVersion.getAuthor().equals(object.getObject_login()) || (theVersion.getShared() && theVersion.isAuthorized())) {
						theVersion.addToObjectSelection(theBook);
					}
				}
			}
		}

		myForm.setSelectedVersions(new String[0]);

		return selection(mapping, form, request, response);
	}

	// This method is used to add selected versions to the user selection
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final User user = SessionTools.getUserFromSession(request);

		final MyNathanHomeForm myForm = (MyNathanHomeForm) form;

		final String[] selectedVersions = myForm.getSelectedVersions();
		final String bookSerial = myForm.getBookSerial();
		final VObject theBook = Factories.VOBJECT.findBySerial(bookSerial);

		if ((theBook != null) && theBook.getOwner().equals(user) && (selectedVersions != null) && (selectedVersions.length > 0)) {
			for (final String idVersion : selectedVersions) {
				final NathanVersionData theVersion = NathanVersionData.find(Long.parseLong(idVersion));
				if (theVersion != null) {
					theVersion.removeFromObjectSelection(theBook);
				}
			}
		}

		myForm.setSelectedVersions(new String[0]);

		return selection(mapping, form, request, response);
	}

	public static class OtherNathanData implements Serializable {

		private final String hasBook;
		private final String img;
		private String img_grise;
		private final int nbVersions;
		private final String url;
		private final String author;
		private final String title;

		public OtherNathanData(int hasBook, String img, int nbVersions, String url, String author, String title) {
			this.hasBook = Integer.toString(hasBook);
			this.img = img;
			this.img_grise = getImgGrise();
			this.nbVersions = nbVersions;
			this.url = url;
			this.author = author;
			this.title = title;
		}

		public String getHasBook() {
			return this.hasBook;
		}

		public String getImg() {
			return this.img;
		}

		public String getImgGrise() {
			final String buf = this.img;
			this.img_grise = buf.substring(0, buf.indexOf(".gif")).concat("_grise.gif");
			return this.img_grise;
		}

		public int getNbVersions() {
			return this.nbVersions;
		}

		public String getUrl() {
			return this.url;
		}

		public String getAuthor() {
			return this.author;
		}

		public String getTitle() {
			return this.title;
		}

	}
}
