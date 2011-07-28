package net.violet.platform.api.maps;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.api.exceptions.MissingParameterException;

import com.sun.syndication.io.impl.DateParser;

class PojoList extends ArrayList {

	private final String mName;

	public PojoList(String inListName, List inList) {
		super((inList == null) ? 0 : inList.size());
		if (inList != null) {
			this.addAll(inList);
		}
		this.mName = inListName;
	}

	/**
	 * Récupère la valeur d'un paramètre texte.
	 * 
	 * @param inIndex le nom du paramètre à récupérer.
	 * @return la valeur du paramètre ou <code>null</code>.
	 * @throws InvalidParameterException si le paramètre n'est pas une chaîne.
	 */
	public String getString(int inIndex) {

		try {
			return (String) get(inIndex);

		} catch (final IndexOutOfBoundsException houps) {
			return null;

		} catch (final ClassCastException bad) {
			// hmm.. cas limite ou une valeur a été parsée en numérique
			// mais l'utilisateur la veux sous forme de chaine, ex : version=0.2
			// >> il aurait du la passer entre quotes mais un bug dans req.getParameter() a viré les quotes !
			// >> on fait un effort
			return (get(inIndex)).toString();
			// throw new InvalidParameterException(APIErrorMessage.NOT_A_STRING);
		}
	}

