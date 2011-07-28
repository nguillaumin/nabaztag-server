package net.violet.platform.interactif.config;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de configuration pour l'application lecture d'un audio book : Le Petit
 * Chaperon Rouge
 */
public class GallimardConfig3760125572260 extends AbstractPlayerConfig {

	/** Le Petit Chaperon Rouge */
	public static final Long ISBN = 3760125572260L;

	private static final List<String> LIST_PATH_STREAM = Arrays.asList("5");

	private static final List<int[]> LIST_INDEXFILES = Arrays.asList(new int[][] { { 0, 20800, 324600, 624000, 905200, 1380000 } });

	public GallimardConfig3760125572260() {
		super(GallimardConfig3760125572260.ISBN, AbstractPlayerConfig.PATH_SONG_FR);
	}

	@Override
	protected List<int[]> getIndexFiles() {
		return GallimardConfig3760125572260.LIST_INDEXFILES;
	}

	@Override
	protected List<String> getPathStream() {
		return GallimardConfig3760125572260.LIST_PATH_STREAM;
	}

}
