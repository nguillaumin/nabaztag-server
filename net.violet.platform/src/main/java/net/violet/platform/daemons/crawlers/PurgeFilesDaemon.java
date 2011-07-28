package net.violet.platform.daemons.crawlers;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.TimedBlockingExecutor;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.common.utils.concurrent.units.TimedProcessUnit;
import net.violet.db.records.Record.RecordWalker;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Files.FILE_DELETION_STATE;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class PurgeFilesDaemon extends AbstractCrawler {

	private static final Logger LOGGER = Logger.getLogger(PurgeFilesDaemon.class);

	static class PurgeFilesProcessUnit extends TimedProcessUnit<Files, Object, Object> {

		public PurgeFilesProcessUnit(Files inProcessFile, long inTime2Wait) {
			super(inProcessFile, null, inTime2Wait, null);
		}
	}

	/**
	 * Fichiers initiaux.
	 */
	private static final Files INITIAL_FILE = Factories.FILES.find(Files.NEW_CONTENT_FILE_ID);
	private static final Files INITIAL_FILE_GMAIL = Application.NativeApplication.GMAIL.getApplication().getProfile().getAnnounceFile();
	private static final Files INITIAL_FILE_TWITTER = Application.NativeApplication.TWITTER.getApplication().getProfile().getAnnounceFile();

	/**
	 * Processeur des contenus.
	 */
	private final RecordWalker<Files> mFilesWalker;
	private final BlockingExecutor<PurgeFilesProcessUnit> TIMED_BLOCKING_EXECUTOR;
	private static final long TIME2WAIT_BEFORE_DELETION = 5000;
	private final long mTime2Wait;
	public static final Pattern BROAD_MATCHER = Pattern.compile("^(broadcast\\/(data\\/)?broad\\/(podcast|\\d+)\\/)(.*)");

	/**
	 * Constructeur à partir des paramètres en ligne de commande.
	 */
	public PurgeFilesDaemon(String[] inArgs) {
		super(inArgs.clone());

		this.mFilesWalker = new RecordWalker<Files>() {

			public void process(Files inFile) {
				processFiles(inFile);
			}
		};

		final Worker<PurgeFilesProcessUnit> theWorker = new Worker<PurgeFilesProcessUnit>() {

			public void process(PurgeFilesProcessUnit inProcessUnit) {
				final Files theFile = inProcessUnit.get();
				final Matcher theMatcher = PurgeFilesDaemon.BROAD_MATCHER.matcher(theFile.getPath());

				if (!((theFile.getPath() == null) || net.violet.common.StringShop.EMPTY_STRING.equals(theFile.getPath())) && theMatcher.matches()) {
					final Long theFileId = theFile.getId();
					final FILE_DELETION_STATE deletionResult = Factories.FILES.deleteFiles(theFile);
					// On vérifie que personne ne l'utilise.
					if (deletionResult == FILE_DELETION_STATE.REFERRED_TO) {// Logger si on trouve un files avec un BB et des références
						PurgeFilesDaemon.LOGGER.warn("The file : " + theFileId + " had a bestBefore but is still associated with something.");
						theFile.unScheduleDeletion();
					} else if (deletionResult == FILE_DELETION_STATE.ERROR) {
						PurgeFilesDaemon.LOGGER.error("The file : " + theFileId + " could not be removed.");
					} else if (deletionResult == FILE_DELETION_STATE.DELETED) {
						PurgeFilesDaemon.LOGGER.info("The file : " + theFileId + " has been removed.");
					}
				}
			}

		};

		final LongOpt[] theLongOpts = new LongOpt[] { new LongOpt("nbElements", LongOpt.REQUIRED_ARGUMENT, null, 'e'), new LongOpt("nbThreads", LongOpt.OPTIONAL_ARGUMENT, null, 'p'), new LongOpt("time2Wait", LongOpt.OPTIONAL_ARGUMENT, null, 't'), };
		final Getopt theGetOpt = new Getopt(getClass().getSimpleName(), inArgs, "e:p:t:", theLongOpts);
		int nbThreads = 1;
		int nbElements = 0;
		long time2WaitBeforeExecution = PurgeFilesDaemon.TIME2WAIT_BEFORE_DELETION;
		int theOption;
		while ((theOption = theGetOpt.getopt()) != -1) {
			switch (theOption) {

			case 'e':
				nbElements = Integer.parseInt(theGetOpt.getOptarg());
				break;
			case 'p':
				nbThreads = Integer.parseInt(theGetOpt.getOptarg());
				break;
			case 't':
				time2WaitBeforeExecution = Long.parseLong(theGetOpt.getOptarg());
				break;

			}
		}
		this.mTime2Wait = time2WaitBeforeExecution;
		this.TIMED_BLOCKING_EXECUTOR = new TimedBlockingExecutor<PurgeFilesProcessUnit>(nbThreads, nbElements, theWorker, this.getClass().getName());
	}

	/**
	 * Itération.
	 */
	@Override
	protected void process() {
		Factories.FILES.walk(this.mFilesWalker);
		try {
			this.TIMED_BLOCKING_EXECUTOR.waitTermination();
		} catch (final InterruptedException e) {
			PurgeFilesDaemon.LOGGER.fatal(e, e);
		}
	}

	/**
	 * Suppression d'un contenu et du fichier associé.
	 */
	private void processFiles(Files inFile) {
		// Récupération du fichier.
		final Long theFileId = inFile.getId();
		if (!PurgeFilesDaemon.INITIAL_FILE.getId().equals(theFileId) && !PurgeFilesDaemon.INITIAL_FILE_GMAIL.getId().equals(theFileId) && !PurgeFilesDaemon.INITIAL_FILE_TWITTER.getId().equals(theFileId)) {
			try {
				this.TIMED_BLOCKING_EXECUTOR.put(new PurgeFilesProcessUnit(inFile, this.mTime2Wait));
			} catch (final InterruptedException e) {
				PurgeFilesDaemon.LOGGER.fatal(e, e);
			}
		} else {// Logger si on trouve un files avec un BB et des références
			PurgeFilesDaemon.LOGGER.info("The file : " + theFileId + " had a bestBefore but still one of the Untouchable.");
			inFile.unScheduleDeletion();
		}
	}

}
