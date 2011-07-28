package net.violet.mynabaztag.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.content.converters.ContentType;
import net.violet.mynabaztag.form.MyManageMp3Form;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.Tag;
import net.violet.platform.datamodel.TagImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.CategData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class MyManageMp3Action extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);

		final User theUser = SessionTools.getUserFromSession(request);
		final Lang lang = SessionTools.getLangFromSession(session, request);

		/**
		 * Check if the user really exists
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		// initialisation des objets
		final MyManageMp3Form myForm = (MyManageMp3Form) form;

		String forward = "upload_mp3";
		String message = StringShop.EMPTY_STRING;
		final String queFaire = myForm.getQueFaire();
		final String music_name = myForm.getMusicName();
		String cloudTag = myForm.getCloudTag();
		final String shareMp3 = myForm.getShareMp3();

		long idMp3 = 0L;
		final int categId = myForm.getCategId();

		if (queFaire.equals("add")) {
			forward = "mp3_reponse";
			int share = 0;
			if (shareMp3.equals("share_mp3") && (categId > 0)) {
				share = categId;
			}

			// enregistre le mp3
			idMp3 = createMP3(music_name, myForm.getMusicFile(), theUser, share);

			// on enregistre le nuage de tags
			if ((share > 0) && (idMp3 > 0) && !cloudTag.trim().equals(StringShop.EMPTY_STRING)) {
				cloudTag = cloudTag.replaceAll(",", ";");
				cloudTag = cloudTag.replaceAll("\n", ";");

				final StringTokenizer st_tag = new StringTokenizer(cloudTag, ";");
				while (st_tag.hasMoreTokens()) {
					try {
						new TagImpl(idMp3, st_tag.nextToken(), lang);
					} catch (SQLException e) {}
				}
			}

			if (idMp3 > 0) {
				message = "upload_OK";
			} else {
				message = "upload_error";
			}
		} else if (queFaire.equals("delete")) {
			forward = "mp3_reponse";
			idMp3 = myForm.getIdMp3();
			final Music theMusic = Factories.MUSIC.find(idMp3);

			if (theMusic != null) {
				if (theMusic.getOwner() != null && theMusic.delete()) {
					message = "delete_OK";
				} else {
					message = "delete_error";
				}

				// ajout de la suppresion des tags associes en cas de
				// suppression du mp3
				deleteTag(idMp3);
			}
		} else if (queFaire.equals("update")) {
			forward = "mp3_reponse";
			idMp3 = myForm.getIdMp3();
			int log = -1;

			if (idMp3 > 0) {
				int share = 0;
				if (shareMp3.equals("share_mp3") && (categId > 0)) {
					share = categId;
				}

				final Music theMusic = Factories.MUSIC.find(idMp3);

				if (theMusic != null) {
					theMusic.setMusicInfo(music_name, share, ObjectLangData.getDefaultObjectLanguage(lang.getIsoCode()).getReference());
					log = 1;
				} else
					log = 0;

				// on enregistre le nuage de tags
				if ((log > 0) && (share > 0) && (idMp3 > 0) && !cloudTag.trim().equals(StringShop.EMPTY_STRING)) {
					deleteTag(idMp3);

					cloudTag = cloudTag.replaceAll(",", ";");
					cloudTag = cloudTag.replaceAll("\n", ";");

					final StringTokenizer st_tag = new StringTokenizer(cloudTag, ";");
					while (st_tag.hasMoreTokens()) {
						try {
							new TagImpl(idMp3, st_tag.nextToken(), lang);
							log = 0;
						} catch (SQLException e) {
							log = -1;
						}
					}
				}

				if (share == 0) {
					// si pas de partage, on supprime les tags
					deleteTag(idMp3);
				}

				if (log < 0) {
					message = "update_error";
				} else {
					message = "update_OK";
				}
			}
		}
		// liste des catgories

		myForm.setListeCateg(CategData.findAllInLang(lang));
		myForm.setMessage(message);
		myForm.setIdMp3(idMp3);
		myForm.setCategId(categId);

		return mapping.findForward(forward);
	}

	private void deleteTag(long inId) {
		final List<Tag> tags = TagImpl.findByMp3(inId);
		for (final Tag tag : tags) {
			tag.delete();
		}
	}

	private static final Pattern AUDIO_MIME_PATTERN = Pattern.compile("^(audio\\/(mpeg|mp3|x-mpeg))");

	/**
	 * upload un fichier mp3 et renvois l'id de la music
	 * 
	 * @param mp3name : nom du fichier pour le mp3
	 * @param file : FormFile du fichier uploader, ne peut pas etre null
	 * @param iduser : id de l'utilisateur
	 * @param firstTime : dbut du temps o l'on veut couper le mp3
	 * @param time : temps du mp3 entre 5 45 secondes
	 * @param share : 1 pour partager sinon 0
	 * @return return -1 si erreur sinon id de la musique ( -3 si fichier trop
	 *         gros et -4 si fichier n'est pas un mp3)
	 */
	static long createMP3(String mp3name, FormFile file, User inUser, int share) {
		if (file == null) {
			return -1;
		}

		// if(!file.getContentType().equals("audio/mpeg")) return -4;
		if ((file.getContentType() == null) || !(AUDIO_MIME_PATTERN.matcher(file.getContentType()).matches())) {
			return -4;
		}

		if (file.getFileSize() > 10000000) {
			return -3;
		}

		if ((file.getFileName() != null) && !file.getFileName().trim().equals(StringShop.EMPTY_STRING)) {
			// v2
			try {
				final Files theFiles = FilesManagerFactory.FILE_MANAGER.post(file.getFileData(), ContentType.MP3, ContentType.MP3_32, Files.CATEGORIES.BROAD, true, true, MimeType.MIME_TYPES.A_MPEG);
				return (theFiles != null) ? MusicData.createMp3(FilesData.getData(theFiles), mp3name, inUser, share).getId() : 0L;
			} catch (final FileNotFoundException e) {} catch (final IOException e) {}
		}
		return -1;
	}
}
