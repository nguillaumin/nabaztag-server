package net.violet.platform.interactif.config;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de configuration pour l'application lecture d'un audio book : Le
 * Monstre Poilu
 */
public class GallimardConfig3760125572307 extends AbstractPlayerConfig {

	/** Le Monstre Poilu */
	public static final Long ISBN = 3760125572307L;

	private static final List<String> LIST_PATH_STREAM = Arrays.asList("12"); // son avec clochette

	private static final List<int[]> LIST_INDEXFILES = Arrays.asList(new int[][] { { 0, 82000, 386000, 644000, 960000, 1272000, 1506000 } }); // index avec clochette

	public GallimardConfig3760125572307() {
		super(GallimardConfig3760125572307.ISBN, AbstractPlayerConfig.PATH_SONG_FR);
	}

	@Override
	protected List<int[]> getIndexFiles() {
		return GallimardConfig3760125572307.LIST_INDEXFILES;
	}

	@Override
	protected List<String> getPathStream() {
		return GallimardConfig3760125572307.LIST_PATH_STREAM;
	}

}
