package net.violet.content.converters;

public enum ContentType {
	PCM_8, // 8KHz
	PCM_22, // 22 KHz
	WAV,
	WAV_16, // 16KHz
	WAV_8, // 8KHz
	MP3,
	AAC,
	FLV,
	MP4,
	ADP,
	CHOR,
	CHOR_CDL,
	MP3_32 { // 32000 Hz

		@Override
		public int getBitRate() {
			return 32;
		}
	},
	MP3_128 { //44KHz

		@Override
		public int getBitRate() {
			return 128;
		}
	};

	public int getBitRate() {
		return 0;
	}
}
