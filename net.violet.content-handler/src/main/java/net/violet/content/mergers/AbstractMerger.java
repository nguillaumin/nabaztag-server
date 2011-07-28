package net.violet.content.mergers;

import java.util.List;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.ContentWrapper;
import net.violet.content.AbstractAlterator;
import net.violet.content.converters.ContentType;

public abstract class AbstractMerger<C extends ContentWrapper> extends AbstractAlterator implements Merger<C> {

	public TmpFile merge(List<C> inFiles, ContentType input) {
		if (inFiles != null && !inFiles.isEmpty()) {
			return doMerge(inFiles, input);
		}

		throw new UnsupportedOperationException();
	}

	protected abstract TmpFile doMerge(List<C> inFiles, ContentType input);

}
