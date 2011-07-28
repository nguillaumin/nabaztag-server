package net.violet.platform.api.actions.applications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchApplicationException;
import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ApplicationData;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.util.Constantes;

/**
 * Renvoie toutes les chaines du dico spécifiques à une application pour une
 * langue donnée
 * 
 * @author christophe - Violet
 */
public class GetAllLocalizedStrings extends AbstractAction {

	/**
	 * @throws NoSuchApplicationException
	 * @throws InvalidParameterException
	 * @see net.violet.platform.api.actions.AbstractAction#doProcessRequest(net.violet.platform.api.actions.ActionParam)
	 */
	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException, NoSuchApplicationException {

		final ApplicationData app = getRequestedApplication(inParam, null);
		final String isoCode = inParam.getString("lang", true);

		// note : this call restrict the lang list to those known by the site
		final SiteLangData languageData = SiteLangData.getByISOCode(isoCode);

		final List<Dico> appDicoEntries = Factories.DICO.findByPage(app.getName(), languageData.getReference());

		final Map<String, String> dicoMap = new HashMap<String, String>(appDicoEntries.size());

		for (final Dico dicoEntry : appDicoEntries) {
			dicoMap.put(dicoEntry.getDico_key(), dicoEntry.getDico_text());
		}

		return dicoMap;

	}

	/**
	 * @see net.violet.platform.api.actions.Action#isCacheable()
	 */
	public boolean isCacheable() {
		return true;
	}

	/**
	 * User informations may be cached one week
	 * 
	 * @see net.violet.platform.api.actions.Action#getExpirationTime()
	 */
	public long getExpirationTime() {
		return Constantes.ONE_WEEK_IN_S;
	}

	/**
	 * Read Only action
	 * 
	 * @see net.violet.platform.api.actions.Action#getType()
	 */
	public ActionType getType() {
		return ActionType.GET;
	}

}
