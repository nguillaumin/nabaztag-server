package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;

import net.violet.platform.datamodel.ApplicationContent;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class ApplicationContentData extends APIData<ApplicationContent> {

	private static final Logger LOGGER = Logger.getLogger(ApplicationContentData.class);

	public static ApplicationContentData getData(ApplicationContent inApplicationContent) {
		try {
			return RecordData.getData(inApplicationContent, ApplicationContentData.class, ApplicationContent.class);
		} catch (final InstantiationException e) {
			ApplicationContentData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			ApplicationContentData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			ApplicationContentData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			ApplicationContentData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected ApplicationContentData(ApplicationContent inRecord) {
		super(inRecord);
	}

	public static ApplicationContentData findByAPIId(String inAPIId, String inAPIKey) {

		ApplicationContentData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.APPLICATION_CONTENT, inAPIKey);

		if (theID != 0) {
			final ApplicationContent app = Factories.APPLICATION_CONTENT.find(theID);
			if (app != null) {
				theResult = ApplicationContentData.getData(app);
			}
		}
		return theResult;
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.APPLICATION_CONTENT;
	}

	public static ApplicationContentData create(ApplicationData inApplication, FilesData inFile) {
		return ApplicationContentData.getData(Factories.APPLICATION_CONTENT.create(inApplication.getRecord(), inFile.getRecord()));
	}

	@Override
	protected boolean doDelete() {
		final ApplicationContent theContent = getRecord();
		if ((theContent != null) && (theContent.getFiles() != null)) {
			theContent.getFiles().scheduleDeletion();
		}
		return true;
	}

}
