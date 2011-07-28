package net.violet.platform.interactif.config;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de configuration pour l'application lecture d'un audio book : Au Loup
 * Tordu
 */
public class GallimardConfig3760125572253 extends AbstractPlayerConfig {

	/** Au Loup Tordu */
	public static final Long ISBN = 3760125572253L;

	private static final List<String> LIST_PATH_STREAM = Arrays.asList("8"); // son avec clochette

	private static final List<int[]> LIST_INDEXFILES = Arrays.asList(new int[][] { { 0, 13200, 384000, 748000, 1032000, 1488000, 1908000 } }); // index avec clochette

	public GallimardConfig3760125572253() {
		super(GallimardConfig3760125572253.ISBN, AbstractPlayerConfig.PATH_SONG_FR);
	}

	@Override
	protected List<int[]> getIndexFiles() {
		return GallimardConfig3760125572253.LIST_INDEXFILES;
	}

	@Override
	protected List<String> getPathStream() {
		return GallimardConfig3760125572253.LIST_PATH_STREAM;
	}

}
