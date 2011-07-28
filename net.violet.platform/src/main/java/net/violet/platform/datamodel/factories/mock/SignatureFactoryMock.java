package net.violet.platform.datamodel.factories.mock;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Signature;
import net.violet.platform.datamodel.Signature.ColorType;
import net.violet.platform.datamodel.factories.SignatureFactory;
import net.violet.platform.datamodel.mock.SignatureMock;

public class SignatureFactoryMock extends RecordFactoryMock<Signature, SignatureMock> implements SignatureFactory {

	protected SignatureFactoryMock() {
		super(SignatureMock.class);
	}

	public Signature create(Files inFile, Anim inAnim, ColorType inColor) {
		return new SignatureMock(inFile, inAnim, inColor);
	}

	public Signature get(Files inFile, Anim inAnim, ColorType inColor) {
		final Signature theSignature = find(inFile, inAnim, inColor);

		if (theSignature == null) {
			return create(inFile, inAnim, inColor);
		}

		return theSignature;
	}

	public Signature find(Files inFile, Anim inAnim, ColorType inColor) {
		for (final Signature aSignature : findAll()) {
			if (aSignature.getFile().equals(inFile) && aSignature.getAnim().equals(inAnim) && (aSignature.getColor() == inColor)) {
				return aSignature;
			}
		}
		return null;
	}

	public boolean usesFiles(Files inFile) {
		return false;
	}

}
