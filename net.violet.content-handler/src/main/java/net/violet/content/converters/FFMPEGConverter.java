package net.violet.content.converters;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.violet.common.StringShop;
import net.violet.common.utils.cache.CacheHandler;
import net.violet.common.utils.cache.CacheHandlerFactory;
import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.TmpFileWrapper;
import net.violet.content.ScriptConstantes;
import net.violet.content.ScriptProcessUnit;
import net.violet.content.ScriptProcessUnit.ScriptProcessUnitBuilder;

import org.apache.log4j.Logger;

public class FFMPEGConverter extends AbstractConverter<TmpFileWrapper> {

	private static final Logger LOGGER = Logger.getLogger(FFMPEGConverter.class);

	private static BlockingExecutor<ScriptProcessUnit<TmpFile>> FFMPEG_EXECUTOR = new BlockingExecutor<ScriptProcessUnit<TmpFile>>(ScriptConstantes.NB_FFMPEG, FILE_WORKER, "FFMPEG");

	/**
	 * Paire VoiceName & Text pour faire référence aux objets.
	 */
	private static final class FFMPEGCacheRef implements Serializable {

		private static final long serialVersionUID = 1L;

		private String optionLine;

		private String md5;

		/**
		 * Constructeur à partir d'une classe et de la valeur de hachage.
		 */
		private FFMPEGCacheRef(String inOptionLine, String inMd5) {
			this.optionLine = inOptionLine;
			this.md5 = inMd5;
		}

		@Override
		public int hashCode() {
			return this.optionLine.hashCode() ^ this.md5.hashCode();
		}

		@Override
		public boolean equals(Object inObject) {
			return (this == inObject) || ((inObject instanceof FFMPEGCacheRef) && ((FFMPEGCacheRef) inObject).md5.equals(this.md5) && ((FFMPEGCacheRef) inObject).optionLine.equals(this.optionLine));
		}

		/**
		 * Ecriture de l'objet.
		 * 
		 * @param inOutputStream flux pour l'écriture.
		 * @throws IOException
		 */
		private void writeObject(ObjectOutputStream inOutputStream) throws IOException {
			inOutputStream.writeUTF(this.md5);
			inOutputStream.writeUTF(this.optionLine);
		}

		/**
		 * Lecture de l'objet.
		 * 
		 * @param inInputStream flux pour la lecture.
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		private void readObject(ObjectInputStream inInputStream) throws IOException {
			this.md5 = inInputStream.readUTF();
			this.optionLine = inInputStream.readUTF();
		}

		@Override
		public String toString() {
			return optionLine;
		}
	}

	/**
	 * Reference the cache instance
	 */
	private static final CacheHandler<FFMPEGCacheRef, byte[]> CACHE_INSTANCE;

	static {
		CacheHandler<FFMPEGCacheRef, byte[]> theCacheHandler = null;
		try {
			theCacheHandler = CacheHandlerFactory.getInstance("FFMPEGCache");
		} catch (final Exception e) {
			LOGGER.fatal(e, e);
		}
		CACHE_INSTANCE = theCacheHandler;
	}

