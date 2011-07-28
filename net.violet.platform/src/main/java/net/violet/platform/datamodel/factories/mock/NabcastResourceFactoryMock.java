package net.violet.platform.datamodel.factories.mock;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationContent;
import net.violet.platform.datamodel.NabcastResource;
import net.violet.platform.datamodel.factories.NabcastResourceFactory;
import net.violet.platform.datamodel.mock.NabcastResourceMock;
import net.violet.platform.dataobjects.ObjectType;

public class NabcastResourceFactoryMock extends RecordFactoryMock<NabcastResource, NabcastResourceMock> implements NabcastResourceFactory {

	protected NabcastResourceFactoryMock() {
		super(NabcastResourceMock.class);
	}

	public NabcastResource create(ApplicationContent inContent, Date inExpirationDate, Date inReleaseDate, ObjectType inType, String inLabel) {
		return new NabcastResourceMock(inContent, new Timestamp(inReleaseDate.getTime()), inExpirationDate != null ? new Timestamp(inExpirationDate.getTime()) : null, inType, inLabel);
	}

	public List<NabcastResource> findAllByName(NabcastResource inResource) {
		final List<NabcastResource> theList = new LinkedList<NabcastResource>();
		for (final NabcastResource aResource : findAll()) {
			if (aResource.getLabel().equals(inResource.getLabel()) && aResource.getContent().getApplication().equals(inResource.getContent().getApplication())) {
				theList.add(aResource);
			}
		}
		return theList;
	}

	public List<NabcastResource> findAllByLabel(String inLabel, Application inNabcast) {
		final List<NabcastResource> result = new ArrayList<NabcastResource>();
		for (final NabcastResource aResource : findAll()) {
			if (aResource.getContent().getApplication().equals(inNabcast) && aResource.getLabel().equals(inLabel)) {
				result.add(aResource);
			}
		}
		return result;
	}
}
