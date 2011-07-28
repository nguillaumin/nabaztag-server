package net.violet.mynabaztag.action;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.content.converters.ContentType;
import net.violet.mynabaztag.form.MyNathanCreateForm;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.FilesData;
import net.violet.platform.dataobjects.NathanVersionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.files.FilesManager;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.interactif.config.NathanPlayerConfig;
import net.violet.platform.struts.DispatchActionForLoggedUserWithObject;
import net.violet.platform.util.Constantes;
import net.violet.platform.util.MyConstantes;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.upload.FormFile;

public class MyNathanCreateAction extends DispatchActionForLoggedUserWithObject {

	private static final String FILE_DEST = "stream-";

	private static final Logger LOGGER = Logger.getLogger(MyNathanCreateAction.class);

	private static final Map<String, Map<TmpFile, ContentType>> CACHE = new HashMap<String, Map<TmpFile, ContentType>>();

	private static final Map<String, TimerTask> CACHE_TIMER = new HashMap<String, TimerTask>();

	private static final Timer TIMER = new Timer(true);

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
		final User user = SessionTools.getUserFromSession(request);
		final MyNathanCreateForm myForm = (MyNathanCreateForm) form;

		myForm.setMp3Id(user.getId() + StringShop.UNDERSCORE + System.currentTimeMillis());
		myForm.setServerPath(MyConstantes.RED5_URL_SERVER);

		final Long isbn = myForm.getIsbn();
		final long applet_id = myForm.getAppletId();
		final Application mService = ApplicationImpl.findByAppletId(applet_id, isbn);
		if ((myForm.getUrl() == null) || myForm.getUrl().equals("")) {
			myForm.setUrl(mService.getShortcut());
		}

		if (myForm.getIsbn() != null) {
			myForm.setDicoRoot(NathanPlayerConfig.BOOKSROOT.get(myForm.getIsbn()));
		} else {
			myForm.setDicoRoot(StringShop.EMPTY_STRING);
		}

