package net.violet.mynabaztag.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMessagesListForm;
import net.violet.platform.datamodel.BlackImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Message;
import net.violet.platform.datamodel.MessageReceived;
import net.violet.platform.datamodel.MessageSentImpl;
import net.violet.platform.datamodel.Messenger;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MessageReceivedData;
import net.violet.platform.dataobjects.MessageSentData;
import net.violet.platform.message.MessageServices;
import net.violet.platform.ping.EventMng;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMessagesListAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyMessagesListAction.class);

	static final int DEFAULT_NB_AFFICHE = 10;

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMessagesListForm myForm = (MyMessagesListForm) form;
		final HttpSession session = request.getSession(true);
		final User user = SessionTools.getUserFromSession(request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		/**
		 * Check if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		Messenger theMessenger = null;

		try {
			theMessenger = Factories.MESSENGER.getByUser(user);
		} catch (final IllegalArgumentException e) {
			MyMessagesListAction.LOGGER.fatal("Can't find a Messenger for this user " + user);
		}

		final Lang lang = SessionTools.getLangFromSession(session, request);

		String forward = "genericNoRabbitMessages";
		String errorMsg = StringShop.EMPTY_STRING;

		int nb_msg = 0;

		int indexD = myForm.getPage_indexD();
		int indexMM = myForm.getPage_indexMM();
		int indexM = myForm.getPage_indexM();
		int index = myForm.getPage_index();
		int indexP = myForm.getPage_indexP();
		int indexPP = myForm.getPage_indexPP();
		int indexF = myForm.getPage_indexF();
		final int indexNew = myForm.getPage_new();

		final String action = myForm.getAction();
		final String selectChoice = myForm.getSelectChoice();

		final String[] checkListMsg = myForm.getCheckListMsg();
		final int nabcast = myForm.getNabcast();
		// TODO: définir une interface commune pour les data objects ajoutés
		// ici.
		List listeMessages = new ArrayList();

		boolean displayNabcast = false;
		if (nabcast == 1) {
			displayNabcast = true;
		}

		if (theObject != null) {

			// mise en archive des messages
			if (selectChoice.equals("archive_msg") && (checkListMsg != null)) {
				for (int i = 0; i < checkListMsg.length; i++) {
					final String clef = checkListMsg[i];
					final Message theMessage = Factories.MESSAGE.find(Long.parseLong(clef));
					if (theMessage != null) {
						final MessageReceived theMessageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(theMessage.getId());
						theMessageReceived.setMessage_state(MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED);
						EventMng.refreshCountMessagesAfterPlaying(theMessageReceived.getRecipient());
					}
				}
			} else if (selectChoice.equals("delete_msg") && (checkListMsg != null)) {// suppression
				// des
				// messages
				boolean delete = false;
				for (int i = 0; i < checkListMsg.length; i++) {
					final String clef = checkListMsg[i];
					final Message theMessage = Factories.MESSAGE.find(Long.parseLong(clef));
					if (theMessage != null) {
						if (action.equals("sent")) {
							// si le message est différé
							if (theMessenger != null) {
								theMessenger.getMessageSent().remove(theMessage);
							}

							if (theMessage.getTimeOfDelivery().after(Calendar.getInstance())) {
								deleteReceivedMessage(theMessage);
							}
						} else {
							deleteReceivedMessage(theMessage);
							delete = true;
						}

						if (theMessage.isOrphan()) {
							theMessage.delete();
						}
					} else {
						MyMessagesListAction.LOGGER.fatal("The message with id = " + clef + "cannot be found and deleted");
					}
				}
				// mise à jour du nombre de message dans la INBOX, on ne le fait
				// qu'une fois qu'il a traité tous les messages
				if (delete) {
					EventMng.refreshCountMessagesAfterPlaying(theObject);
				}

			} else if (selectChoice.equals("replay_msg") && (checkListMsg != null)) {// rejouer
				// les
				// messages
				for (int i = 0; i < checkListMsg.length; i++) {
					final String clef = checkListMsg[i];
					final Message theMessage = Factories.MESSAGE.find(Long.parseLong(clef));
					if (theMessage != null) {
						final MessageReceived theMessageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(theMessage.getId());

						if (theMessageReceived != null) {

							MessageServices.resendUserMessage(theMessage, theMessageReceived.getSender(), Factories.MESSENGER.getByObject(theObject));
							EventMng.refreshCountMessagesAfterSending(theObject);

						} else {
							MyMessagesListAction.LOGGER.fatal("The message with the message_id = " + theMessage.getId() + "cannot be found");
						}
					} else {
						MyMessagesListAction.LOGGER.fatal("The message with id = " + clef + "cannot be found");
					}

				}
			} else if (selectChoice.equals("blacklist") && (checkListMsg != null)) {// mettre
				// l
				// 'envoyeur
				// en
				// blacklist
				for (int i = 0; i < checkListMsg.length; i++) {
					final String clef = checkListMsg[i];
					final Messenger theSender = Factories.MESSENGER.findSenderByMessageId(Long.parseLong(clef));

					// test l'utilisateur veux s'auto-blacklisté
					if (!user.equals(theSender.getUser())) {

						final User sender = theSender.getUser();

						if (!user.getBlackList().containsKey(sender)) {
							final BlackImpl black = new BlackImpl(user, sender, StringShop.EMPTY_STRING);
							user.getBlackList().put(sender, black);

							if (user.getFriends().contains(sender)) {
								user.getFriends().remove(sender);
							}
							errorMsg = "Cette personne est désormais dans votre liste noire";
						} else {
							errorMsg = "Cette personne est déjà dans votre liste noire";
						}
					} else {
						errorMsg = "Bien essayé !! Mais vous ne pouvez pas vous auto-blacklister";
					}
				}
			}

			if (action.equals("home_received")) {
				forward = "home_received";
			} else if (action.equals("home_sent")) {
				forward = "home_sent";
			} else if (action.equals("home_archived")) {
				forward = "home_archived";
			} else if (action.equals("received")) {
				nb_msg = (int) MessageReceivedData.countReceivedMessagesByVObject(theObject, MessageReceived.MESSAGE_RECEIVED_STATES.INBOX, displayNabcast);
			} else if (action.equals("sent")) {
				if (theMessenger != null) {
					nb_msg = theMessenger.getMessageSent().size();
				}
				// nb_msg = (int) EventSendData.countAllMessagesSent(user,
				// displayNabcast);
			} else if (action.equals("archived")) {
				nb_msg = (int) MessageReceivedData.countReceivedMessagesByVObject(theObject, MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED, displayNabcast);
			}

			/* INDEX GENERATOR */
			int nbAffParPage = myForm.getNbAffParPage();
			if (nbAffParPage == 0) {
				nbAffParPage = MyMessagesListAction.DEFAULT_NB_AFFICHE;
			}
			/* PAGE COUNT */
			int nb_pages = 1;
			if (nb_msg > nbAffParPage) {
				nb_pages = nb_msg / nbAffParPage;
				final float nb_pages_tmp = (float) nb_msg / nbAffParPage;
				if (nb_pages_tmp > (int) nb_pages_tmp) {
					nb_pages++;
				}
			}
			myForm.setNombre_pages(nb_pages);

			/* INDEX COUNT */

			if (indexNew == 1) {
				index = 0;
				myForm.setPage_new(0);
			}

			if (index == 0) {
				index = 1;
			}
			if (indexD == 0) {
				indexD = 1;
			}
			if (indexF == 0) {
				indexF = (nbAffParPage * (nb_pages - 1)) + 1;
			}
			if (index == 1) {
				indexMM = 0;
				indexM = 0;
			}
			if (index == nbAffParPage + 1) {
				indexMM = 0;
				indexM = 1;
			}
			if (nb_pages == 1) {
				indexP = 0;
				indexPP = 0;
			}
			if (nb_pages == nbAffParPage + 1) {
				indexP = nbAffParPage + 1;
				indexPP = 0;
			}
			if (nb_pages > 1) {
				indexP = index + nbAffParPage;
				if (index >= nbAffParPage) {
					indexM = index - nbAffParPage;
				}
			}
			if (nb_pages > 2) {
				indexPP = index + 2 * nbAffParPage;
				if (index >= 2 * nbAffParPage) {
					indexMM = index - 2 * nbAffParPage;
				}
			}

			if (index > nb_msg) {
				--index;
				indexP = 0;
				indexPP = 0;
			} else {
				if (index > (nb_msg - nbAffParPage)) {
					indexP = 0;
					indexPP = 0;
				} else {
					if (index > (nb_msg - 2 * nbAffParPage)) {
						indexPP = 0;
					}
				}
			}

			myForm.setPage_indexD(indexD);
			myForm.setPage_indexMM(indexMM);
			myForm.setPage_AffIndexMM(indexMM / nbAffParPage + 1);
			myForm.setPage_indexM(indexM);
			myForm.setPage_AffIndexM(indexM / nbAffParPage + 1);
			myForm.setPage_index(index);
			myForm.setPage_AffIndex(index / nbAffParPage + 1);
			myForm.setPage_indexP(indexP);
			myForm.setPage_AffIndexP(indexP / nbAffParPage + 1);
			myForm.setPage_indexPP(indexPP);
			myForm.setPage_AffIndexPP(indexPP / nbAffParPage + 1);
			myForm.setPage_indexF(indexF);

			myForm.setPage_courante((index / nbAffParPage) + 1);

			if (action.equals("received")) {
				listeMessages = MessageReceivedData.findAllMessagesReceivedByObject(theObject, MessageReceived.MESSAGE_RECEIVED_STATES.INBOX, displayNabcast, index - 1, nbAffParPage);
				forward = "received";
			}

			if (action.equals("sent")) {
				listeMessages = MessageSentData.findAllSentMessagesByObjectFromUser(user, index - 1, nbAffParPage);
				forward = "sent";
			}

			if (action.equals("archived")) {
				listeMessages = MessageReceivedData.findAllMessagesReceivedByObject(theObject, MessageReceived.MESSAGE_RECEIVED_STATES.ARCHIVED, displayNabcast, index - 1, nbAffParPage);
				forward = "archived";
			}
		}

		// utilisateur sans objet
		if ((theObject == null) && action.equals("sent")) {

			if (selectChoice.equals("delete_msg") && (checkListMsg != null)) {
				for (int i = 0; i < checkListMsg.length; i++) {

					final String clef = checkListMsg[i];
					final Message theMessage = Factories.MESSAGE.find(Long.parseLong(clef));
					if ((theMessage != null) && (theMessenger != null)) {
						// si le message est différé
						theMessenger.getMessageSent().remove(theMessage);

						if (theMessage.getTimeOfDelivery().after(Calendar.getInstance())) {
							deleteReceivedMessage(theMessage);
							theMessage.delete();
						}

					}
				}
			}

			nb_msg = 0;
			try {
				nb_msg = (int) MessageSentImpl.countSentMessages(Factories.MESSENGER.getByUser(user), displayNabcast);
			} catch (final IllegalArgumentException e) {
				MyMessagesListAction.LOGGER.fatal(e, e);
			} catch (final SQLException e) {
				MyMessagesListAction.LOGGER.fatal(e, e);
			}
			/* INDEX GENERATOR */
			int nbAffParPage = myForm.getNbAffParPage();
			if (nbAffParPage == 0) {
				nbAffParPage = MyMessagesListAction.DEFAULT_NB_AFFICHE;
			}
			/* PAGE COUNT */
			int nb_pages = 1;
			if (nb_msg > nbAffParPage) {
				nb_pages = nb_msg / nbAffParPage;
				final float nb_pages_tmp = (float) nb_msg / nbAffParPage;
				if (nb_pages_tmp > (int) nb_pages_tmp) {
					nb_pages++;
				}
			}
			myForm.setNombre_pages(nb_pages);

			/* INDEX COUNT */

			if (indexNew == 1) {
				index = 0;
				myForm.setPage_new(0);
			}

			if (index == 0) {
				index = 1;
			}
			if (indexD == 0) {
				indexD = 1;
			}
			if (indexF == 0) {
				indexF = (nbAffParPage * (nb_pages - 1)) + 1;
			}
			if (index == 1) {
				indexMM = 0;
				indexM = 0;
			}
			if (index == nbAffParPage + 1) {
				indexMM = 0;
				indexM = 1;
			}
			if (nb_pages == 1) {
				indexP = 0;
				indexPP = 0;
			}
			if (nb_pages == nbAffParPage + 1) {
				indexP = nbAffParPage + 1;
				indexPP = 0;
			}
			if (nb_pages > 1) {
				indexP = index + nbAffParPage;
				if (index >= nbAffParPage) {
					indexM = index - nbAffParPage;
				}
			}
			if (nb_pages > 2) {
				indexPP = index + 2 * nbAffParPage;
				if (index >= 2 * nbAffParPage) {
					indexMM = index - 2 * nbAffParPage;
				}
			}

			if (index > nb_msg) {
				--index;
				indexP = 0;
				indexPP = 0;
			} else {
				if (index > (nb_msg - nbAffParPage)) {
					indexP = 0;
					indexPP = 0;
				} else {
					if (index > (nb_msg - 2 * nbAffParPage)) {
						indexPP = 0;
					}
				}
			}

			myForm.setPage_indexD(indexD);
			myForm.setPage_indexMM(indexMM);
			myForm.setPage_AffIndexMM(indexMM / nbAffParPage + 1);
			myForm.setPage_indexM(indexM);
			myForm.setPage_AffIndexM(indexM / nbAffParPage + 1);
			myForm.setPage_index(index);
			myForm.setPage_AffIndex(index / nbAffParPage + 1);
			myForm.setPage_indexP(indexP);
			myForm.setPage_AffIndexP(indexP / nbAffParPage + 1);
			myForm.setPage_indexPP(indexPP);
			myForm.setPage_AffIndexPP(indexPP / nbAffParPage + 1);
			myForm.setPage_indexF(indexF);

			myForm.setPage_courante((index / nbAffParPage) + 1);

			listeMessages = MessageSentData.findAllSentMessagesByObjectFromUser(user, index - 1, nbAffParPage);
			forward = "sent";

		}

		myForm.setErrorMsg(errorMsg);
		myForm.setNombre_profils(nb_msg);
		myForm.setIndex(index);
		myForm.setLangUser(Long.toString(lang.getId()));
		myForm.setUserId(user.getId());
		myForm.setListeMessages(listeMessages);
		final Date today = new Date();
		myForm.setDateToday(String.valueOf(today.getTime() / 1000));

		return mapping.findForward(forward);
	}

	/**
	 * Deletes a message from the message received
	 * 
	 * @param inMessage
	 */
	private void deleteReceivedMessage(Message inMessage) {
		final MessageReceived theMessageReceived = Factories.MESSAGE_RECEIVED.findMessageReceivedByMessageId(inMessage.getId());
		if (theMessageReceived != null) {
			final Messenger theRecipient = theMessageReceived.getRecipient();

			if (theRecipient != null) {
				theRecipient.getMessageReceived().remove(inMessage);
			} else {
				MyMessagesListAction.LOGGER.fatal("The message received with this id" + inMessage.getId() + " cannot be removed");
			}
		} else {
			MyMessagesListAction.LOGGER.fatal("The message received with this id" + inMessage.getId() + " cannot be removed");
		}
	}

}
