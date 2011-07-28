package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Continent;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public final class ContinentData extends RecordData<Continent> {

	private static final Logger LOGGER = Logger.getLogger(ContinentData.class);

	public static ContinentData getData(Continent inContinent) {
		try {
			return RecordData.getData(inContinent, ContinentData.class, Continent.class);
		} catch (final InstantiationException e) {
			ContinentData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			ContinentData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			ContinentData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			ContinentData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected ContinentData(Continent inContinent) {
		super(inContinent);
	}

	/**
	 * @return the continent name
	 */
	public String getName() {
		final Continent record = getRecord();
		if (record != null) {
			return record.getName();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static List<String> getAllContinentsNames() {
		final List<String> namesList = new ArrayList<String>();
		for (final Continent inContinent : Factories.CONTINENT.findAllContinents()) {
			namesList.add(inContinent.getName());
		}
		return namesList;
	}

	public static ContinentData findByName(String continentName) {
		final Continent record = Factories.CONTINENT.findIdByName(continentName);
		if (record != null) {
			return ContinentData.getData(record);
		}
		return null;
	}
}
