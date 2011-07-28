package net.violet.platform.message;

/**
 * Interface pour les séquences. Les séquences sont stockées dans la base (table
 * evseq) et représentent les atomes des messages communs aux v1 et aux v2.
 */
public interface Sequence {

	/**
	 * Accesseur sur le type.
	 * 
	 * @return le type de la séquence.
	 */
	int getType();

	/**
	 * Accesseur sur la donnée.
	 * 
	 * @return la donnée associée.
	 */
	String getData();

	int SEQ_MUSICDOWNLOAD = 0;
	int SEQ_MUSICSHOULDSTREAM = 1; //Stream url link 
	int SEQ_MUSICSIGN = 3;
	int SEQ_CHORDOWNLOAD = 4;
	int SEQ_CHORSTREAMING = 5;
	int SEQ_COLOR = 6;
	int SEQ_PALETTE = 7;
	int SEQ_BEGIN_INTERACTIVE_MODE = 8;
	int SEQ_END_INTERACTIVE_MODE = 9;
	int SEQ_SECURESTREAMING = 10;
	int SEQ_SETTING = 11;
	int SEQ_MUSIC_STREAMING = 12; //Stream several files
	int SEQ_STREAMING_ID = 13;
	int SEQ_STREAMING_STOP = 14;
	int SEQ_WAIT = 15;
}
