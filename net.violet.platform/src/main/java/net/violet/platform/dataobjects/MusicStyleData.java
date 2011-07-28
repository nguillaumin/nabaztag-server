package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.Categ;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.MusicStyleImpl;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MusicData.StatusMusic;
import net.violet.platform.util.DicoTools;

public final class MusicStyleData extends RecordData<MusicStyle> {

	private final String musicStyleName;

	/**
	 * Contructs a new {@link MusicStyleData}
	 * 
	 * @param inMusicStyle
	 * @param inLang
	 */
	private MusicStyleData(MusicStyle inMusicStyle, Lang inLang) {
		super(inMusicStyle);
		Lang lang = Factories.LANG.find(2);

		if (inLang != null) {
			lang = inLang;
		}

		if ((inMusicStyle != null)) {
			this.musicStyleName = DicoTools.dico_if(lang, inMusicStyle.getMusicstyle_name());
		} else {
			this.musicStyleName = net.violet.common.StringShop.EMPTY_STRING;
		}
	}

	/**
	 * Finds all the MusicImpl styles by {@link Lang}
	 * 
	 * @param inLangId
	 * @return a list of {@link MusicStyleData} or an empty list
	 */
	public static List<MusicStyleData> findAllClinByLang(Lang inLang) {
		if (inLang != null) {
			return MusicStyleData.generateListFromMusicStylesAndLang(MusicStyleImpl.findAllClinByLang(inLang.getId()), inLang);
		}
		return Collections.emptyList();
	}

	/**
	 * Finds all the music available for the given biblio
	 * 
	 * @param inLang
	 * @return
	 */
	public static List<MusicStyleData> findAllCategForBiblio(Lang inLang) {
		return MusicStyleData.generateListFromMusicStylesAndLang(MusicStyleImpl.findAllCategForBiblio(), inLang);
	}

	/**
	 * Finds all the MusicImpl styles by {@link Lang}
	 * 
	 * @param inLangId
	 * @return a list of {@link MusicStyleData} or an empty list
	 */
	public static List<MusicStyleData> findAllBiblioByLang(Lang inLang) {
		if (inLang != null) {
			return MusicStyleData.generateListFromMusicStylesAndLang(MusicStyleImpl.findAllBiblioByLang(inLang.getId()), inLang);
		}
		return Collections.emptyList();
	}

	/**
	 * Finds all Categories by LibraryType
	 * 
	 * @param inLangId
	 * @return a list of {@link MusicStyleData} or an empty list
	 */
	public static List<MusicStyleData> findAllCategoriesByType(long type) {
		final List<MusicStyleData> musicStyleDataList = new ArrayList<MusicStyleData>();
		final List<MusicStyle> musicStyleList = Factories.MUSIC_STYLE.findAllStylesByType(type);
		final Lang inLang = Factories.LANG.find(1);
		for (final MusicStyle aMusicStyle : musicStyleList) {
			musicStyleDataList.add(new MusicStyleData(aMusicStyle, inLang));
		}
		return musicStyleDataList;
	}

	public static List<String> findAllCategoriesByType(StatusMusic libraryType) {
		final int type = libraryType.getType();
		final List<String> theResult = new ArrayList<String>();
		if (type == Music.TYPE_MP3_USER_LIBRARY) {
			final List<Categ> listCateg = Factories.CATEG.findAll();
			for (final Categ inCateg : listCateg) {
				theResult.add(inCateg.getName());
			}
		} else {
			final List<MusicStyle> listMusicStyle = Factories.MUSIC_STYLE.findAllStylesByType(type);
			for (final MusicStyle inMusicStyle : listMusicStyle) {
				theResult.add(inMusicStyle.getMusicstyle_name());
			}
		}
		return theResult;
	}

	/**
	 * Generates a list of {@link MusicStyleData} with the given
	 * {@link MusicStyle} list & {@link Lang}
	 * 
	 * @param inMusicStyles MusicStyleImpl list
	 * @param inLang
	 * @return
	 */
	private static List<MusicStyleData> generateListFromMusicStylesAndLang(List<MusicStyle> inMusicStyles, Lang inLang) {
		final List<MusicStyleData> musicStyleDataList = new ArrayList<MusicStyleData>();
		for (final MusicStyle aMusicStyle : inMusicStyles) {
			musicStyleDataList.add(new MusicStyleData(aMusicStyle, inLang));
		}

		return musicStyleDataList;
	}

	/**
	 * @return the attribute music_style_id
	 */
	public long getId() {
		final MusicStyle theRecord = getRecord();
		if ((theRecord != null)) {
			return theRecord.getId();
		}
		return 0;
	}

	/**
	 * @return the attribute music_style_id
	 */
	public long getMusicSytleId() {
		return getId();
	}

	/**
	 * @return the attribute music_style_name
	 */
	public String getMusicStyleName() {
		return this.musicStyleName;
	}

	/**
	 * @return the attribute mysic_style_private
	 */
	public long getMusicStylePrivate() {
		final MusicStyle theRecord = getRecord();
		if ((theRecord != null)) {
			return theRecord.getStyle_private();
		}
		return 0;
	}

}
