package net.violet.vadmin.forms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

@Deprecated //TODO: Fix me
public class BookSplitterForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private final List<Map<String, String>> listPoint = new LinkedList<Map<String, String>>();
	private long bookNumber;
	private String fileName;
	private String[] nbBytes = {};
	private long mBitRate = 32;
	private FormFile musicFile;

	/**
	 * Used to set the encoding for the form this fixes problems with accents
	 * when text's going from the form (JSP) to the form object (JAVA) *
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		if (this.listPoint.isEmpty()) {
			final Map<String, String> point = new HashMap<String, String>();
			point.put("sec", "00:00.00");
			point.put("nbByte", "0");
			this.listPoint.add(point);
		}

		super.reset(mapping, request);
	}

	@Override
	public ActionErrors validate(@SuppressWarnings("unused") ActionMapping mapping,	@SuppressWarnings("unused") HttpServletRequest request) {
		final ActionErrors errors = new ActionErrors();
//
//		final String bookNumberString = String.valueOf(this.bookNumber);
//
//		if ((this.nbBytes == null) || (this.nbBytes.length == 0)) {
//			if (this.bookNumber == 0) {
//				errors.add("fileError", new ActionMessage("errors.uploadStream", "Please provide us with a Book number"));
//			} else {
//				if (FilesManagerFactory.FILE_MANAGER.fileExists(bookNumberString, Files.CATEGORIES.STREAMING)) {
//					errors.add("fileError", new ActionMessage("errors.uploadStream", "Book number already exists"));
//				} else if ((this.musicFile != null) && !StringShop.EMPTY_STRING.equals(this.musicFile.getFileName())) {
//					if (!FilesManagerFactory.FILE_MANAGER.mkdir(bookNumberString, Files.CATEGORIES.STREAMING)) {
//						errors.add("fileError", new ActionMessage("errors.uploadStream", "Folder cannot be created"));
//					} else {
//						try {
//							final byte[] theBytes = LibFile.toMp3(this.musicFile.getFileData(), MimeType.FILE.MP3, String.valueOf(this.mBitRate));
//							if ((theBytes == null) || (theBytes.length <= 0)) {
//								errors.add("fileError", new ActionMessage("errors.uploadStream", "Error file cannot be used as a workable MP3"));
//							} else {
//								this.fileName = FilesManagerFactory.FILE_MANAGER.writeTmpFile(theBytes).getName();
//							}
//						} catch (final FileNotFoundException e) {
//							errors.add("fileError", new ActionMessage("errors.uploadStream", "File Not Found"));
//						} catch (final IOException e) {
//							errors.add("fileError", new ActionMessage("errors.uploadStream", "I/O Error"));
//						} finally {
//							this.musicFile.destroy();
//						}
//					}
//				} else {
//					errors.add("fileError", new ActionMessage("errors.uploadStream", "Please provide us with a file."));
//				}
//			}
//
//			if (!errors.isEmpty()) {
//				this.musicFile = null;
//				FilesManagerFactory.FILE_MANAGER.deleteFile(bookNumberString, Files.CATEGORIES.STREAMING);
//			}
//
//		}
//
		return errors;
	}

	public long getBitRate() {
		return this.mBitRate;
	}

	public void setBitRate(long bitRate) {
		this.mBitRate = bitRate;
	}

	public long getFile() {
		return this.bookNumber;
	}

	public void setFile(long File) {
		this.bookNumber = File;
	}

	/**
	 * @return the listPoint
	 */
	public List<Map<String, String>> getListPoint() {
		return this.listPoint;
	}

	/**
	 * @return the nbBytes
	 */
	public String[] getNbBytes() {
		return this.nbBytes;
	}

	/**
	 * @param nbBytes the nbBytes to set
	 */
	public void setNbBytes(String[] nbBytes) {
		this.nbBytes = nbBytes;
	}

	/**
	 * @return the musicFile
	 */
	public FormFile getMusicFile() {
		return this.musicFile;
	}

	/**
	 * @param musicFile the musicFile to set
	 */
	public void setMusicFile(FormFile musicFile) {
		this.musicFile = musicFile;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
