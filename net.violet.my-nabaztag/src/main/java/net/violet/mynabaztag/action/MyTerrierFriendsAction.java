package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyTerrierFriendsForm;
import net.violet.platform.datamodel.Black;
import net.violet.platform.datamodel.BlackImpl;
import net.violet.platform.datamodel.Contact;
import net.violet.platform.datamodel.FriendsLists;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.UserImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ContactData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.struts.DispatchActionForLoggedUser;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;
import net.violet.platform.util.Templates;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class MyTerrierFriendsAction extends DispatchActionForLoggedUser {

	public static final int ALREADY_IN_FL = 6;
	public static final int ADD_TO_FL = 7;
	public static final int MUST_WAIT = 8;
	public static final int MAIL_SENT = 9;
	public static final int FL_ERROR = -1;

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final MyTerrierFriendsForm myForm = (MyTerrierFriendsForm) form;
		final User user = SessionTools.getUserFromSession(request);
		final UserData theUserData = UserData.getData(user);
		// Liste de demande d'ami de l'utilisateur
		final List<ContactData> theSentRequest = ContactData.getSentContactRequest(theUserData, 0, UserImpl.MAX_CONTACT);
		// Liste de demande d'ami des contacts
		final List<ContactData> theReceivedRequest = ContactData.getReceivedContactRequest(theUserData, 0, UserImpl.MAX_CONTACT);
		// Liste de demande d'ami des contacts ( en retirant les black listé)
		final List<ContactData> theGoodReceivedRequest = new ArrayList<ContactData>();

		// On affiche pas les demandes des users black listés
		final Map<User, Black> tmpBlackList = user.getBlackList();
		for (final ContactData theContactData : theReceivedRequest) {
			final UserData theContact = theContactData.getPerson();
			if (!tmpBlackList.containsKey(theContact.getReference())) {
				theGoodReceivedRequest.add(theContactData);
			}
		}

		final List<UserData> ansListForm = new ArrayList<UserData>();
		final List<UserData> reqListForm = new ArrayList<UserData>();
		final List<UserData> friendListForm = new ArrayList<UserData>();

		for (final ContactData friend : theGoodReceivedRequest) {
			ansListForm.add(friend.getPerson());
		}

		for (final ContactData friend : theSentRequest) {
			reqListForm.add(friend.getContact());
		}

		for (final Contact friend : user.getAllContacts()) {
			friendListForm.add(UserData.getData(friend.getContact()));
		}

		// FIXME inverse dans la jsp pour retablir la coherence de la Form
		myForm.setReqList(ansListForm);
		myForm.setAnsList(reqListForm);
		myForm.setListFriends(friendListForm);

		return mapping.getInputForward();
	}

	// Supprime des amis
	public ActionForward deleteFriends(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {
		final MyTerrierFriendsForm myForm = (MyTerrierFriendsForm) form;
		final User user = SessionTools.getUserFromSession(request);
		if (myForm.getCheckListFriends() != null) {
			for (final String s : myForm.getCheckListFriends()) {
				final long friendId = Long.parseLong(s);
				final User friend = Factories.USER.find(friendId);
				if (friend != null) {
					Contact theContactRemove = null;
					for (final Contact thecontact : user.getAllContacts()) {
						if (thecontact.getContact().getId().equals(friend.getId())) {
							thecontact.delete();
							theContactRemove = thecontact;
						}
					}
					if (theContactRemove != null) {
						user.removeContact(theContactRemove);
					}
				}
			}
		}

		return load(mapping, form, request, response);
	}

	// Ajoute des amis a la blackList
	public ActionForward addBlackList(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {
		final MyTerrierFriendsForm myForm = (MyTerrierFriendsForm) form;
		final User user = SessionTools.getUserFromSession(request);
		if (myForm.getCheckListFriends() != null) {
			for (final String s : myForm.getCheckListFriends()) {
				final User userFriend = Factories.USER.find(Long.parseLong(s));
				Contact theContactRemove = null;
				for (final Contact thecontact : user.getAllContacts()) {
					if (thecontact.getContact().getId().equals(userFriend.getId())) {
						thecontact.delete();
						theContactRemove = thecontact;
					}
				}
				if (theContactRemove != null) {
					user.removeContact(theContactRemove);
				}

				final BlackImpl black = new BlackImpl(user, userFriend, StringShop.EMPTY_STRING);
				user.getBlackList().put(userFriend, black);
			}
		}
		return load(mapping, form, request, response);
	}

	// Accepte une demande d'amitie
	public ActionForward acceptFriend(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {
		final MyTerrierFriendsForm myForm = (MyTerrierFriendsForm) form;
		final User user = SessionTools.getUserFromSession(request);
		final int friendId = myForm.getFriend_id();

		if (friendId > 0) {
			final User userFriend = Factories.USER.find(friendId);
			if (userFriend != null) {
				final Contact theContact = Factories.CONTACT.getContactByUserAndContact(userFriend, user);

				if (theContact != null) {
					if (Contact.STATUS.PENDING.toString().equalsIgnoreCase(theContact.getStatus())) {
						theContact.changeContact(Contact.STATUS.ACCEPTED);
						userFriend.addContact(theContact);
					}
				}
			}
		}

		return load(mapping, form, request, response);
	}

	// Accepte une demande et ajoute aux amis
	public ActionForward acceptFriendAdd(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {
		final MyTerrierFriendsForm myForm = (MyTerrierFriendsForm) form;
		final User user = SessionTools.getUserFromSession(request);
		final int friendId = myForm.getFriend_id();

		if (friendId > 0) {
			final User userFriend = Factories.USER.find(friendId);
			if (userFriend != null) {
				final Contact theContact = Factories.CONTACT.getContactByUserAndContact(userFriend, user);

				if (theContact != null) {
					if (Contact.STATUS.PENDING.toString().equalsIgnoreCase(theContact.getStatus())) {
						theContact.changeContact(Contact.STATUS.ACCEPTED);
						userFriend.addContact(theContact);
					}

					MyTerrierFriendsAction.addContact(user, userFriend);
				}
			}
		}

		return load(mapping, form, request, response);
	}

	// refuse une demande
	public ActionForward declineFriend(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {
		final MyTerrierFriendsForm myForm = (MyTerrierFriendsForm) form;
		final User user = SessionTools.getUserFromSession(request);
		final int friendId = myForm.getFriend_id();

		if (friendId > 0) {
			final User friend = Factories.USER.find(friendId);
			if (friend != null) {
				final Contact theContact = Factories.CONTACT.getContactByUserAndContact(friend, user);
				if (theContact != null) {
					theContact.changeContact(Contact.STATUS.REJECTED);
				}
			}
		}

		return load(mapping, form, request, response);
	}

	// annule une demande
	public ActionForward cancelFriend(

	ActionMapping mapping,

	ActionForm form,

	HttpServletRequest request,

	HttpServletResponse response) {
		final MyTerrierFriendsForm myForm = (MyTerrierFriendsForm) form;
		final User user = SessionTools.getUserFromSession(request);
		final int friendId = myForm.getFriend_id();

		if (friendId > 0) {
			final User friend = Factories.USER.find(friendId);
			if (friend != null) {
				final Contact theContact = Factories.CONTACT.getContactByUserAndContact(user, friend);
				if (theContact != null) {
					theContact.delete();
				}
			}
		}

		return load(mapping, form, request, response);
	}

	// permet l'ajout d'un ami
	public ActionForward addFriend(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);
		final MyTerrierFriendsForm myForm = (MyTerrierFriendsForm) form;
		final User user = SessionTools.getUserFromSession(request);
		final Lang lang = SessionTools.getLangFromSession(session, request);
		final String name = myForm.getName();

		final VObject friendObject = Factories.VOBJECT.findByName(name);

		final ActionMessages errors = new ActionMessages();
		if (friendObject == null) {
			errors.add("friendInexistent", new ActionMessage("errors.addFriend", DicoTools.dico(lang, "friend/user_not_exist")));
			saveErrors(request, errors);
			return load(mapping, form, request, response);
		}

		final User friend = friendObject.getOwner();

		final Map<User, Black> blackList = user.getBlackList();
		for (final User l : blackList.keySet()) {
			if (l.getId().equals(friend.getId())) {
				errors.add("blackedUser", new ActionMessage("errors.addFriend", DicoTools.dico(lang, "friend/user_is_blacked")));
				saveErrors(request, errors);
				return load(mapping, form, request, response);
			}
		}

		int res = MyTerrierFriendsAction.FL_ERROR;

		if (user.existFriend(friend)) {
			res = MyTerrierFriendsAction.ALREADY_IN_FL;
		}

		if (res != MyTerrierFriendsAction.ALREADY_IN_FL) {
			res = MyTerrierFriendsAction.addContact(user, friend);
		}

		if (res == MyTerrierFriendsAction.FL_ERROR) {
			errors.add("failed", new ActionMessage("errors.addFriend", DicoTools.dico(lang, "friend/error")));
		}
		if (res == MyTerrierFriendsAction.ALREADY_IN_FL) {
			errors.add("alreadyFriend", new ActionMessage("errors.addFriend", name + StringShop.SPACE + DicoTools.dico(lang, "friend/already_in_list")));
		}
		if (res == MyTerrierFriendsAction.ADD_TO_FL) {
			errors.add("succes", new ActionMessage("errors.addFriend", name + StringShop.SPACE + DicoTools.dico(lang, "friend/added_to_list")));
		}
		if (res == MyTerrierFriendsAction.MAIL_SENT) {
			errors.add("mailSent", new ActionMessage("errors.addFriend", DicoTools.dico(lang, "friend/mail_sent") + StringShop.SPACE + name + StringShop.SPACE + DicoTools.dico(lang, "friend/ask_be_friend")));
		}
		if (res == MyTerrierFriendsAction.MUST_WAIT) {
			errors.add("mustWait", new ActionMessage("errors.addFriend", DicoTools.dico(lang, "friend/already_asked") + StringShop.SPACE + name + StringShop.SPACE + DicoTools.dico(lang, "friend/to_be_friend") + "\n" + DicoTools.dico(lang, "friend/wait_answer")));
		}

		saveErrors(request, errors);

		return load(mapping, form, request, response);
	}

	public static int addContact(User inUser, User inContact) {

		int theResult = MyTerrierFriendsAction.ADD_TO_FL;

		final FriendsLists theFriendList = Factories.FRIENDS_LISTS.findByUser(inContact);
		long theConfirmation = 0;
		if (theFriendList != null) {
			theConfirmation = theFriendList.getFriendslists_confirmationlevel();
		}

		// le contact veux etre notifié par mail
		if ((theConfirmation == 3) || (theConfirmation == 2) || (theConfirmation == 0)) {
			if (theConfirmation != 3) {
				inUser.addContact(Factories.CONTACT.createContact(inUser, inContact, Contact.STATUS.AUTOMATICALLY_ACCEPTED));
			}
			if (theConfirmation != 0) {
				Templates.notifyAddFriend(inUser, inContact); // mail de notification d'ami
				theResult = MyTerrierFriendsAction.MAIL_SENT;
			}
		}

		// le contact veux pouvoir accepter la demande
		if ((theConfirmation == 3) || (theConfirmation == 1)) {
			Factories.CONTACT.createContact(inUser, inContact, Contact.STATUS.PENDING);
			Templates.validateAddFriend(inUser, inContact); // mail de validation d'ami
			theResult = MyTerrierFriendsAction.MUST_WAIT;
		}

		return theResult;
	}
}
