package net.violet.platform.api.callers;

import net.violet.platform.api.exceptions.APIException;
import net.violet.platform.datamodel.Application;
import net.violet.platform.dataobjects.ApplicationCredentialsData;
import net.violet.platform.dataobjects.ApplicationData;

/**
 * Wrapper around the different API call emitters we have to deal with TODO :
 * add the objects
 * 
 * @author christophe - Violet
 */
public class ApplicationAPICaller implements APICaller {

	private final ApplicationCredentialsData mCredentials;
	private final CallerClass mCallerClass = CallerClass.APPLICATION;;

	/**
	 * Constructeur à partir d'une application. FIXME: il y a les 'vraies'
	 * applications, et les composants de la plateforme qui sont aussi inscrits
	 * dans la table application (pour l'authentification REST notamment) la
	 * distinction est faite grace à la colonne application_role
	 * 
	 * @param inApplication application.
	 * @throws APIException
	 */
	public ApplicationAPICaller(ApplicationCredentialsData inCredentials) {

		this.mCredentials = inCredentials;

		/*
		 * if (callerClass.equals(CallerClass.COMPONENT)) { mCallerClass =
		 * CallerClass.COMPONENT; // FIXME mApplication = null; // we are not a
		 * REAL application / this could be to ensure that the emitter is a real
		 * platform component ie : we can instanciate it try { Class
		 * emitterClass = Class.forName(inApp.getName()); Method getInstanceMth
		 * = emitterClass.getMethod("getInstance"); IAPICallEmitter theRealMe =
		 * (IAPICallEmitter) getInstanceMth.invoke(null); } catch (Exception e)
		 * { throw new
		 * APIException("Unable to create an instance of caller component " +
		 * inApp.getName(), e); } } else { // TODO throw new
		 * APIException("Unrecognized API call emitter. Role whould be in " +
		 * CallerClass.values()); }
		 */
	}

	@Override
	public String toString() {
		return "Application caller [" + this.mCredentials.getPublicKey() + "]";
	}

	public CallerClass getCallerClass() {
		return this.mCallerClass;
	}

	public boolean isApplication() {
		return this.mCallerClass == CallerClass.APPLICATION;
	}

	public boolean isObject() {
		return this.mCallerClass == CallerClass.OBJECT;
	}

	public boolean isPlatformComponent() {
		return this.mCallerClass == CallerClass.COMPONENT;
	}

	public Application.ApplicationClass getApplicationClass() {
		return this.mCredentials.getApplication().getApplicationClass();
	}

	/**
	 * @see net.violet.platform.api.callers.APICaller#getApplication()
	 */
	public ApplicationData getApplication() {
		return this.mCredentials.getApplication();
	}

	/*
	 * (non-Javadoc)
	 * @see net.violet.platform.api.authentication.IAPICallEmitter#getAPIKey()
	 */
	public String getAPIKey() {
		return this.mCredentials.getPublicKey();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * net.violet.platform.api.authentication.IAPICallEmitter#getAPIPassword()
	 */
	public String getAPIPassword() {
		return this.mCredentials.getPrivateKey();
	}

}