		return mapping.getInputForward();
	}

	// Adds a new mp3

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MyNathanCreateForm myForm = (MyNathanCreateForm) form;
		TmpFile theTmpFile = null;

		final FormFile upLoadedFile = myForm.getMp3File();
		final String newId = myForm.getMp3Id();
		InputStream theStream = null;
		final User theUser = SessionTools.getUserFromSession(request);
		final Map<TmpFile, ContentType> theMap = getCacheItem(theUser.getId(), myForm.getIsbn());
		try {
			// First option : the user uploads a file, we have to save it
			if (upLoadedFile != null) {
				theTmpFile = FilesManager.TMP_MANAGER.new TmpFile(theStream = upLoadedFile.getInputStream());
				theMap.put(theTmpFile, ContentType.MP3);
				upLoadedFile.destroy();
			} else {// Second option : the user records his/her file, it's
				theTmpFile = FilesManager.TMP_MANAGER.new TmpFile(theStream = new URL(FilesData.VOCAL_RECORDER + newId + StringShop.FLV_EXT).openStream());
				theMap.put(theTmpFile, ContentType.FLV);
			}
		} catch (final MalformedURLException e) {
			MyNathanCreateAction.LOGGER.fatal(e, e);
		} catch (final IOException e) {
			MyNathanCreateAction.LOGGER.fatal(e, e);
		} finally {
			if (theStream != null) {
				try {
					theStream.close();
				} catch (IOException e) {
					LOGGER.fatal(e, e);
				}
			}
			reschedule(theUser.getId(), myForm.getIsbn(), false);
		}

		if ((theTmpFile == null) || !theTmpFile.exists() || (theTmpFile.length() <= 0)) {
			myForm.setError(1);
		} else {
			final String[] mp3IdList = myForm.getMp3IdList();
			final String[] newMp3IdList = new String[mp3IdList.length + 1];

			myForm.setError(0);
			System.arraycopy(mp3IdList, 0, newMp3IdList, 0, mp3IdList.length);

			newMp3IdList[mp3IdList.length] = theTmpFile.getName();
			myForm.setMp3IdList(newMp3IdList);
		}

		return load(mapping, form, request, response);
	}

	// This method is used to delete a fragment from the list

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MyNathanCreateForm myForm = (MyNathanCreateForm) form;
		final User theUser = SessionTools.getUserFromSession(request);

		try {

			final String mp3Id = myForm.getMp3Id();
			final String[] mp3IdList = myForm.getMp3IdList();

			final Map<TmpFile, ContentType> theMap = getCacheItem(theUser.getId(), myForm.getIsbn());

			final String[] newMp3IdList = new String[mp3IdList.length - 1];

			int count = 0;
			for (final String anId : mp3IdList) {
				if (!anId.equals(mp3Id) && (count < newMp3IdList.length)) {
					newMp3IdList[count++] = anId;
				}
			}
			myForm.setMp3IdList(newMp3IdList);

			final TmpFile file2Remove = FilesManager.TMP_MANAGER.new TmpFile(mp3Id);
			theMap.remove(file2Remove);
			file2Remove.delete();
		} catch (IOException e) {
			LOGGER.fatal(e, e);
		} finally {
			reschedule(theUser.getId(), myForm.getIsbn(), false);
		}
		return load(mapping, form, request, response);
	}

	// All mp3 are loaded, we create the version

	public ActionForward finish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final MyNathanCreateForm myForm = (MyNathanCreateForm) form;

		final HttpSession session = request.getSession(true);
		final VObject object = SessionTools.getRabbitFromSession(session);
		final Long isbn = myForm.getIsbn();

		try {
			final Long appletId = myForm.getAppletId();

			if (myForm.getMp3IdList().length <= 0) {
				myForm.setError(2);
				return load(mapping, form, request, response);
			}

			final NathanVersionData theVersion = NathanVersionData.createNewVersion(VObjectData.getData(object), isbn.toString());

			boolean valid = false;
			try {
				valid = MyNathanCreateAction.cutNathanFiles(getCacheItem(object.getOwner().getId(), isbn), isbn, Factories.NATHAN_VERSION.find(theVersion.getId()));
			} catch (final Exception e) {
				MyNathanCreateAction.LOGGER.fatal(e, e);
			}

			// creation failed
			if (!valid) {
				NathanVersionData.deleteVersion(theVersion.getId());
				myForm.setError(3);
				return load(mapping, form, request, response);
			}

			final ActionRedirect theAction = new ActionRedirect(mapping.findForward("goToConfig"));
			theAction.addParameter("versionId", theVersion.getId());
			theAction.addParameter("appletId", appletId);
			theAction.addParameter("isbn", isbn);
			return theAction;
		} finally {
			deleteAll(object.getOwner().getId(), isbn);
			reschedule(object.getOwner().getId(), isbn, true);
		}
	}

	private static boolean cutNathanFiles(Map<TmpFile, ContentType> inInputFiles, Long isbn, NathanVersion inVersion) {

		final Long officialVersionsSize = Long.parseLong(NathanPlayerConfig.LIST_PATH_STREAM_OFFICIAL_VERSION.get(isbn).get(NathanPlayerConfig.VERSION_SIZE));
		final Long version_id_officielle = Long.parseLong(NathanPlayerConfig.LIST_PATH_STREAM_OFFICIAL_VERSION.get(isbn).get(NathanPlayerConfig.VERSION_ID));
		final NathanPlayerConfig theIAPlayerNathanConfig = new NathanPlayerConfig(isbn, version_id_officielle);
		final String versionIdUser = inVersion.getId().toString();

		final LinkedHashMap<Files, Integer> theFiles = FilesManagerFactory.FILE_MANAGER.joinAndSplit(inInputFiles, theIAPlayerNathanConfig.getIndexFilesByVoice(0), officialVersionsSize, Files.CATEGORIES.NATHAN, ContentType.MP3_32, MimeType.MIME_TYPES.A_MPEG, versionIdUser + StringShop.SLASH + MyNathanCreateAction.FILE_DEST, 20);

		if (theFiles != null) {

			for (final Iterator<Entry<Files, Integer>> theIterator = theFiles.entrySet().iterator(); theIterator.hasNext();) {
				final Entry<Files, Integer> theElement = theIterator.next();

				if (theIterator.hasNext())
					Factories.NATHAN_MP3.createNewNathanMp3(inVersion, theElement.getKey(), theElement.getValue());
				else
					inVersion.setPreview(theElement.getKey().getId());

			}

		} else {
			return false;
		}

		return true;
	}

	private static Map<TmpFile, ContentType> getCacheItem(long inUserId, long inISBN) {
		final String key = inUserId + "_" + inISBN;
		Map<TmpFile, ContentType> theMap = CACHE.get(key);

		if (theMap == null) {
			CACHE.put(key, (theMap = new LinkedHashMap<TmpFile, ContentType>()));
		}

		return theMap;

	}

	private static final void reschedule(final long inUserId, final long inISBN, boolean inCancel) {
		final String key = inUserId + "_" + inISBN;
		TimerTask theTask = CACHE_TIMER.remove(key);

		if (theTask != null)
			theTask.cancel();

		if (!inCancel) {
			CACHE_TIMER.put(key, (theTask = new TimerTask() {

				@Override
				public void run() {
					deleteAll(inUserId, inISBN);

				}
			}));

			TIMER.schedule(theTask, Constantes.TIMEOUT_ONEMINUTE * 5);
			TIMER.purge();
		}

	}

	private static final void deleteAll(long inUserId, long inISBN) {
		final String key = inUserId + "_" + inISBN;
		final Map<TmpFile, ContentType> theMap = CACHE.remove(key);

		if (theMap != null) {
			for (TmpFile aFile : theMap.keySet()) {
				aFile.delete();
			}
		}
	}

}
