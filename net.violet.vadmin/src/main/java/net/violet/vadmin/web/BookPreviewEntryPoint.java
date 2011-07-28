package net.violet.vadmin.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe pour itmode.jsp. l'objet est en mode interactif.
 */
@Deprecated
//TODO: Fix me
public class BookPreviewEntryPoint extends HttpServlet {

	// private static final String IP_NABDEV = "192.168.1.11";
	//private static final String IP_NAB = "192.168";

	/**
	 * Point d'entrée de la servlet.
	 */
	@Override
	public void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) {

//		final String offset = inRequest.getParameter("offset");
//		final String fileName = inRequest.getParameter("fileName");
//		BookPreviewEntryPoint.LOGGER.info("-------> offset = " + offset + " & fileName = " + fileName);
//
//		if (SessionTools.getIP(inRequest).startsWith(BookPreviewEntryPoint.IP_NAB) || "127.0.0.1".equals(SessionTools.getIP(inRequest))) {
//			// 1. vérifier que filenumber est un numéro.
//			final int theOffSet = ConvertTools.atoi(offset);
//
//			if ((fileName != null) && !StringShop.EMPTY_STRING.equals(fileName)) {
//				final byte[] theBuffer = FilesManagerFactory.FILE_MANAGER.readTmpFile(fileName);
//				ServletOutputStream theOutPutStream = null;
//				final int length = theBuffer.length - theOffSet;
//				inResponse.setContentType(MimeType.MIME_TYPES.A_MPEG.getLabel());
//				// partir de l'offset offset.
//
//				inResponse.setContentLength(length);
//
//				// Ecrire le fichier sur inResponse
//				try {
//					theOutPutStream = inResponse.getOutputStream();
//					AbstractFilesManager.writeByteArray2OutputStream(theOutPutStream, theBuffer, theOffSet, length);
//				} catch (final IOException e) {
//					throw e;
//				} finally {
//					if (theOutPutStream != null) {
//						theOutPutStream.close();
//					}
//				}
//			}
//		}
	}
}
