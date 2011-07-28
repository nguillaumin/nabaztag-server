package net.violet.platform.interactif.config;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de configuration pour l'application lecture d'un audio book :
 * Goldilocks and the Three Bears
 */
public class PenguinConfig3760125572369 extends AbstractPlayerConfig {

	/** Goldilocks and the Three Bears */
	public static final Long ISBN = 3760125572369L;

	private static final List<String> LIST_PATH_STREAM = Arrays.asList("39");

	private static final List<int[]> LIST_INDEXFILES = Arrays.asList(new int[][] { { 0, 20000, 536000, 804000, 1104000, 1460000, 1800000, 2040000, 2268000, 2508000 } });

	public PenguinConfig3760125572369() {
		super(PenguinConfig3760125572369.ISBN, AbstractPlayerConfig.PATH_SONG_EN);

	}

	@Override
	protected List<int[]> getIndexFiles() {
		return PenguinConfig3760125572369.LIST_INDEXFILES;
	}

	@Override
	protected List<String> getPathStream() {
		return PenguinConfig3760125572369.LIST_PATH_STREAM;
	}

}
