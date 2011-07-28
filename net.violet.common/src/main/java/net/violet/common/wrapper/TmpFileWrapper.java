package net.violet.common.wrapper;

import java.io.File;

import net.violet.common.utils.io.TmpFileManager.TmpFile;

public class TmpFileWrapper extends ContentWrapper<TmpFile> {

	public TmpFileWrapper() {
		super(null);
	}

	public TmpFileWrapper(TmpFile inContent) {
		super(inContent);
	}

	/**
	 * Clears & removes the {@link File} from the FS
	 */
	@Override
	public void clean() {
		final TmpFile theFile = get();

		if (theFile != null) {
			theFile.delete();
		}
		clear();
	}

}
