package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Signature;
import net.violet.platform.datamodel.SignatureImpl;
import net.violet.platform.datamodel.Signature.ColorType;
import net.violet.platform.datamodel.factories.SignatureFactory;

import org.apache.log4j.Logger;

public class SignatureFactoryImpl extends RecordFactoryImpl<Signature, SignatureImpl> implements SignatureFactory {

	private static final Logger LOGGER = Logger.getLogger(SignatureFactoryImpl.class);

	protected SignatureFactoryImpl() {
		super(SignatureImpl.SPECIFICATION);
	}

	public Signature create(Files inFile, Anim inAnim, ColorType inColor) {
		try {
			return new SignatureImpl(inFile, inAnim, inColor);
		} catch (final SQLException e) {
			SignatureFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	public Signature get(Files inFile, Anim inAnim, ColorType inColor) {
		Signature theSignature = null;

		do {
			theSignature = find(inFile, inAnim, inColor);

			if (theSignature == null) {
				theSignature = create(inFile, inAnim, inColor);
			}

		} while (theSignature == null);

		return theSignature;
	}

	public Signature find(Files inFile, Anim inAnim, ColorType inColor) {
		return find("file_id = ? AND anim_id = ? AND color = ?", Arrays.asList(new Object[] { inFile.getId(), inAnim.getId(), inColor.getValue() }));
	}

	public boolean usesFiles(Files inFile) {
		//TODO Uncomment when the table will used
		//return count(null, " file_id = ? ", Collections.singletonList((Object) inFile.getId()), null) > 0;
		return false;
	}
}
