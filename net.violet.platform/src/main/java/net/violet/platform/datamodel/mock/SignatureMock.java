package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Anim;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Signature;
import net.violet.platform.datamodel.factories.Factories;

public class SignatureMock extends AbstractMockRecord<Signature, SignatureMock> implements Signature {

	protected long fileId;
	protected long animId;
	protected ColorType color;

	protected SignatureMock(long fileId, long animId, ColorType color) {
		super(0L);
		this.fileId = fileId;
		this.animId = animId;
		this.color = color;
	}

	public SignatureMock(Files inFile, Anim inAnim, ColorType inColor) {
		this(inFile.getId(), inAnim.getId(), inColor);
	}

	public Anim getAnim() {
		return Factories.ANIM.find(this.animId);
	}

	public ColorType getColor() {
		return this.color;
	}

	public Files getFile() {
		return Factories.FILES.find(this.fileId);
	}

}
