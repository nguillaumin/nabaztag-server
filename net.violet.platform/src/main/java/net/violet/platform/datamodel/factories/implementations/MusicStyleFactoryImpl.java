package net.violet.platform.datamodel.factories.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.MusicStyleImpl;
import net.violet.platform.datamodel.factories.MusicStyleFactory;

public class MusicStyleFactoryImpl extends RecordFactoryImpl<MusicStyle, MusicStyleImpl> implements MusicStyleFactory {


	// private static final Logger LOGGER =
	// Logger.getLogger(LookupService.class);
	public MusicStyleFactoryImpl() {
		super(MusicStyleImpl.SPECIFICATION);
	}

	public List<MusicStyle> findAllStylesByType(long type) {

		final String[] inJoinTables = new String[] { "music" };
		final String condition = "musicstyle_id = music_styleid AND music_lang= 1 AND music_type = ? AND style_private=0 group by musicstyle_id";

		final List<MusicStyle> theResult = new ArrayList<MusicStyle>();

		theResult.addAll(findAll(inJoinTables, condition, Collections.singletonList((Object) type), "musicstyle_id"));

		return theResult;
	}

	public List<MusicStyle> findAllClinByLang(long inLangId) {

		final String[] inJoinTables = new String[] { "music" };
		final String condition = "musicstyle_id = music_styleid AND music_lang= ? AND music_type = 1 AND style_private=0 group by musicstyle_id";

		final List<MusicStyle> theResult = new ArrayList<MusicStyle>();

		theResult.addAll(findAll(inJoinTables, condition, Collections.singletonList((Object) inLangId), "musicstyle_id"));

		return theResult;
	}

	public MusicStyle findStyleByName(String categoryName) {
		final String condition = "musicstyle_name = ?";
		final List<MusicStyle> theResult = findAll(condition, Collections.singletonList((Object) categoryName));
		if (!theResult.isEmpty()) {
			return theResult.get(0);
		}
		// TODO : error
		return null;
	}

}
