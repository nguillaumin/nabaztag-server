package net.violet.vadmin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * Classe dérivée de DispatchAction où execute est protégé par un try/catch avec
 * log des exceptions au niveau fatal.
 */
public abstract class DispatchActionWithLog extends DispatchAction {

	/**
	 * Constructeur par défaut.
	 */
	public DispatchActionWithLog() {
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
	 * Appelle la méthode execute de DispatchAction, i.e. fait effectivement le
	 * dispatch.
	 */
	protected final ActionForward dispatchExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return super.execute(mapping, form, request, response);
	}

	/**
	 * Méthode execute. Par défaut, appelle super.execute. Les classes dérivées
	 * peuvent surcharger cette méthode.
	 */
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return dispatchExecute(mapping, form, request, response);
	}
}
