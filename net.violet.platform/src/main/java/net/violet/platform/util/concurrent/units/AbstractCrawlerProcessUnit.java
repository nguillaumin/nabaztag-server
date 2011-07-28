package net.violet.platform.util.concurrent.units;

import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.common.utils.concurrent.units.AbstractProcessUnit;
import net.violet.platform.datamodel.Files;

/**
 * Interface for the ProcessUnits used all over the project
 */
public abstract class AbstractCrawlerProcessUnit<C> extends AbstractProcessUnit<String, C, Files> {

	private final String id_xml;
	private final String title;
	private final String link;
	private final Integer mTTL;
	private ThreadWatcher mThreadWatcher;

	/**
	 * @param id_xml
	 * @param title
	 * @param link
	 * @param publishedDate
	 * @param threadWatcher
	 * @param inContent
	 */
	public AbstractCrawlerProcessUnit(String inMaterial, C inCondition, String id_xml, String title, String link, Integer inTTL) {
		super(inMaterial, inCondition, null);
		this.id_xml = id_xml;
		this.title = title;
		this.link = link;
		this.mTTL = inTTL;
	}

	/**
	 * @return the id_xml
	 */
	public String getId_xml() {
		return this.id_xml;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return this.link;
	}

	public void setThreadWatcher(ThreadWatcher inWatcher) {
		this.mThreadWatcher = inWatcher;
	}

	@Override
	protected ThreadWatcher getThreadWatcher() {
		return this.mThreadWatcher;
	}

	@Override
	public Files getResult() {
		return super.getResult();
	}

	public Integer getTTL() {
		return this.mTTL;
	}
}
