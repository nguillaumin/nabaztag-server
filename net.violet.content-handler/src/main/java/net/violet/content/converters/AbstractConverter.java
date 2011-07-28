package net.violet.content.converters;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.ContentWrapper;
import net.violet.content.AbstractAlterator;

public abstract class AbstractConverter<C extends ContentWrapper> extends AbstractAlterator implements Converter<C> {

	public boolean canGenerate(ContentType inType) {
		return getAvailableOutput().contains(inType);
	}

	public boolean canWorkWith(ContentType inType) {
		return getAvailableInput().contains(inType);
	}

	public final TmpFile convert(C inContent, ContentType inputType, ContentType outputType) {
		if (canWorkWith(inputType) && canGenerate(outputType)) {
			return doConvert(inContent, inputType, outputType);
		}

		throw new UnsupportedOperationException();
	}

	protected abstract TmpFile doConvert(C inContent, ContentType inputType, ContentType outputType);
}
