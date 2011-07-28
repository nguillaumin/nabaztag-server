package net.violet.platform.interactif.config;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de configuration pour l'application lecture d'un audio book :
 * Cinderella
 */
public class PenguinConfig3760125572352 extends AbstractPlayerConfig {

	/** Cinderella */
	public static final Long ISBN = 3760125572352L;

	private static final List<String> LIST_PATH_STREAM = Arrays.asList("37");

	private static final List<int[]> LIST_INDEXFILES = Arrays.asList(new int[][] { { 0, 20000, 604000, 1072000, 1480000, 1948000, 2368000, 2716000, 3060000, 3360000, 3800000 } });

	public PenguinConfig3760125572352() {
		super(PenguinConfig3760125572352.ISBN, AbstractPlayerConfig.PATH_SONG_EN);

	}

	@Override
	protected List<int[]> getIndexFiles() {
		return PenguinConfig3760125572352.LIST_INDEXFILES;
	}

	@Override
	protected List<String> getPathStream() {
		return PenguinConfig3760125572352.LIST_PATH_STREAM;
	}

}