	@Override
	protected TmpFile doConvert(TmpFileWrapper inContent, ContentType inputType, ContentType outputType) {
		TmpFile theFile = null;

		TmpFile theTmpFile;
		try {
			theTmpFile = FILES_MANAGER.new TmpFile();
		} catch (IOException e) {
			LOGGER.fatal(e, e);
			return null;
		}

		try {
			theFile = inContent.get();
			final StringBuilder theOptionLine = new StringBuilder(ScriptConstantes.FFMPEG).append(StringShop.SPACE).append(theFile.getPath()).append(StringShop.SPACE);
			final int offset;
			final StringBuilder theArgsLine = new StringBuilder();

			if (inputType == ContentType.WAV) {
				theArgsLine.append("wav");
			} else if (inputType == ContentType.PCM_8) {
				theArgsLine.append("s16le");
				theArgsLine.append(" -ar 8000 ");
			} else if (inputType == ContentType.PCM_22) {
				theArgsLine.append("s16le");
				theArgsLine.append(" -ar 22000 ");
			} else if (inputType == ContentType.MP3) {
				theArgsLine.append("mp3");
			} else if (inputType == ContentType.FLV) {
				theArgsLine.append("flv");
			} else if (inputType == ContentType.MP4) {
				theArgsLine.append("mp4");
			} else if (inputType == ContentType.AAC) {
				theArgsLine.append("m4a");
			}

			offset = theArgsLine.append(StringShop.SPACE).length();

			theArgsLine.append(StringShop.SPACE);

			if (outputType == ContentType.MP3_128) {
				theArgsLine.append(" mp3 ");
				theArgsLine.append(" -ar 44100 ");
				theArgsLine.append(" -ab " + outputType.getBitRate() + "k ");
				theArgsLine.append(" -ac 2 ");
			} else {
				if (outputType == ContentType.WAV_16) {
					theArgsLine.append("fade t 0 45 5");
					theArgsLine.append(" wav ");
					theArgsLine.append(" -ar 16000 ");
					theArgsLine.append(" -t 45 ");
				} else if (outputType == ContentType.WAV_8) {
					theArgsLine.append(" wav ");
					theArgsLine.append(" -ar 8000 ");
				} else if (outputType == ContentType.MP3_32) {
					theArgsLine.append(" mp3 ");
					theArgsLine.append(" -ar 32000 ");
					theArgsLine.append(" -aq 60 "); //low audio quality
					theArgsLine.append(" -ab " + outputType.getBitRate() + "k ");
				} else {
					assert false : "No such output type !";
				}
				theArgsLine.append(" -ac 1 ");
			}

			final String argsLine = theArgsLine.toString();
			theArgsLine.insert(offset, theTmpFile.getPath());
			final ScriptProcessUnitBuilder<TmpFile> theBuilder = new ScriptProcessUnitBuilder<TmpFile>(null);
			final ThreadWatcher theWatcher = new ThreadWatcher();
			theBuilder.setAllButSource(theOptionLine.append(theArgsLine).toString(), false, theWatcher);
			final ScriptProcessUnit<TmpFile> theProcessUnit = theBuilder.build();
			theBuilder.clear();

			final String theMD5 = theFile.getMD5Sum();
			final FFMPEGCacheRef theKey;

			LOGGER.info(theMD5);

			if (theMD5 != null) {
				theKey = new FFMPEGCacheRef(argsLine, theMD5);
				final byte[] theCachedResult = CACHE_INSTANCE.get(theKey);

				if (theCachedResult != null) {
					theFile.write(theCachedResult);
					return theFile;
				}

			} else {
				theKey = null;
			}

			theProcessUnit.startWatching();
			try {
				FFMPEG_EXECUTOR.put(theProcessUnit);
				theWatcher.await();
			} catch (final InterruptedException e) {
				LOGGER.fatal(e, e);
			}

			if (!theProcessUnit.isError() && theFile.exists()) {

				if (theKey != null)
					CACHE_INSTANCE.add(theKey, theFile.getContent());

				return theFile;
			}

			inContent.clean();

		} finally {
			theTmpFile.delete();
			inContent.clear();
		}
		return null;
	}

	private static final Set<ContentType> AVAILABLE_INPUT = Collections.unmodifiableSet(new HashSet<ContentType>(Arrays.asList(ContentType.PCM_22, ContentType.PCM_8, ContentType.WAV, ContentType.MP3, ContentType.FLV, ContentType.MP4, ContentType.AAC)));
	private static final Set<ContentType> AVAILABLE_OUTPUT = Collections.unmodifiableSet(new HashSet<ContentType>(Arrays.asList(ContentType.WAV_16, ContentType.WAV_8, ContentType.MP3_128, ContentType.MP3_32)));

	public Set<ContentType> getAvailableOutput() {
		return AVAILABLE_OUTPUT;
	}

	public Set<ContentType> getAvailableInput() {
		return AVAILABLE_INPUT;
	}

}
