package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Tag;
import net.violet.platform.datamodel.TagImpl;

public final class TagData extends RecordData<Tag> {

	private final int id_css;

	private TagData(Tag inTag, int inIdCss) {
		super(inTag);
		this.id_css = inIdCss;
	}

	private TagData(Tag inTag) {
		this(inTag, 0);
	}

	/**
	 * Returns (limit) TagDatas available for a LangImpl
	 * 
	 * @param inLang
	 * @param limit
	 * @return
	 */
	public static List<TagData> findByLang(Lang inLang, int limit) {
		return TagData.generateList(TagImpl.getWordCloud(limit, inLang), limit / 5);
	}

	/**
	 * Returns a list of TagDatas available for the given mp3
	 * 
	 * @param inIdMp3
	 * @return
	 */
	public static List<TagData> findByMp3(long inIdMp3) {
		return TagData.generateList(TagImpl.findByMp3(inIdMp3));
	}

	/**
	 * Generates a list of TagData with the given TagImpl list
	 * 
	 * @param inTags TagImpl list
	 * @return
	 */
	private static List<TagData> generateList(List<Tag> inTags, int palier) {
		final SortedMap<String, TagData> tagDataList = new TreeMap<String, TagData>();
		int i = 0;

		for (final Tag tempTag : inTags) {
			int idCss = 0;

			if (i < palier) {
				idCss = 4;
			} else if ((i >= palier) && (i < palier * 2)) {
				idCss = 3;
			} else if ((i >= palier * 2) && (i < palier * 3)) {
				idCss = 2;
			} else if ((i >= palier * 3) && (i < palier * 4)) {
				idCss = 1;
			}

			tagDataList.put(tempTag.getTag_word().trim(), new TagData(tempTag, idCss));
			i++;
		}

		return new ArrayList<TagData>(tagDataList.values());
	}

	/**
	 * Generates a list of TagData with the given TagImpl list
	 * 
	 * @param inTags TagImpl list
	 * @return
	 */
	private static List<TagData> generateList(List<Tag> inTags) {
		final SortedMap<String, TagData> tagDataList = new TreeMap<String, TagData>();

		for (final Tag tempTag : inTags) {
			tagDataList.put(tempTag.getTag_word().trim(), new TagData(tempTag));
		}

		return new ArrayList<TagData>(tagDataList.values());
	}

	/**
	 * @return the attribute word
	 */
	public String getWord() {
		final Tag theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getTag_word() != null)) {
			return theRecord.getTag_word();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute frequency
	 */
	public int getFrequency() {
		final Tag theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getFrequency();
		}
		return 0;
	}

	/**
	 * @return the attribute id_css
	 */
	public int getId_css() {
		return this.id_css;
	}
}
