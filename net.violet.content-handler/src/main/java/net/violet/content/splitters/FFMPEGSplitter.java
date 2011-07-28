package net.violet.content.splitters;

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

public class FFMPEGSplitter extends AbstractSplitter<TmpFileWrapper> {

	private static final Logger LOGGER = Logger.getLogger(FFMPEGSplitter.class);

	private static BlockingExecutor<ScriptProcessUnit<TmpFile>> FFMPEG_EXECUTOR = new BlockingExecutor<ScriptProcessUnit<TmpFile>>(ScriptConstantes.NB_FFMPEG, FILE_WORKER, "FFMPEG");
	private static final List<ContentType> INPUTS = Arrays.asList(new ContentType[] { ContentType.MP3, ContentType.MP3_32, ContentType.MP3_128, ContentType.MP4, ContentType.FLV, ContentType.AAC });

	public List<ContentType> getSupportedInput() {
		return INPUTS;
	}

	@Override
	protected TmpFile doSplit(TmpFileWrapper inContent, ContentType inType, int from, int length) {

		ScriptProcessUnit<TmpFile> theProcessUnit = null;

		TmpFile theOutputFile;
		try {
			theOutputFile = FILES_MANAGER.new TmpFile();
		} catch (IOException e) {
			LOGGER.fatal(e, e);
			return null;
		}

		try {
			final StringBuilder theOptionLine = new StringBuilder(ScriptConstantes.FFMPEG_SPLIT).append(StringShop.SPACE).append(theOutputFile.getPath()).append(StringShop.SPACE);
			theOptionLine.append(from).append(StringShop.SPACE).append(length).append(StringShop.SPACE);

			if (inType == ContentType.MP3 || inType == ContentType.MP3_32 || inType == ContentType.MP3_128) {
				theOptionLine.append(" mp3 ");
			} else if (inType == ContentType.FLV) {
				theOptionLine.append(" flv ");
			} else if (inType == ContentType.MP4) {
				theOptionLine.append(" mp4 ");
			} else if (inType == ContentType.AAC) {
				theOptionLine.append(" m4a ");
			}

			final ScriptProcessUnitBuilder<TmpFile> theBuilder = new ScriptProcessUnitBuilder<TmpFile>(inContent.get());
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

			if (!theProcessUnit.isError() && theOutputFile.exists()) {
				return theOutputFile;
			}

			inContent.clean();

		} finally {
			if (theProcessUnit != null && theProcessUnit.isError()) {
				theOutputFile.delete();
			}

			inContent.clear();
		}
		return null;
	}

}
