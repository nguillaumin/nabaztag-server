package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.violet.platform.datamodel.NabcastResource;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class NabcastResourceData extends APIData<NabcastResource> {

	private static final Logger LOGGER = Logger.getLogger(NabcastResourceData.class);

	public static NabcastResourceData getData(NabcastResource inResource) {
		try {
			return RecordData.getData(inResource, NabcastResourceData.class, NabcastResource.class);
		} catch (final InstantiationException e) {
			NabcastResourceData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			NabcastResourceData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			NabcastResourceData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			NabcastResourceData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected NabcastResourceData(NabcastResource inRecord) {
		super(inRecord);
	}

	public static NabcastResourceData create(ApplicationContentData inContent, Date inExpirationDate, Date inReleaseDate, ObjectType inType, String inPath) {
		try {
			return NabcastResourceData.getData(Factories.NABCAST_RESOURCE.create(inContent.getRecord(), inExpirationDate, inReleaseDate, inType, inPath));
		} catch (final SQLException e) {
			NabcastResourceData.LOGGER.fatal(e, e);
		}
		return null;
	}

	public static NabcastResourceData findByAPIId(String inAPIId, String inAPIKey) {

		NabcastResourceData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.APPLICATION_CONTENT, inAPIKey);

		if (theID != 0) {
			final NabcastResource app = Factories.NABCAST_RESOURCE.find(theID);
			if (app != null) {
				theResult = NabcastResourceData.getData(app);
			}
		}
		return theResult;
	}

	public ApplicationContentData getContent() {
		final NabcastResource theRecord = getRecord();

		if ((theRecord != null) && (theRecord.getContent() != null)) {
			return ApplicationContentData.getData(theRecord.getContent());
		}
		return null;
	}

	public static List<NabcastResourceData> findByName(NabcastResourceData inResource) {
		return NabcastResourceData.generateList(Factories.NABCAST_RESOURCE.findAllByName(inResource.getRecord()));
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.NABCAST_RESOURCE;
	}

	private static List<NabcastResourceData> generateList(List<NabcastResource> inList) {
		final List<NabcastResourceData> theList = new LinkedList<NabcastResourceData>();
		for (final NabcastResource aResource : inList) {
			theList.add(NabcastResourceData.getData(aResource));
		}

		return theList;
	}

	public static List<NabcastResourceData> findAllByLabel(String inLabel, ApplicationData inNabcast) {
		return NabcastResourceData.generateList(Factories.NABCAST_RESOURCE.findAllByLabel(inLabel, inNabcast.getRecord()));
	}

}
