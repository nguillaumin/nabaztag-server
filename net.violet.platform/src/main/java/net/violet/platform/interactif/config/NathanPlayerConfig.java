package net.violet.platform.interactif.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.NathanMp3;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

/**
 * Classe de configuration pour l'application lecture d'un audio book : Nathan
 */
public class NathanPlayerConfig extends AbstractPlayerConfig {

	public static final Map<Long, HashMap<String, String>> LIST_PATH_STREAM_OFFICIAL_VERSION = new HashMap<Long, HashMap<String, String>>();
	public static final Map<Long, String> BOOKSROOT = new HashMap<Long, String>();

	public static final String VERSION_SIZE = "version_size";

	public static final String VERSION_ID = "version_id";

	public static final String VERSION_PATH_SECURE = "version_path_secure";

	static {
		// TODO pour l'instant on récupère ici les versions des bouquins Nathan
		// (il y en a que 6)
		final HashMap<String, String> violette = new HashMap<String, String>();
		violette.put(NathanPlayerConfig.VERSION_PATH_SECURE, "15");
		violette.put(NathanPlayerConfig.VERSION_SIZE, "1045420");
		violette.put(NathanPlayerConfig.VERSION_ID, "1");
		violette.put("author", "Hubert Ben Kemoun & Peggy Nille");
		violette.put("title", "Violette dans le noir");

		final HashMap<String, String> costaud = new HashMap<String, String>();
		costaud.put(NathanPlayerConfig.VERSION_PATH_SECURE, "17");
		costaud.put(NathanPlayerConfig.VERSION_SIZE, "666018");
		costaud.put(NathanPlayerConfig.VERSION_ID, "2");
		costaud.put("author", "Robin");
		costaud.put("title", "C'est qui le plus costaud ?");

		final HashMap<String, String> feuille = new HashMap<String, String>();
		feuille.put(NathanPlayerConfig.VERSION_PATH_SECURE, "18");
		feuille.put(NathanPlayerConfig.VERSION_SIZE, "947827");
		feuille.put(NathanPlayerConfig.VERSION_ID, "3");
		feuille.put("author", "Sophie Pavlovsky & Barroux");
		feuille.put("title", "Petite feuille et le grand chêne");

		final HashMap<String, String> doudou = new HashMap<String, String>();
		doudou.put(NathanPlayerConfig.VERSION_PATH_SECURE, "19");
		doudou.put(NathanPlayerConfig.VERSION_SIZE, "742922");
		doudou.put(NathanPlayerConfig.VERSION_ID, "100");
		doudou.put("author", "Françoise Bobe & Claire Le Grand");
		doudou.put("title", "Le doudou de Siyabou");

		NathanPlayerConfig.LIST_PATH_STREAM_OFFICIAL_VERSION.put(9782092512593L, violette);
		NathanPlayerConfig.LIST_PATH_STREAM_OFFICIAL_VERSION.put(9782092512456L, costaud);
		NathanPlayerConfig.LIST_PATH_STREAM_OFFICIAL_VERSION.put(9782092513439L, feuille);
		NathanPlayerConfig.LIST_PATH_STREAM_OFFICIAL_VERSION.put(9782092508053L, doudou);

		NathanPlayerConfig.BOOKSROOT.put(9782092512593L, "vdln");
		NathanPlayerConfig.BOOKSROOT.put(9782092512456L, "costaud");
		NathanPlayerConfig.BOOKSROOT.put(9782092513439L, "pfgc");
		NathanPlayerConfig.BOOKSROOT.put(9782092508053L, "doudou");
	}

	private Long isbn;

	private Long version;

	public static final String VERSIONS = "versions";

	// utilisé juste pour le stream sécurisé
	private final List<String> list_path_stream = new ArrayList<String>();

	private final List<int[]> list_indexfiles = new ArrayList<int[]>();

	private final List<Files> list_files = new ArrayList<Files>();

	private NathanVersion nathanVersion;

	public NathanPlayerConfig(Long inISBN, Long inVersion) {
		super(inISBN, AbstractPlayerConfig.PATH_SONG_FR);
		final NathanVersion theResult = Factories.NATHAN_VERSION.find(inVersion);
		if (theResult != null) {
			this.isbn = inISBN;
			this.version = inVersion;
			if (theResult.getOfficial()) {
				this.list_path_stream.add(NathanPlayerConfig.LIST_PATH_STREAM_OFFICIAL_VERSION.get(inISBN).get(NathanPlayerConfig.VERSION_PATH_SECURE).toString());
			}
			final List<NathanMp3> theMp3List = Factories.NATHAN_MP3.findAllMp3ByVersion(theResult);
			final int[] tab = new int[theMp3List.size()];
			int i = 0;
			for (final NathanMp3 mp3 : theMp3List) {
				tab[i] = mp3.getOffset();
				this.list_files.add(i, mp3.getFile());
				i++;
			}
			this.list_indexfiles.add(tab);
			this.nathanVersion = theResult;
		}
	}

	public NathanPlayerConfig(Long inISBN) {
		super(inISBN, AbstractPlayerConfig.PATH_SONG_FR);
		this.isbn = inISBN;
	}

	@Override
	public String getMusicEndBook() {
		return AbstractPlayerConfig.PATH_SONG_FR + AbstractPlayerConfig.SHORT_INTRO_NATHAN_MP3;
	}

	@Override
	public String getMusicHelpLong() {
		return AbstractPlayerConfig.PATH_SONG_FR + AbstractPlayerConfig.LONG_INTRO_NATHAN_MP3;
	}

	public String getCountFinish(long inVersion) {
		return this.isbn + StringShop.UNDERSCORE + this.COUNT_FINISH + StringShop.UNDERSCORE + inVersion;
	}

	public String getAllVersions() {
		return this.isbn + StringShop.UNDERSCORE + NathanPlayerConfig.VERSIONS;
	}

	public long getVersion() {
		return this.version;
	}

	public NathanVersion getNathanVersion() {
		return this.nathanVersion;
	}

	public List<Files> getFilesList() {
		return this.list_files;
	}

	@Override
	protected List<int[]> getIndexFiles() {
		return this.list_indexfiles;
	}

	@Override
	protected List<String> getPathStream() {
		return this.list_path_stream;
	}

}
