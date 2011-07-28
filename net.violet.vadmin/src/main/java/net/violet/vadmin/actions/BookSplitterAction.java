package net.violet.vadmin.actions;


public class BookSplitterAction{
//
//	private static final String STREAM = "stream-";
//	private static final String STREAM0 = BookSplitterAction.STREAM + "0" + MimeType.MIME_TYPES.A_MPEG.getExtension();
//	private static final String SLASH = "/";
//
//	@Override
//	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//
//		// initialisation des objets
//		final BookSplitterForm myForm = (BookSplitterForm) form;
//		final String[] nbBytes = myForm.getNbBytes();
//		final String path2Directory = myForm.getFile() + BookSplitterAction.SLASH;
//		final ActionErrors errors = new ActionErrors();
//		final byte[] theFile = FilesManagerFactory.FILE_MANAGER.readTmpFile(myForm.getFileName());
//		final int theLength = theFile.length;
//
//		if (nbBytes.length > 0) {
//			final Files theFiles = FilesManagerFactory.getHadoopManager().postAudio(theFile, false, null, Files.CATEGORIES.STREAMING,MimeType.FILE.MP3, false, false, null, null, path2Directory + BookSplitterAction.STREAM0);
//			if (theFiles == null) {
//				errors.add("fileError", new ActionMessage("errors.uploadStream", "Could not write the file"));
//			}
//
//		}
//
//		if (errors.isEmpty()) {
//			for (final String anAmountOfBytes : nbBytes) {
//				final int theOffset = ConvertTools.atoi(anAmountOfBytes);
//				final StringBuilder path2CurrentFile = new StringBuilder(path2Directory);
//				path2CurrentFile.append(BookSplitterAction.STREAM);
//				path2CurrentFile.append(anAmountOfBytes);
//				path2CurrentFile.append(MimeType.FILE.MP3.getExtension());
//
//				// Ecrire le fichier sur theFileOutPutStream partir de l'offset
//				// offset.
//				final byte[] theBuffer = new byte[theLength - theOffset];
//				System.arraycopy(theFile, theOffset, theBuffer, 0, theLength - theOffset);
//				final Files theFiles = FilesManagerFactory.getHadoopManager().postAudio(theBuffer, false, null, Files.CATEGORIES.STREAMING, MimeType.FILE.MP3, false, false, null, null, path2CurrentFile.toString());
//				if (theFiles == null) {
//					errors.add("fileError", new ActionMessage("errors.uploadStream", "Could not write the file"));
//				}
//			}
//		}
//
//		if (errors.isEmpty() && (nbBytes.length != 0)) {
//			FilesManagerFactory.FILE_MANAGER.deleteTmpFile(myForm.getFileName());
//			errors.add("fileError", new ActionMessage("errors.uploadStream", "Hurray !! Everything went smoothly !!"));
//		}
//
//		saveErrors(request, errors);
//
//		return mapping.getInputForward();
//	}
}
