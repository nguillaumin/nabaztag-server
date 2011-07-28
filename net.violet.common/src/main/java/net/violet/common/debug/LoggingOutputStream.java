package net.violet.common.debug;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * OutputStream pour un logger de log4j. Inspiré de:
 * http://www.mail-archive.com/log4j-user@logging.apache.org/msg05005.html
 */
public final class LoggingOutputStream extends OutputStream {

	/**
	 * Si le flux a été fermé.
	 */
	private boolean mHasBeenClosed;

	/**
	 * Buffer.
	 */
	private byte[] mBuffer;

	/**
	 * Nombre d'octets dans le buffer.
	 */
	private int mBufferSize;

	/**
	 * Taille du buffer par défaut.
	 */
	private static final int DEFAULT_BUFFER_LENGTH = 2048;

	/**
	 * Logger où on écrit.
	 */
	private final Logger mLogger;

	/**
	 * Niveau de log.
	 */
	private final Level mLevel;

	/**
	 * Constructeur à partir d'un logger et d'un niveau de log.
	 * 
	 * @param inLogger logger pour la redirection.
	 * @param inLevel niveau de log.
	 * @throws IllegalArgumentException
	 */
	public LoggingOutputStream(Logger inLogger, Level inLevel) throws IllegalArgumentException {
		if (inLogger == null) {
			throw new IllegalArgumentException("logger == null");
		}
		if (inLevel == null) {
			throw new IllegalArgumentException("level == null");
		}

		this.mLevel = inLevel;
		this.mLogger = inLogger;
		this.mBuffer = new byte[LoggingOutputStream.DEFAULT_BUFFER_LENGTH];
		this.mBufferSize = 0;
	}

	@Override
	public void close() {
		flush();
		this.mHasBeenClosed = true;
	}

	@Override
	public void write(final int b) throws IOException {
		if (this.mHasBeenClosed) {
			throw new IOException("The stream has been closed.");
		}

		final int bufLength = this.mBuffer.length;

		if (this.mBufferSize == bufLength) {
			final int newBufLength = bufLength + LoggingOutputStream.DEFAULT_BUFFER_LENGTH;
			final byte[] newBuf = new byte[newBufLength];
			System.arraycopy(this.mBuffer, 0, newBuf, 0, bufLength);
			this.mBuffer = newBuf;
		}

		this.mBuffer[this.mBufferSize] = (byte) b;
		this.mBufferSize++;
	}

	@Override
	public void flush() {
		if (this.mBufferSize > 0) {
			if (this.mBufferSize == 1) {
				final char char0 = (char) this.mBuffer[0];
				if ((char0 == '\n') || (char0 == '\r')) {
					reset();
					return;
				}
			} else if ((this.mBufferSize == 2) && ((char) this.mBuffer[0] == '\r') && ((char) this.mBuffer[1] == '\n')) {
				reset();
				return;
			}

			final byte[] theBytes = new byte[this.mBufferSize];
			System.arraycopy(this.mBuffer, 0, theBytes, 0, this.mBufferSize);
			this.mLogger.log(this.mLevel, new String(theBytes));
			reset();
		}
	}

	private void reset() {
		this.mBufferSize = 0;
	}
}
