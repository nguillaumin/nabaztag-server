package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationContent;
import net.violet.platform.datamodel.NabcastResource;
import net.violet.platform.datamodel.NabcastResourceImpl;
import net.violet.platform.datamodel.factories.NabcastResourceFactory;
import net.violet.platform.dataobjects.ObjectType;

public class NabcastResourceFactoryImpl extends RecordFactoryImpl<NabcastResource, NabcastResourceImpl> implements NabcastResourceFactory {

	protected NabcastResourceFactoryImpl() {
		super(NabcastResourceImpl.SPECIFICATION);
	}

	public NabcastResource create(ApplicationContent inContent, Date inExpirationDate, Date inReleaseDate, ObjectType inType, String inPath) throws SQLException {
		return new NabcastResourceImpl(inContent, new Timestamp(inReleaseDate.getTime()), inExpirationDate != null ? new Timestamp(inExpirationDate.getTime()) : null, inType, inPath);
	}

	public List<NabcastResource> findAllByName(NabcastResource inResource) {
		return findAll(new String[] { "application_contents" }, " application_id = ? AND label = ? AND application_contents.id = nabcast_resources.content_id ", Arrays.asList(new Object[] { inResource.getContent().getApplication().getId(), inResource.getLabel() }), null);
	}

	public List<NabcastResource> findAllByLabel(String inLabel, Application inNabcast) {
		final String condition = " application_contents.application_id = ? AND application_contents.id = nabcast_resources.content_id AND nabcast_resources.label = ? ";
		final List<Object> values = Arrays.asList(new Object[] { inNabcast.getId(), inLabel });
		return findAll(new String[] { "application_contents" }, condition, values, null);
	}
}
