package net.violet.platform.datamodel.factories;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationContent;
import net.violet.platform.datamodel.NabcastResource;
import net.violet.platform.dataobjects.ObjectType;

public interface NabcastResourceFactory extends RecordFactory<NabcastResource> {

	NabcastResource create(ApplicationContent inContent, Date inExpirationDate, Date inReleaseDate, ObjectType inType, String inPath) throws SQLException;

	List<NabcastResource> findAllByName(NabcastResource inResource);

	List<NabcastResource> findAllByLabel(String inLabel, Application inNabcast);

}
