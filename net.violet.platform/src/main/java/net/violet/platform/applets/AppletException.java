package net.violet.platform.applets;

/**
 * Erreur lors de l'exécution d'une application interactive (processEvent)
 * 
 * @author christophe - Violet
 */
public class AppletException extends Exception {

	public AppletException(String inMsg) {
		super(inMsg);
	}

	public AppletException(String inMsg, Throwable cause) {
		super(inMsg, cause);
	}
}
