package net.violet.content;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import net.violet.common.utils.concurrent.BlockingExecutorLight;
import net.violet.common.utils.concurrent.BlockingExecutorLight.Worker;
import net.violet.common.utils.io.TmpFileManager;
import net.violet.common.utils.io.TmpFileManager.TmpFile;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public abstract class AbstractAlterator {

	private static final Logger LOGGER = Logger.getLogger(AbstractAlterator.class);

	protected static TmpFileManager FILES_MANAGER = null;
	protected static final BlockingExecutorLight.Worker<ScriptProcessUnit<TmpFile>> FILE_WORKER;
	protected static final BlockingExecutorLight.Worker<ScriptProcessUnit<InputStream>> STREAM_WORKER;

	static {
		try {
			FILES_MANAGER = new TmpFileManager(ScriptConstantes.LOCAL_TMP_PATH);
		} catch (FileNotFoundException e) {
			LOGGER.fatal(e, e);
		}

		abstract class WorkerImpl<T> implements Worker<ScriptProcessUnit<T>> {

			private final long CONVERSION_TTL = 1200000L; // 20 mins
			private final Timer CONVERSION_WATCHER = new Timer("LAME WATCHER");

			protected abstract void dealWithMaterial(T inMaterial, OutputStream inStream) throws IOException;

			public void process(final ScriptProcessUnit<T> inUnit) {
				try {
					inUnit.processing();
					LOGGER.debug(inUnit.getProcessConditioner());
					final Process proc = Runtime.getRuntime().exec(inUnit.getProcessConditioner());
					final TimerTask theTask = new TimerTask() {

						@Override
						public void run() {
							LOGGER.warn("KILLING : ");
							proc.destroy();
							try {
								proc.getInputStream().close();
								proc.getOutputStream().close();
								proc.getErrorStream().close();
							} catch (final IOException anException) {
								LOGGER.fatal(anException, anException);
							}
						}

					};

					try {
						CONVERSION_WATCHER.schedule(theTask, CONVERSION_TTL);

						final Object theMutex = new Object();

						// Le thread qui écrit dans le process.
						final Thread theThread = new Thread() {

							@Override
							public void run() {
								final T theMaterial = inUnit.get();

								final OutputStream theStream2Proc = proc.getOutputStream();
								try {
									if (theMaterial != null) {
										dealWithMaterial(theMaterial, theStream2Proc);
									}
								} catch (final IOException e) {
									LOGGER.fatal(e, e);
								} finally {
									try {
										theStream2Proc.close();
									} catch (final IOException e) {
										LOGGER.fatal(e, e);
									}

									inUnit.clear();
									synchronized (theMutex) {
										theMutex.notify();
									}
								}
							}
						};

						final byte[] theResult;
						synchronized (theMutex) {
							theThread.start();

							if (inUnit.getProcessTarget()) {
								final InputStream theStream = proc.getInputStream();
								try {
									inUnit.setResult(IOUtils.toByteArray(theStream));
								} finally {
									try {
										theStream.close();
									} catch (final IOException e) {
										// IGNORE
									}
								}
							}

							theMutex.wait();
							theResult = inUnit.getResult();
						}

						if ((proc.waitFor() != 0) || (inUnit.getProcessTarget() && ((theResult == null) || (theResult.length <= 0)))) {
							LOGGER.info("ERROR -- theResult = " + theResult);
							inUnit.setError();
							inUnit.setResult(null);
						}
					} finally {
						theTask.cancel();
						CONVERSION_WATCHER.purge();

						IOUtils.closeQuietly(proc.getInputStream());
						IOUtils.closeQuietly(proc.getOutputStream());
						IOUtils.closeQuietly(proc.getErrorStream());
					}
				} catch (final IOException e) {
					LOGGER.fatal(e, e);
					inUnit.setError();
				} catch (final InterruptedException e) {
					LOGGER.fatal(e, e);
					inUnit.setError();
				} finally {
					inUnit.stopWatching();

					if (!inUnit.isError()) {
						inUnit.processed();
					}
				}
			}
		}

		FILE_WORKER = new WorkerImpl<TmpFile>() {

			@Override
			protected void dealWithMaterial(TmpFile inMaterial, OutputStream instream) throws IOException {
				IOUtils.copy(new FileInputStream(inMaterial.get()), instream);
			}
		};

		STREAM_WORKER = new WorkerImpl<InputStream>() {

			@Override
			protected void dealWithMaterial(InputStream inMaterial, OutputStream instream) throws IOException {
				IOUtils.copy(inMaterial, instream);
			}

		};
	}
}
