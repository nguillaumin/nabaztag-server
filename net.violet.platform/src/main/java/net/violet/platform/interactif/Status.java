package net.violet.platform.interactif;

public class Status {

	/**
	 * Origine de l'interruption
	 * 
	 * @author thierry
	 */
	public enum Source {
		EAR {

			@Override
			protected String getKeyWord() {
				return "ear";
			}
		},
		BUTTON {

			@Override
			protected String getKeyWord() {
				return "btn";
			}
		},
		RECO {

			@Override
			protected String getKeyWord() {
				return "reco";
			}
		},
		RFID {

			@Override
			protected String getKeyWord() {
				return "rfid";
			}
		},
		START {

			@Override
			protected String getKeyWord() {
				return "start";
			}
		},
		DONE {

			@Override
			protected String getKeyWord() {
				return "done";
			}
		};

		protected abstract String getKeyWord();

		public static Source getByName(String inName) {
			for (final Source s : Source.values()) {
				if (s.getKeyWord().equals(inName)) {
					return s;
				}
			}
			return null;
		}

		@Override
		public String toString() {
			return this.name() + " EVENT";
		}
	}

	private final Source mSource;
	private final int mSourceValue;
	private final int mEarLeft;
	private final int mEarRight;
	private byte[] mRecoFile;

	public Status(Source inSource, int inValue, int inEarLeft, int inEarRight) {
		this.mSource = inSource;
		this.mSourceValue = inValue;
		this.mEarLeft = inEarLeft;
		this.mEarRight = inEarRight;
	}

	public Source getSource() {
		return this.mSource;
	}

	public int getValue() {
		return this.mSourceValue;
	}

	public int getEarLeft() {
		return this.mEarLeft;
	}

	public int getEarRight() {
		return this.mEarRight;
	}

	public void setRecoFile(byte[] recoFile) {
		this.mRecoFile = recoFile;
	}

	public byte[] getRecoFile() {
		return this.mRecoFile;
	}

}
