package net.violet.platform.feeds.processors;

import java.util.Collections;
import java.util.regex.Pattern;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.content.converters.ContentType;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.feeds.FeedsTools;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.httpclient.DownloadTask;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * The DownloadingProcessor looks for enclosure url in the given SyndEntry and downloads it.
 */
public class DownloadingProcessor implements EntryProcessor {

	private static final Pattern FAAD_MATCHER = Pattern.compile(".*\\.m(4a|p4)$");

	private final boolean probesGroup;

	public DownloadingProcessor(boolean probesGroup) {
		this.probesGroup = probesGroup;
	}

	public ProcessedEntry processEntry(SyndEntry entry) throws ProcessingException {

		final String enclosureUrl = FeedsTools.extractEnclosureUrl(entry);

		if (enclosureUrl == null) {
			throw new ProcessingException("No available enclosure found");
		}

		TmpFile file = null;
		try {
			file = new DownloadTask(this.probesGroup, enclosureUrl).execute();

			final ContentType theFileType;
			if (DownloadingProcessor.FAAD_MATCHER.matcher(enclosureUrl).matches()) {
				theFileType = ContentType.AAC;
			} else {
				theFileType = ContentType.MP3;
			}

			final Files theFiles = FilesManagerFactory.FILE_MANAGER.post(file, theFileType, ContentType.MP3_32, Files.CATEGORIES.PODCAST, false, false, MimeType.MIME_TYPES.A_MPEG);
			return new ProcessedEntry(entry, Collections.singletonList(theFiles), enclosureUrl);
		} catch (final Exception e) {
			throw new ProcessingException(e);
		} finally {
			if (file != null) {
				file.delete();
			}
		}
	}
}
