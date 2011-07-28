package net.violet.mynabaztag.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.violet.mynabaztag.form.MyTerrierEditRabbitImageForm;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.dataobjects.AnnuData;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.ObjectProfileData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class MyTerrierEditRabbitImageAction extends ActionWithLog {

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final UserData user = UserData.getData(SessionTools.getUserFromSession(request));
		final VObjectData object = VObjectData.getData(SessionTools.getRabbitFromSession(request.getSession(true)));

		/**
		 * check if the user really exists
		 */
		if ((user == null) || (object == null) || !user.isValid() || !object.isValid()) {
			return mapping.findForward("login");
		}

		final MyTerrierEditRabbitImageForm myForm = (MyTerrierEditRabbitImageForm) form;

		if (myForm.getModify() <= 0) {
			myForm.setUserImage((int) user.getUser_image());
			return mapping.findForward("edit");
		}
		final FormFile imageFile = myForm.getImageFile();

		// Le fichier doit tre infrieur 10Mo
		if (imageFile.getFileSize() > 10485760) {
			myForm.setModify(-1);
			return mapping.findForward("edit");
		}
		// Redimensionnement de l'image
		try {

			// On update dans le nouveau modèle
			final FilesData theNewPicture = FilesData.postLibraryItem(MimeType.MIME_TYPES.JPEG.getLabel(), imageFile.getInputStream());
			if ((theNewPicture != null) && theNewPicture.isValid()) {
				user.getReference().setImage(1);
				user.getReference().setIsGood(0);
				AnnuData theAnnuUser = user.getAnnu();
				if ((theAnnuUser == null) || !theAnnuUser.isValid()) {
					theAnnuUser = AnnuData.create(user, StringShop.EMPTY_STRING, user.getReference().getLang());
				}

				final FilesData oldFilePicture = theAnnuUser.getPictureFile();
				// détruit l'ancienne image
				if ((oldFilePicture != null) && oldFilePicture.isValid()) {
					oldFilePicture.scheduleDeletion();
				}

				theAnnuUser.setPictureFile(theNewPicture);

				ObjectProfileData objectProfile = object.getProfile();
				if ((objectProfile == null) || !objectProfile.isValid()) {
					objectProfile = ObjectProfileData.createObjectProfile(object);
				}
				objectProfile.setPicture(theNewPicture);
			}

		} catch (final Exception e) {
			myForm.setModify(-1);
			return mapping.findForward("edit");
		}
		return mapping.findForward("modify_ok");
	}
}
