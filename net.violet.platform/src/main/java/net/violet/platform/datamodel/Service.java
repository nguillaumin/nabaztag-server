package net.violet.platform.datamodel;

import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.factories.Factories;

/**
 * Used by the old site, deprecated.
 * 
 *
 */
public interface Service extends Record<Service> {

	Map<String, Service> serviceByNameCache = new HashMap<String, Service>();
	Map<Long, Service> serviceByIdCache = ServiceCommon.createCache();

	String getImg();

	String getLabel();

	String getLink();

	int getTtl();

	Files getIntro();

	Files getOutro();

	class ServiceCommon {

		/**
		 * Initiates the SrvCategImpl Cache: gets all the srvCateg by LangImpl
		 * which have services associated to them
		 * 
		 * @return
		 */
		protected static Map<Long, Service> createCache() {
			final Map<Long, Service> serviceList = new HashMap<Long, Service>();

			for (final Service service : Factories.SERVICE.findAllMapped().values()) {
				serviceList.put(service.getId(), service);
				Service.serviceByNameCache.put(service.getLabel(), service);
			}
			return serviceList;
		}
	}

}
