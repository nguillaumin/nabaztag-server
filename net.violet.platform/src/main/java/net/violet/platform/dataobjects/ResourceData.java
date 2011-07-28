package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;

import net.violet.platform.datamodel.Resource;

import org.apache.log4j.Logger;

public class ResourceData extends RecordData<Resource> {

	private static final Logger LOGGER = Logger.getLogger(ResourceData.class);

	public static ResourceData getData(Resource inResource) {
		try {
			return RecordData.getData(inResource, ResourceData.class, Resource.class);
		} catch (final InstantiationException e) {
			ResourceData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			ResourceData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			ResourceData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			ResourceData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected ResourceData(Resource inRecord) {
		super(inRecord);
	}

}
