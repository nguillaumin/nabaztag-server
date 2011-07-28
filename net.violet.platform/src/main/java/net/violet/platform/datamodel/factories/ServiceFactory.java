package net.violet.platform.datamodel.factories;

import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Service;

public interface ServiceFactory extends RecordFactory<Service> {

	enum SERVICE {
		RSS("rss"),
		PODCAST("podcast"),
		CALLBACK("callback"),
		TWITTER("twitter"),
		FACEBOOK("facebook"),
		FILCKR("flickr"),
		GMAIL("gmail"),
		LIVE_MESH("liveMesh");

		private static final Map<String, SERVICE> LABEL_SERVICE = new HashMap<String, SERVICE>();

		static {
			for (final SERVICE aService : SERVICE.values()) {
				SERVICE.LABEL_SERVICE.put(aService.getLabel(), aService);
			}
		}

		private final Service mService;

		private SERVICE(String inLabel) {
			this.mService = Factories.SERVICE.findByLabel(inLabel);
		}

		protected SERVICE findByLabel(String inLabel) {
			return SERVICE.LABEL_SERVICE.get(inLabel);
		}

		public Service getService() {
			return this.mService;
		}

		public String getLabel() {
			return this.mService.getLabel();
		}

	}

	Service findByLabel(String inName);

}
