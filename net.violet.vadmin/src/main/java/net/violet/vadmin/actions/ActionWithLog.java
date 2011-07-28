package net.violet.vadmin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Classe dérivée de Action où execute est protégé par un try/catch avec log des
 * exceptions au niveau fatal.
 */
public abstract class ActionWithLog extends Action {

	/**
	 * Constructeur par défaut.
	 */
	public ActionWithLog() {
		// This space for rent.
	}

	@Override
	public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		ActionForward theResult = null;
		try {
			theResult = doExecute(mapping, form, request, response);
		} catch (final Throwable aThrowable) {
			final Logger theLogger = Logger.getLogger(this.getClass());
			theLogger.fatal(aThrowable, aThrowable);
		}
		return theResult;
	}

	/**
	 * Méthode execute. Par défaut, appelle super.execute. Les classes dérivées
	 * peuvent surcharger cette méthode.
	 */
	protected abstract ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response);
}
