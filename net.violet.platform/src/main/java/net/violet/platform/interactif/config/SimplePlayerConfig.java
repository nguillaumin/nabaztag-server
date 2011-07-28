package net.violet.platform.interactif.config;

import java.util.Arrays;
import java.util.List;

import net.violet.platform.util.StringShop;

/**
 * Classe de configuration pour l'application lecture d'un audio book : Cube
 * Pour l'instant il n'y a pas de suffixe (ISBN) dans le nom de la classe car on
 * a qu'un seul modéle
 */
public class SimplePlayerConfig extends AbstractPlayerConfig {

	/** Cube */
	public static final Long ISBN = 1000000000000L;

	private static final List<String> LIST_PATH_STREAM = Arrays.asList("22");

	private static final List<int[]> LIST_INDEXFILES = Arrays.asList(new int[][] { { 0, 1000000, 2260000, 3604000 } }); // index

	public SimplePlayerConfig() {
		super(SimplePlayerConfig.ISBN, AbstractPlayerConfig.PATH_SONG_FR);
	}

	@Override
	public String getMusicHelpInit() {
		return StringShop.EMPTY_STRING;
	}

	@Override
	public String getMusicHelpLong() {
		return StringShop.EMPTY_STRING;
	}

	@Override
	public String getMusicHelpShort() {
		return StringShop.EMPTY_STRING;
	}

	@Override
	public String getMusicBye() {
		return StringShop.EMPTY_STRING;
	}

	@Override
	public String getMusicEndBook() {
		return StringShop.EMPTY_STRING;
	}

	@Override
	protected List<int[]> getIndexFiles() {
		return SimplePlayerConfig.LIST_INDEXFILES;
	}

	@Override
	protected List<String> getPathStream() {
		return SimplePlayerConfig.LIST_PATH_STREAM;
	}
}
