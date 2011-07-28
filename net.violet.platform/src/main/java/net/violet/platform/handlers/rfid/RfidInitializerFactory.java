package net.violet.platform.handlers.rfid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.violet.platform.applets.AppletDispatcher;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.ZtampBatch;
import net.violet.platform.datamodel.Application.NativeApplication;
import net.violet.platform.datamodel.factories.Factories;

public class RfidInitializerFactory {

	private static final Map<Application, RfidInitializer> INITIALIZERS_MAP;
	private static final Map<Application.NativeApplication, RfidInitializer> NATIVE_INITIALIZERS_MAP;

	private static final RfidInitializer RFID_PLAYER_INITIALIZER = new PlayerInitializer();

	static {
		final Map<Application, RfidInitializer> initializerMap = new HashMap<Application, RfidInitializer>();

		initializerMap.put(DiscoveryInitializer.APPLICATION, new DiscoveryInitializer());
		initializerMap.put(SIXnCoInitializer.APPLICATION, new SIXnCoInitializer());
		initializerMap.put(OperationM6Initializer.APPLICATION, new OperationM6Initializer());
		initializerMap.put(OperationDELLInitializer.APPLICATION, new OperationDELLInitializer());
		initializerMap.put(OperationAmusementInitializer.APPLICATION, new OperationAmusementInitializer());
		initializerMap.put(OperationMobileadInitializer.MOBILEAD, new OperationMobileadInitializer());
		initializerMap.put(OperationFuturEnSeineInitializer.APPLICATION, new OperationFuturEnSeineInitializer());
		initializerMap.put(FreeAngelAdultInitializer.APPLICATION, new FreeAngelAdultInitializer());
		initializerMap.put(FreeAngelAdoInitializer.APPLICATION, new FreeAngelAdoInitializer());
		initializerMap.put(FreeAngelChildInitializer.APPLICATION, new FreeAngelChildInitializer());
		initializerMap.put(ColoriGOBoyInitializer.APPLICATION, new ColoriGOBoyInitializer());
		initializerMap.put(ColoriGOGirlInitializer.APPLICATION, new ColoriGOGirlInitializer());
		initializerMap.put(ColoriGOMixteInitializer.APPLICATION, new ColoriGOMixteInitializer());

		//theMap.put(CallUrlInitializer.APPLICATION, new CallUrlInitializer());

		INITIALIZERS_MAP = Collections.unmodifiableMap(initializerMap);

	}

	static {
		final Map<Application.NativeApplication, RfidInitializer> theOtherMap = new HashMap<Application.NativeApplication, RfidInitializer>();
		theOtherMap.put(NativeApplication.RSS_FREE, new RssFreeInitializer());

		NATIVE_INITIALIZERS_MAP = Collections.unmodifiableMap(theOtherMap);
	}

	public static void initRfid(VObject rfid, ZtampBatch batch) {

		final Long theApplicationId = batch.getDefaultAppletId();
		if (theApplicationId != null) {
			final Application theApplication = Factories.APPLICATION.find(theApplicationId);

			RfidInitializer theInitializer;
			if (AppletDispatcher.AUDIOBOOK_NAME.matcher(theApplication.getName()).matches()) {
				theInitializer = RfidInitializerFactory.RFID_PLAYER_INITIALIZER;
			} else {
				theInitializer = RfidInitializerFactory.INITIALIZERS_MAP.get(theApplication);
				if (theInitializer == null) { //maybe native application
					final Application.NativeApplication theNative = Application.NativeApplication.findByApplication(theApplication);
					if (theNative != null) {
						theInitializer = RfidInitializerFactory.NATIVE_INITIALIZERS_MAP.get(theNative);
					}
				}
			}
			if (theInitializer != null) {
				theInitializer.initRfid(rfid, batch.getDefaultAppletParam());
			}

		}

	}
}
