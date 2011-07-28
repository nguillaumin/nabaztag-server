package net.violet.platform.handlers.rfid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Hardware;
import net.violet.platform.datamodel.Ztamp;
import net.violet.platform.datamodel.ZtampBatch;
import net.violet.platform.datamodel.ZtampBatchImpl;
import net.violet.platform.datamodel.ZtampImpl;
import net.violet.platform.datamodel.factories.Factories;

public class RfidBatchCreator {

	public static void main(String[] args) {
		if (args.length < 3) {
			RfidBatchCreator.printUsage();
			return;
		}

		final String filePath = args[0];
		final long batchId = Long.parseLong(args[1]);
		final long hardwareId = Long.parseLong(args[2]);
		final String pictureId = (args.length == 4) ? args[3] : null; //optional

		final File theFile = new File(filePath);
		if (!theFile.exists()) {
			System.err.println("This file does not exist : " + filePath);
			return;
		}

		final ZtampBatch theBatch = ZtampBatchImpl.find(batchId);
		if (theBatch == null) {
			System.err.println("This batch does not exist : " + batchId);
			return;
		}

		Files thePicture = null;
		if (pictureId != null) {

			thePicture = Factories.FILES.find(Long.parseLong(pictureId));
			if (thePicture == null) {
				System.err.println("This file does not exist : " + pictureId);
				return;
			}
		}

		final Hardware.HARDWARE theHardware = Hardware.HARDWARE.findById(hardwareId);
		if (theHardware == null) {
			System.err.println("This hardware does not exist : " + hardwareId);
			return;
		}

		try {
			final BufferedReader reader = new BufferedReader(new FileReader(theFile));
			RfidBatchCreator.installApplication(reader, theBatch, theHardware, thePicture);
		} catch (final Exception e) {
			System.err.println("An unexpected error occured : " + e);
		}

		System.exit(0);

	}

	private static void installApplication(BufferedReader reader, ZtampBatch batch, Hardware.HARDWARE hardware, Files picture) throws IOException {

		int successes = 0;
		int failed = 0;

		String serial;
		while ((serial = reader.readLine()) != null) {
			Ztamp theRfid = Factories.ZTAMP.findBySerial(serial);
			if ((theRfid == null) && !net.violet.common.StringShop.EMPTY_STRING.equals(serial)) {
				System.err.println("Creating ztamp : " + serial);
				try {
					theRfid = new ZtampImpl(serial, batch, picture, hardware);
				} catch (final SQLException e) {
					System.err.println("Couldn't create ztamp " + serial);
					failed++;
				}
			}

			if (theRfid != null) {
				theRfid.setBatch(batch);
				successes++;
			}
		}

		System.out.println(successes + " rfid successfully installed, " + failed + " fails");

	}

	private static void printUsage() {
		System.err.println("Three arguments needed : file path, batch id, hardware id. Optional Fourth argument : file id for picture (default null)");
	}
}
