package net.violet.content.splitters;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.ContentWrapper;
import net.violet.content.AbstractAlterator;
import net.violet.content.converters.ContentType;

public abstract class AbstractSplitter<C extends ContentWrapper> extends AbstractAlterator implements Splitter<C> {

	public TmpFile split(C inFile, ContentType inType, int from, int length) {
		if (from >= 0 && length > 0) {
			return doSplit(inFile, inType, from, length);
		}

		throw new UnsupportedOperationException();
	}

	protected abstract TmpFile doSplit(C inFile, ContentType inType, int from, int length);

}
