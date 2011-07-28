package net.violet.platform.util.concurrent.units;

/**
 * The thread safety of the mState member relies on the use of
 * {@link DownloadProcessUnit} - it is not Thread safe on its own
 */
public class DownloadProcessUnit extends AbstractCrawlerProcessUnit<String> {

	public DownloadProcessUnit(String inIdXML, String inTitle, String link, Integer inTTL) {
		super(link, inTitle, inIdXML, inTitle, link, inTTL);
	}
}
