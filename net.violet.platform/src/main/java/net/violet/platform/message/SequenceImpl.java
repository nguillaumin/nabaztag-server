package net.violet.platform.message;

/**
 * Classe pour une séquence dans un message.
 */
public class SequenceImpl implements Sequence {

	private final int mType;
	private final String mData;

	public SequenceImpl(int inType, String inData) {
		this.mType = inType;
		this.mData = inData;
	}

	SequenceImpl(String inType, String inData) {
		this.mType = MessageDump.stringToSequence(inType);
		this.mData = inData;
	}

	public String getData() {
		return this.mData;
	}

	public int getType() {
		return this.mType;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SequenceImpl)) {
			return false;
		}

		final SequenceImpl theObject = (SequenceImpl) obj;
		return theObject.getData().equals(getData()) && (theObject.getType() == getType());
	}

	@Override
	public int hashCode() {
		return this.mData.hashCode() + this.mType;
	}
}
