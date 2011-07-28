package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.MusicStyle;

public interface MusicStyleFactory extends RecordFactory<MusicStyle> {

	List<MusicStyle> findAllStylesByType(long type);

}
