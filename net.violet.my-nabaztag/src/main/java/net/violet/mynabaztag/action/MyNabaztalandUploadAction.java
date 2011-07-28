package net.violet.mynabaztag.action;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyNabaztalandUploadForm;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.NabcastImpl;
import net.violet.platform.datamodel.NabcastValImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.NabcastValData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.ConvertTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;
import net.violet.vlisp.services.NabcastServices;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public final class MyNabaztalandUploadAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyNabaztalandUploadAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);

		final User user = SessionTools.getUserFromSession(request);
		final Lang lang = SessionTools.getLangFromSession(session, request);

		/**
		 * Checks if the user really exists
		 */
		if (user == null) {
			return mapping.findForward("login");
		}

		final MyNabaztalandUploadForm myForm = (MyNabaztalandUploadForm) form;

		/**
		 * Checks if the NabcastImpl really exists
		 */
		final Nabcast theNabcast = NabcastImpl.find(myForm.getIdNabcast());
		if (theNabcast == null) {
			return mapping.findForward("home");
		}

		/**
		 * Checks if the user is the nabcast's author
		 */
		if (user.getId().longValue() != theNabcast.getAuthor().getId().longValue()) {
			return mapping.findForward("home");
		}

		int heure_delay = 0;
		int minute_delay = 0;
		try {
			heure_delay = ConvertTools.atoi(myForm.getHeure_delay());
			minute_delay = ConvertTools.atoi(myForm.getMinute_delay());
		} catch (final NumberFormatException e) {
			MyNabaztalandUploadAction.LOGGER.debug("MyNabaztalandUploadAction" + e);
			MyNabaztalandUploadAction.LOGGER.fatal(e, e);
		}

		// INFOS DE SESSION
		myForm.setUser_id(user.getId().intValue());
		myForm.setLanguser(Long.toString(lang.getId()));

		// Partie Listes
		myForm.setNabcastData(new NabcastData(theNabcast, null));

		final int nbrAbonn = theNabcast.getSubscribers().size(); // NabcastServices . getNbrAbonnbyNabcast ( theNabcast .getId(). intValue () );
		myForm.setNbrAbonn(nbrAbonn);

		// Partie Upload
		final List<MusicData> mp3List = MusicData.findAllPersoByUser(user);
		myForm.setMp3List(mp3List);
		myForm.setSong_end(45);
		myForm.setSong_start(0);

		// les modes
		final int mode = myForm.getMode();
		if (mode == 0) {
			myForm.setNabcastValSent(NabcastValData.findSongsNabcast(theNabcast, 0, 0, 0, 0, user));

			myForm.setNabcastValToSend(NabcastValData.findSongsNabcast(theNabcast, 0, 0, 1, 0, user));
		} else if (mode == 1) { // Upload d'un mp3
			final FormFile song_path = myForm.getSong_path();
			final String song_title = myForm.getSong_title();

			final long idMp3 = MyManageMp3Action.createMP3(song_title, song_path, user, 0);

			myForm.setMode(0);
			myForm.setIdNabcast(theNabcast.getId().intValue());
			if (idMp3 < 0) {
				return mapping.findForward("errorload");
			}

			myForm.setIdMp3((int) idMp3);
			myForm.setMode(4);
			return mapping.getInputForward();

		} else if (mode == 2) { // ajout d'un son dans la liste des sons
			final Music theMusic = Factories.MUSIC.find(myForm.getIdMp3());
			String title = StringShop.EMPTY_STRING;
			final String song_title = myForm.getSong_title();

			if ((song_title != null) && !song_title.equals(StringShop.EMPTY_STRING)) {
				title = song_title;
			} else {
				title = theMusic.getMusic_name();
			}

			NabcastValImpl theNabcastValCreate = null;
			String date_delay = myForm.getDate_delay();
			if ((date_delay == null) || date_delay.equals(StringShop.EMPTY_STRING)) {
				try {
					theNabcastValCreate = new NabcastValImpl(title, theMusic.getId(), theNabcast, CCalendar.getCurrentTimeInSecond());
				} catch (final SQLException e) {
					MyNabaztalandUploadAction.LOGGER.fatal(e, e);
				}
			} else {
				final Calendar cal2 = Calendar.getInstance();
				final String[] tab = date_delay.split("/");
				final int date2 = Integer.parseInt(tab[0]);
				final int month2 = Integer.parseInt(tab[1]) - 1;
				final int year2 = Integer.parseInt(tab[2]);
				cal2.set(year2, month2, date2, heure_delay, minute_delay);

				final long prog = cal2.getTimeInMillis() / 1000;

				try {
					theNabcastValCreate = new NabcastValImpl(title, theMusic.getId(), theNabcast, (int) prog);
				} catch (final SQLException e) {
					MyNabaztalandUploadAction.LOGGER.fatal(e, e);
				}
			}

			if (theNabcastValCreate != null) {
				// update count post under one week
				NabcastServices.nbrNabcastInOneWeek(theNabcast.getId().intValue(), myForm.getNabcastData().getNabcast_categ());

				myForm.setMode(0);
				myForm.setIdNabcast(theNabcast.getId().intValue());

				myForm.setNabcastValSent(NabcastValData.findSongsNabcast(theNabcast, 0, 0, 0, 0, user));

				myForm.setNabcastValToSend(NabcastValData.findSongsNabcast(theNabcast, 0, 0, 1, 0, user));

				/* bizarre a regardé!! */
				/* ajout cascade */
				date_delay = myForm.getDate_delay();
				CCalendar timenext_diff;
				if ((date_delay == null) || date_delay.equals(StringShop.EMPTY_STRING)) {
					timenext_diff = null;
				} else {
					final String[] tab = date_delay.split("/");
					final int date = Integer.parseInt(tab[0]);
					final int month = Integer.parseInt(tab[1]) - 1;
					final int year = Integer.parseInt(tab[2]);

					timenext_diff = NabcastServices.nexttimediffFromCalendar(month, date, year, Integer.toString(heure_delay), Integer.toString(minute_delay), user);
				}

				NabcastServices.sendNabcastToMySubscriber(theNabcast.getId().intValue(), timenext_diff, theMusic.getId().intValue(), user.getId().intValue(), title, myForm.getNabcastData(), heure_delay, minute_delay, theNabcastValCreate.getId());
			}

		} // fin d'ajout d'un son dans la liste de diffusion

		if (mode == 3) { // supprsession d'un son de la liste des sons
			final long idNabcastVal = myForm.getIdNabcastVal();
			final boolean ok = NabcastServices.isNabcastAuthor(user.getId(), theNabcast.getId());

			if (ok) {
				if (NabcastValImpl.findProgrammedNabcast(idNabcastVal) != null) {
					NabcastServices.deleteMsgNabCastInFuture(idNabcastVal); // supprime tout les messages programmé chez les users
				}
				NabcastValImpl.deleteNabcastVal(idNabcastVal);
			}
			myForm.setMode(0);
			myForm.setIdNabcast(theNabcast.getId().intValue());

			/* Ajout du temps relatif */
			myForm.setNabcastValSent(NabcastValData.findSongsNabcast(theNabcast, 0, 0, 0, 0, user));

			/* Ajout du temps relatif pour l'heure */
			myForm.setNabcastValToSend(NabcastValData.findSongsNabcast(theNabcast, 0, 0, 1, 1, user));

		} // fin de la suppression de la liste de diffusion

		return mapping.getInputForward();
	}
}
