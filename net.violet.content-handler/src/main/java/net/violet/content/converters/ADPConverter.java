package net.violet.content.converters;

import java.io.IOException;

import java.util.Collections;
import java.util.Set;

import net.violet.common.utils.concurrent.BlockingExecutor;
import net.violet.common.utils.concurrent.ThreadWatcher;
import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.TmpFileWrapper;
import net.violet.content.ScriptConstantes;
import net.violet.content.ScriptProcessUnit;
import net.violet.content.ScriptProcessUnit.ScriptProcessUnitBuilder;

import org.apache.log4j.Logger;

public class ADPConverter extends AbstractConverter<TmpFileWrapper> {

	private static final Logger LOGGER = Logger.getLogger(ADPConverter.class);
	private static final BlockingExecutor<ScriptProcessUnit<TmpFile>> ADP_EXECUTOR = new BlockingExecutor<ScriptProcessUnit<TmpFile>>(ScriptConstantes.NB_ADP, FILE_WORKER, "ADP");

	@Override
	protected TmpFile doConvert(TmpFileWrapper inContent, @SuppressWarnings("unused") ContentType inputType, @SuppressWarnings("unused") ContentType outputType) {
		final TmpFile theAdp;
		try {
			theAdp = FILES_MANAGER.new TmpFile();
		} catch (IOException e) {
			LOGGER.fatal(e, e);
			return null;
		}

		try {
			final StringBuilder theBuilder = new StringBuilder(ScriptConstantes.RSCMNG_WAV2ADP).append(net.violet.common.StringShop.SPACE).append(inContent.get().getPath()).append(net.violet.common.StringShop.SPACE).append(theAdp.getPath());
			final ScriptProcessUnitBuilder<TmpFile> theUnitBuilder = new ScriptProcessUnitBuilder<TmpFile>(null);
			final ThreadWatcher theWatcher = new ThreadWatcher();
			theUnitBuilder.setAllButSource(theBuilder.toString(), false, theWatcher);
			final ScriptProcessUnit<TmpFile> theProcessUnit = theUnitBuilder.build();
			theUnitBuilder.clear();
			theProcessUnit.startWatching();

			try {
				ADP_EXECUTOR.put(theProcessUnit);
				theWatcher.await();
			} catch (final InterruptedException e) {
				LOGGER.fatal(e, e);
			}

			if (!theProcessUnit.isError())
				return theAdp;
			theAdp.delete();
		} finally {
			inContent.clean();
		}
		return null;
	}

	private static final Set<ContentType> AVAILABLE_INPUT = Collections.singleton(ContentType.WAV_16);
	private static final Set<ContentType> AVAILABLE_OUTPUT = Collections.singleton(ContentType.ADP);

	public Set<ContentType> getAvailableOutput() {
		return AVAILABLE_OUTPUT;
	}

	public Set<ContentType> getAvailableInput() {
		return AVAILABLE_INPUT;
	}

}