	/**
	 * Récupère la valeur d'un paramètre texte obligatoire.
	 * 
	 * @param inIndex le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas une chaîne ou
	 *             n'est pas présent.
	 */
	public String getString(int inIndex, boolean isMandatory) throws InvalidParameterException {

		final String paramValue = getString(inIndex);

		if ((paramValue == null) && isMandatory) {
			throw new MissingParameterException("[" + inIndex + "]");
		}

		return paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre texte avec valeur par défaut.
	 * 
	 * @param inIndex le nom du paramètre à récupérer.
	 * @param inDefaultValue la valeur par défaut.
	 * @return la valeur lue du paramètre s'il existe ou la valeur par défaut.
	 * @throws InvalidParameterException 
	 * @throws InvalidParameterException si le paramètre n'est pas une chaîne
	 */
	public <T> T get(int inIndex, T inDefaultValue) throws InvalidParameterException {

		final T paramValue;
		if (inDefaultValue instanceof String) {
			paramValue = (T) getString(inIndex);
		} else if (inDefaultValue instanceof Date) {
			paramValue = (T) getDate(inIndex);
		} else if (inDefaultValue instanceof Integer) {
			paramValue = (T) getInteger(inIndex);
		} else if (inDefaultValue instanceof Long) {
			paramValue = (T) getLong(inIndex);
		} else if (inDefaultValue instanceof Boolean) {
			paramValue = (T) getBoolean(inIndex);
		} else if (inDefaultValue instanceof List) {
			paramValue = (T) getList(inIndex);
		} else {
			paramValue = null;
		}

		return (paramValue == null) ? inDefaultValue : paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre date ou <code>null</code> si absent.
	 * 
	 * @param inIndex name of the parameter we want to retrieve the value.
	 * @return the value of the parameter, or null.
	 */
	public Date getDate(int inIndex) throws InvalidParameterException {

		try {
			// Date parameters passed via XML-RPC calls or in JSON format
			// may already have been parsed according to their specific formats
			return (Date) get(inIndex);

		} catch (final IndexOutOfBoundsException houps) {
			return null;

		} catch (final ClassCastException ignore) {
			// assume that the string has not been parsed by the format converter
		}

		final String strDate = getString(inIndex); // InvalidParameterException if param is not a String either !

		if (strDate == null) {
			return null;
		}

		// try to parse date in RFC822 or W3C format
		final Date parsed = DateParser.parseDate(strDate);

		if (parsed == null) { // null means that the parsing was impossible
			throw new InvalidParameterException(APIErrorMessage.NOT_A_DATE, this.mName + "[" + inIndex + "]");
		}

		return parsed;
	}

	/**
	 * Récupère la valeur d'un paramètre date obligatoire.
	 * 
	 * @param inIndex le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas une date ou
	 *             n'est pas présent.
	 */
	public Date getDate(int inIndex, boolean isMandatory) throws InvalidParameterException {

		final Date paramValue = getDate(inIndex);

		if ((paramValue == null) && isMandatory) {
			throw new MissingParameterException("[" + inIndex + "]");
		}

		return paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre entier <code>Integer</code> ou
	 * <code>null</code>.
	 * 
	 * @param inIndex le nom du paramètre à récupérer.
	 * @return la valeur du paramètre ou <code>null</code>.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier.
	 */
	public Integer getInteger(int inIndex) throws InvalidParameterException {

		try {
			return (Integer) get(inIndex);

		} catch (final IndexOutOfBoundsException houps) {
			return null;

		} catch (final ClassCastException passThatOne) {
			throw new InvalidParameterException(APIErrorMessage.NOT_AN_INTEGER, this.mName + "[" + inIndex + "]");
		}
	}

	/**
	 * Récupère la valeur d'un paramètre entier obligatoire
	 * 
	 * @param inIndex le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier
	 *             signé ou n'est pas présent.
	 */
	public int getInt(int inIndex, boolean isMandatory) throws InvalidParameterException {
		final Integer paramValue = getInteger(inIndex);

		if (isMandatory && (paramValue == null)) {
			throw new MissingParameterException("[" + inIndex + "]");
		}

		return paramValue.intValue();
	}

	/**
	 * Récupère la valeur d'un paramètre entier <code>Long</code>ou
	 * <code>null</code>
	 * 
	 * @param inIndex le nom du paramètre à récupérer.
	 * @return la valeur du paramètre ou <code>null</code>.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier.
	 */
	public Long getLong(int inIndex) throws InvalidParameterException {

		try {
			return (Long) get(inIndex);

		} catch (final IndexOutOfBoundsException houps) {
			return null;

		} catch (final ClassCastException passThatOne) {
			// maybe Integer..?
		}

		try {
			final Integer i = (Integer) get(inIndex);
			return new Long(i.longValue());

		} catch (final ClassCastException passThatOne) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_LONG, this.mName + "[" + inIndex + "]");
		}
	}

	/**
	 * Récupère la valeur d'un paramètre entier long obligatoire
	 * 
	 * @param inIndex le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier
	 *             long ou n'est pas présent.
	 */
	public long getLong(int inIndex, boolean isMandatory) throws InvalidParameterException {
		final Long paramValue = getLong(inIndex);

		if ((paramValue == null) && isMandatory) {
			throw new MissingParameterException("[" + inIndex + "]");
		}

		return paramValue;
	}

	/**
	 * Récupère la valeur d'un paramètre booléen, s'il est présent.
	 * 
	 * @param inIndex le nom du paramètre à récupérer.
	 * @return la valeur du paramètre ou <code>NULL</code>.
	 * @throws InvalidParameterException si le paramètre n'est pas un entier ou
	 *             n'est pas présent.
	 */
	public Boolean getBoolean(int inIndex) throws InvalidParameterException {
		try {
			return (Boolean) get(inIndex);

		} catch (final IndexOutOfBoundsException houps) {
			return null;

		} catch (final ClassCastException bad) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_BOOLEAN, this.mName + "[" + inIndex + "]");
		}
	}

	/**
	 * @param <T>
	 * @param inIndex
	 * @return
	 * @throws InvalidParameterException
	 */
	public <T> List<T> getList(int inIndex) throws InvalidParameterException {
		try {
			return (List<T>) get(inIndex);

		} catch (final IndexOutOfBoundsException houps) {
			return null;

		} catch (final ClassCastException bad) {
			throw new InvalidParameterException(APIErrorMessage.NOT_A_LIST, this.mName + "[" + inIndex + "]");
		}
	}

	/**
	 * Récupère la valeur d'un paramètre sous forme d'une liste.
	 * 
	 * @param inIndex le nom du paramètre à récupérer.
	 * @param isMandatory si le paramètre est requis.
	 * @return la valeur du paramètre.
	 * @throws InvalidParameterException si le paramètre n'est pas une liste ou
	 *             n'est pas présent.
	 */
	public <T> List<T> getList(int inIndex, boolean isMandatory) throws InvalidParameterException {
		final List<T> paramValue = getList(inIndex);

		if (isMandatory && (paramValue == null)) {
			throw new MissingParameterException("[" + inIndex + "]");
		}

		return paramValue;
	}

}
