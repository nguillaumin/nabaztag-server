package net.violet.platform.interactif.config;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de configuration pour l'application lecture d'un audio book : Le Chat
 * Botté
 */
public class GallimardConfig3760125572246 extends AbstractPlayerConfig {

	/** Le Chat Botté */
	public static final Long ISBN = 3760125572246L;

	private static final List<String> LIST_PATH_STREAM = Arrays.asList("9");

	private static final List<int[]> LIST_INDEXFILES = Arrays.asList(new int[][] { { 0, 20000, 408000, 886000, 1436000, 1852000, 2390000 } });

	public GallimardConfig3760125572246() {
		super(GallimardConfig3760125572246.ISBN, AbstractPlayerConfig.PATH_SONG_FR);

	}

	@Override
	protected List<int[]> getIndexFiles() {
		return GallimardConfig3760125572246.LIST_INDEXFILES;
	}

	@Override
	protected List<String> getPathStream() {
		return GallimardConfig3760125572246.LIST_PATH_STREAM;
	}

}
