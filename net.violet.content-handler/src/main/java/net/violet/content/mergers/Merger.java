package net.violet.content.mergers;

import java.util.List;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.ContentWrapper;
import net.violet.content.converters.ContentType;

public interface Merger<C extends ContentWrapper> {

	TmpFile merge(List<C> inFiles, ContentType input);

	List<ContentType> getSupportedInput();

}
