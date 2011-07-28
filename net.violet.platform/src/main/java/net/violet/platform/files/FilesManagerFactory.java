package net.violet.platform.files;

public class FilesManagerFactory {

	private static final RscManager RSC_MANAGER;
	private static final HadoopManager HADOOP_MANAGER;
	private static final DualFilesManager DUAL_FILES_MANAGER;
	private static final HttpManager HTTP_MANAGER;

	static {
		FilesManager theManager;

		try {
			/*
			 * TEMPORARY FIX : eviter les erreurs d'initialisation de hadoop
			 */
			theManager = null; // new HadoopManager();
		} catch (final Exception e) {
			theManager = null;
		}

		HADOOP_MANAGER = (HadoopManager) theManager;

		try {
			theManager = new RscManager();
		} catch (final Exception e) {
			theManager = null;
		}

		RSC_MANAGER = (RscManager) theManager;

		try {
			theManager = new DualFilesManager();
		} catch (final Exception e) {
			theManager = null;
		}

		DUAL_FILES_MANAGER = (DualFilesManager) theManager;

		try {
			theManager = new HttpManager();
		} catch (final Exception e) {
			theManager = null;
		}

		HTTP_MANAGER = (HttpManager) theManager;

	}

	public static final FilesManager FILE_MANAGER = FilesManagerFactory.getRscManager();
	public static final FilesManager ALTERNATE_FILE_MANAGER = FilesManagerFactory.getAlternateFileManager();

	static FilesManager getFilesManager() {
		return FilesManagerFactory.getRscManager();
	}

	public static HadoopManager getHadoopManager() {
		return FilesManagerFactory.HADOOP_MANAGER;
	}

	public static RscManager getRscManager() {
		return FilesManagerFactory.RSC_MANAGER;
	}

	public static DualFilesManager getDualFilesManager() {
		return FilesManagerFactory.DUAL_FILES_MANAGER;
	}

	private static FilesManager getAlternateFileManager() {
		if (FilesManagerFactory.FILE_MANAGER.equals(FilesManagerFactory.RSC_MANAGER) && (FilesManagerFactory.HADOOP_MANAGER != null)) {
			return FilesManagerFactory.HADOOP_MANAGER;
		}
		return FilesManagerFactory.RSC_MANAGER;
	}

	/**
	 * TODO this method is public only to be used by the HadoopCrawler, as soon as possible switch it to 'default' visibility.
	 * @return
	 */
	public static HttpManager getHttpManager() {
		return FilesManagerFactory.HTTP_MANAGER;
	}

}
