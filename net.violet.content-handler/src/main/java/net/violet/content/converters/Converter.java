package net.violet.content.converters;

import java.util.Set;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.ContentWrapper;

public interface Converter<C extends ContentWrapper> {

	boolean canGenerate(ContentType inType);

	Set<ContentType> getAvailableOutput();

	Set<ContentType> getAvailableInput();

	boolean canWorkWith(ContentType inType);

	TmpFile convert(C inContent, ContentType inputType, ContentType outputType);
}
