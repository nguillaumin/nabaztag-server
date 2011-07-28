package net.violet.platform.interactif.config;

import java.util.Arrays;
import java.util.List;

/**
 * Classe pour l'application lecture d'un audio book : La belle lisse poire.
 */
public class GallimardConfig9782070548064 extends AbstractPlayerConfig {

	/** La belle lisse poire */
	public static final Long ISBN = 9782070548064L;

	private static final List<String> LIST_PATH_STREAM = Arrays.asList("6", "7"); // son avec clochette

	private static final List<int[]> LIST_INDEXFILES = Arrays.asList(new int[][] { { 0, 28000, 480000, 764000, 1176000, 1567200, 1869000, 2109000 }, { 0, 35000, 437000, 668000, 1004000, 1300000, 1536000, 1705000 } }); // index avec clochette

	public GallimardConfig9782070548064() {
		super(GallimardConfig9782070548064.ISBN, AbstractPlayerConfig.PATH_SONG_FR);
	}

	@Override
	public int getCountVoice() {
		return 2;
	}

	@Override
	protected List<int[]> getIndexFiles() {
		return GallimardConfig9782070548064.LIST_INDEXFILES;
	}

	@Override
	protected List<String> getPathStream() {
		return GallimardConfig9782070548064.LIST_PATH_STREAM;
	}

}
