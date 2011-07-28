package net.violet.platform.datamodel.factories.mock;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.MusicStyleFactory;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.datamodel.mock.MusicStyleMock;

public class MusicStyleFactoryMock extends RecordFactoryMock<MusicStyle, MusicStyleMock> implements MusicStyleFactory {

	MusicStyleFactoryMock() {
		super(MusicStyleMock.class);
	}

	public List<MusicStyle> findAllStylesByType(long type) {
		final List<MusicStyle> theResult = new LinkedList<MusicStyle>();
		final Map<Long, Music> inMusic = Factories.MUSIC.findAllMapped();
		final Iterator it = inMusic.values().iterator();
		while (it.hasNext()) {
			final MusicMock theMusic = (MusicMock) it.next();
			final MusicStyle aux = theMusic.getMusicStyle();
			if ((theMusic.getMusic_lang() == 1) && (theMusic.getMusic_type() == type)) {
				if (type != Music.TYPE_MP3_USER_LIBRARY) {
					if (!theResult.contains(aux)) {
						theResult.add(aux);
					}
				} else if (theMusic.getMusic_share() == 1) {
					if (!theResult.contains(aux)) {
						theResult.add(aux);
					}
				}
			}
		}
		return theResult;
	}

}
