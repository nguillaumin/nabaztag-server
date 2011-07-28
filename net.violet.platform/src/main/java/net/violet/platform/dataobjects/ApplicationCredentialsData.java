package net.violet.platform.dataobjects;

import net.violet.platform.api.callers.APICaller.CallerClass;
import net.violet.platform.datamodel.ApplicationCredentials;
import net.violet.platform.datamodel.factories.Factories;

public class ApplicationCredentialsData extends RecordData<ApplicationCredentials> {

	//private static final Logger LOGGER = Logger.getLogger(ApplicationCredentialsData.class);

	public static ApplicationCredentialsData getData(ApplicationCredentials inApplicationCredentials) {
//		try {
//			return RecordData.getData(inApplicationCredentials, ApplicationCredentialsData.class, ApplicationCredentials.class);
//		} catch (final InstantiationException e) {
//			ApplicationCredentialsData.LOGGER.fatal(e, e);
//		} catch (final IllegalAccessException e) {
//			ApplicationCredentialsData.LOGGER.fatal(e, e);
//		} catch (final InvocationTargetException e) {
//			ApplicationCredentialsData.LOGGER.fatal(e, e);
//		} catch (final NoSuchMethodException e) {
//			ApplicationCredentialsData.LOGGER.fatal(e, e);
//		}
//
//		return null;
		if (inApplicationCredentials != null) {
			return new ApplicationCredentialsData(inApplicationCredentials);
		}
		return null;
	}

	public static ApplicationCredentialsData findByPublicKey(String inPublicKey) {
		return ApplicationCredentialsData.getData(Factories.APPLICATION_CREDENTIALS.findByPublicKey(inPublicKey));
	}

	public static ApplicationCredentialsData findByApplication(ApplicationData inApplication) {
		return ApplicationCredentialsData.getData(Factories.APPLICATION_CREDENTIALS.findByApplication(inApplication.getRecord()));
	}

	/**
	 * This constructor MUST be protected, it is used by the RecordData class to
	 * create new instances.
	 * 
	 * @param inApplication
	 */
	protected ApplicationCredentialsData(ApplicationCredentials inApplicationCredentials) {
		super(inApplicationCredentials);
	}

	public ApplicationData getApplication() {
		final ApplicationCredentials rec = getRecord();
		return (rec == null) ? null : ApplicationData.getData(rec.getApplication());
	}

	public String getPublicKey() {
		final ApplicationCredentials rec = getRecord();
		return (rec == null) ? net.violet.common.StringShop.EMPTY_STRING : rec.getPublicKey();
	}

	public String getPrivateKey() {
		final ApplicationCredentials rec = getRecord();
		return (rec == null) ? net.violet.common.StringShop.EMPTY_STRING : rec.getPrivateKey();
	}

	public CallerClass getCallerClass() {
		final ApplicationCredentials rec = getRecord();
		return (rec == null) ? null : rec.getCallerClass();
	}

}
