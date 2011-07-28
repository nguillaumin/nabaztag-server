package net.violet.platform.api.actions.site;

import java.util.Map;
import java.util.Map.Entry;

import net.violet.platform.api.actions.AbstractAction;
import net.violet.platform.api.actions.ActionParam;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.dataobjects.DicoData;
import net.violet.platform.dataobjects.SiteLangData;
import net.violet.platform.util.Constantes;

import org.apache.log4j.Logger;

public class GetLocalizedString extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(AbstractAction.class);

	private static final String REPLACEMENT_KEY_REGEX = "\\$\\{[0-9a-zA-Z]+\\}";

	@Override
	protected Object doProcessRequest(ActionParam inParam) throws InvalidParameterException {

		final String languageCode = inParam.getMainParamAsString();
		final String dicoKey = inParam.getString("key", true);

		final Map<String, Object> parameters = inParam.getMap("parameters", false);

		SiteLangData langData;
		try {
			langData = SiteLangData.getByISOCode(languageCode);

		} catch (final InvalidParameterException wtf) {
			GetLocalizedString.LOGGER.error("Called getLocalizedString(" + dicoKey + ") with invalid Lang parameter : " + languageCode, wtf);
			langData = SiteLangData.getByISOCode(SiteLangData.DEFAULT_SITE_LANGUAGE_ISOCODE);
		}

		String localized;
		try {
			localized = DicoData.findByDicoKeyAndLang(dicoKey, langData);
		} catch (final NoSuchMessageException e) {
			return dicoKey;
		}

		if ((parameters != null) && !parameters.isEmpty()) { // on remplace les signets par les params passés

			for (final Entry<String, Object> theEntry : parameters.entrySet()) {
				localized = localized.replaceAll("\\$\\{" + theEntry.getKey() + "\\}", theEntry.getValue().toString());
			}
			//clean le texte car elle contient des signets de remplacement ( ${xxx} )
			localized = localized.replaceAll(GetLocalizedString.REPLACEMENT_KEY_REGEX, net.violet.common.StringShop.EMPTY_STRING);
		}

		return localized;
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
		return Constantes.ONE_MONTH_IN_S;
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
