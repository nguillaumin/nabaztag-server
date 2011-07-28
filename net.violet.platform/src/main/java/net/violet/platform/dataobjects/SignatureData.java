package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.Signature;
import net.violet.platform.datamodel.Signature.ColorType;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class SignatureData extends RecordData<Signature> {

	private static final Logger LOGGER = Logger.getLogger(SignatureData.class);

	public static SignatureData getData(Signature inSignature) {
		try {
			return RecordData.getData(inSignature, SignatureData.class, Signature.class);
		} catch (final InstantiationException e) {
			SignatureData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			SignatureData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			SignatureData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			SignatureData.LOGGER.fatal(e, e);
		}

		return null;
	}

	protected SignatureData(Signature inRecord) {
		super(inRecord);
	}

	public static SignatureData get(FilesData inFileData, AnimData inAnimData, ColorType inColor) {
		final Signature theSignature = Factories.SIGNATURE.get(inFileData.getRecord(), inAnimData.getRecord(), inColor);

		return (theSignature != null) ? SignatureData.getData(theSignature) : null;
	}

	public Long getId() {
		final Record theRecord = getRecord();

		if (theRecord != null) {
			return theRecord.getId();
		}

		return null;
	}
}
