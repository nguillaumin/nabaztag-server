package net.violet.platform.daemons.crawlers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.violet.db.records.Record.RecordWalker;
import net.violet.platform.datamodel.Annu;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.implementations.VObjectFactoryImpl;
import net.violet.platform.geocoding.Coordinates;
import net.violet.platform.geocoding.GeoCoder;
import net.violet.platform.geocoding.GeoCodingException;

/**
 * Migration crawler to set the objects coordinates up.
 */
public class GeoCodingCrawler {

	public static void main(String[] args) throws InterruptedException {

		final ExecutorService executor = Executors.newFixedThreadPool(10);

		final int amount = ((VObjectFactoryImpl) Factories.VOBJECT).walk("object.object_id = object_profile.object_id AND object_profile.object_longitudeGPS IS NULL AND annu.annu_user = object.object_owner AND annu.annu_country != '' ", null, new String[] { "annu", "object_profile" }, null, 0, null, null, new RecordWalker<VObject>() {

			public void process(final VObject object) {

				executor.execute(new Runnable() {

					public void run() {
						final Annu ownerProfile = object.getOwner().getAnnu();
						final String city = ownerProfile.getAnnu_city().equals(net.violet.common.StringShop.EMPTY_STRING) ? null : ownerProfile.getAnnu_city();
						try {
							final Coordinates coord = GeoCoder.geoCodeAddress(ownerProfile.getAnnu_country(), city);
							if (coord != null) {
								object.getProfile().setPositionGPS(coord.getLatitude(), coord.getLongitude());
							}
						} catch (final GeoCodingException e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

		GeoCodingCrawler.waitForTermination(executor);
		System.err.println(amount + " objects have been processed");

	}

	private static void waitForTermination(ExecutorService executor) throws InterruptedException {
		Thread.sleep(5000);

		executor.shutdown();
		System.err.println("executor shuted down");
		while (!executor.isTerminated()) {
			System.err.println("waiting for full termination");
			executor.awaitTermination(10, TimeUnit.SECONDS);
		}
		System.err.println("Terminated");
	}
}
