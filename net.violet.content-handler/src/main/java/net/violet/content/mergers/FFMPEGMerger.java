package net.violet.content.mergers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.violet.common.StringShop;
import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.TmpFileWrapper;
import net.violet.content.ScriptConstantes;
import net.violet.content.ScriptProcessUnit;
import net.violet.content.ScriptProcessUnit.ScriptProcessUnitBuilder;
import net.violet.content.converters.ContentType;

import org.apache.log4j.Logger;

public class FFMPEGMerger extends AbstractMerger<TmpFileWrapper> {

	private static final Logger LOGGER = Logger.getLogger(FFMPEGMerger.class);

	private static BlockingExecutor<ScriptProcessUnit<TmpFile>> FFMPEG_EXECUTOR = new BlockingExecutor<ScriptProcessUnit<TmpFile>>(ScriptConstantes.NB_FFMPEG, FILE_WORKER, "FFMPEG");
	private static final List<ContentType> INPUTS = Arrays.asList(new ContentType[] { ContentType.MP3, ContentType.MP3_32, ContentType.MP3_128, ContentType.MP4, ContentType.FLV, ContentType.AAC });

	public List<ContentType> getSupportedInput() {
		return INPUTS;
	}

	@Override
	protected TmpFile doMerge(List<TmpFileWrapper> inFiles, ContentType inType) {

		final TmpFile theTmpFile;
		try {
			theTmpFile = FILES_MANAGER.new TmpFile();
		} catch (IOException e) {
			LOGGER.fatal(e, e);
			return null;
		}

		ScriptProcessUnit<TmpFile> theProcessUnit = null;

		try {
			final StringBuilder theOptionLine = new StringBuilder(ScriptConstantes.FFMPEG_MERGE).append(StringShop.SPACE).append(theTmpFile.getPath());

			if (inType == ContentType.MP3 || inType == ContentType.MP3_32 || inType == ContentType.MP3_128) {
				theOptionLine.append(" mp3 ");
			} else if (inType == ContentType.FLV) {
				theOptionLine.append(" flv ");
			} else if (inType == ContentType.MP4) {
				theOptionLine.append(" mp4 ");
			} else if (inType == ContentType.AAC) {
				theOptionLine.append(" m4a ");
			}

			for (final TmpFileWrapper aWrapper : inFiles) {
				final TmpFile theFile = aWrapper.get();
				if (theFile.exists())
					theOptionLine.append(theFile.getPath()).append(StringShop.SPACE);
			}

			final ScriptProcessUnitBuilder<TmpFile> theBuilder = new ScriptProcessUnitBuilder<TmpFile>(null);
			final ThreadWatcher theWatcher = new ThreadWatcher();
			theBuilder.setAllButSource(theOptionLine.toString(), false, theWatcher);
			theProcessUnit = theBuilder.build();
			theBuilder.clear();
			theProcessUnit.startWatching();
			try {
				FFMPEG_EXECUTOR.put(theProcessUnit);
				theWatcher.await();
			} catch (final InterruptedException e) {
				LOGGER.fatal(e, e);
			}

			if (!theProcessUnit.isError() && theTmpFile.exists()) {
				return theTmpFile;
			}

		} finally {
			if (theProcessUnit != null && theProcessUnit.isError()) {
				theTmpFile.delete();
			}
			for (final TmpFileWrapper aWrapper : inFiles) {
				aWrapper.clean();
			}
		}
		return null;
	}
}
