package net.violet.platform.api.maps;

import java.util.Map;

import net.violet.platform.api.converters.PojoHelper;
import net.violet.platform.api.exceptions.InvalidParameterException;

public abstract class AbstractAPIMap extends PojoMap {

	private static int DEFAULT_INITIAL_CAPACITY = 16;

	public AbstractAPIMap() {
		super(AbstractAPIMap.DEFAULT_INITIAL_CAPACITY);
	}

	public AbstractAPIMap(int inInitialCapacity) {
		super(inInitialCapacity);
	}

	/**
	 * Takes an allready built map to promote it as an API map.. We should do
	 * some conformance tests before accepting it..
	 * 
	 * @param inMap
	 * @param testConformance TRUE to launch a test on the map content to ensure
	 *            POJO conformance
	 * @throws InvalidParameterException
	 */
	protected AbstractAPIMap(Map<String, Object> inMap, boolean testConformance) throws InvalidParameterException {
		super(inMap);
		if (testConformance) {
			checkConformance();
		}
	}

	/**
	 * API maps should overwrite this method to implement more specific
	 * conformance tests
	 * 
	 * @throws InvalidParameterException
	 */
	public void checkConformance() throws InvalidParameterException {
		PojoHelper.checkPojoConformance(this);
	}

}
