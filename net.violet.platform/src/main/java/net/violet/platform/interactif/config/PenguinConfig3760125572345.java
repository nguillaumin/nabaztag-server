package net.violet.platform.interactif.config;

import java.util.Arrays;
import java.util.List;

/**
 * Classe de configuration pour l'application lecture d'un audio book : The
 * Elves and the Shoemaker
 */
public class PenguinConfig3760125572345 extends AbstractPlayerConfig {

	/** The Elves and the Shoemaker */
	public static final Long ISBN = 3760125572345L;

	private static final List<String> LIST_PATH_STREAM = Arrays.asList("35");

	private static final List<int[]> LIST_INDEXFILES = Arrays.asList(new int[][] { { 0, 20000, 520000, 1032000, 1412000, 1944000, 2520000 } });

	public PenguinConfig3760125572345() {
		super(PenguinConfig3760125572345.ISBN, AbstractPlayerConfig.PATH_SONG_EN);

	}

	@Override
	protected List<int[]> getIndexFiles() {
		return PenguinConfig3760125572345.LIST_INDEXFILES;
	}

	@Override
	protected List<String> getPathStream() {
		return PenguinConfig3760125572345.LIST_PATH_STREAM;
	}

}
