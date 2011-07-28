package net.violet.content.splitters;

import java.util.List;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.ContentWrapper;
import net.violet.content.converters.ContentType;

public interface Splitter<C extends ContentWrapper> {

	TmpFile split(C inFile, ContentType inType, int from, int to);

	List<ContentType> getSupportedInput();

}
