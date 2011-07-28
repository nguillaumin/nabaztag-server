package net.violet.content.manager;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import net.violet.common.utils.io.TmpFileManager.TmpFile;
import net.violet.common.wrapper.TmpFileWrapper;
import net.violet.content.converters.ADPConverter;
import net.violet.content.converters.ContentType;
import net.violet.content.converters.Converter;
import net.violet.content.converters.FFMPEGConverter;
import net.violet.content.mergers.FFMPEGMerger;
import net.violet.content.mergers.Merger;
import net.violet.content.splitters.FFMPEGSplitter;
import net.violet.content.splitters.Splitter;

public class ConvertersManager {

	private final static Map<ContentType, Converter<TmpFileWrapper>> CONVERTERS_OUTPUT = new ConcurrentHashMap<ContentType, Converter<TmpFileWrapper>>();
	private final static Map<ContentType, Converter<TmpFileWrapper>> CONVERTERS_INPUT = new ConcurrentHashMap<ContentType, Converter<TmpFileWrapper>>();
	private final static Map<ContentType, Merger<TmpFileWrapper>> MERGE_INPUT = new ConcurrentHashMap<ContentType, Merger<TmpFileWrapper>>();
	private final static Map<ContentType, Splitter<TmpFileWrapper>> SPLIT_INPUT = new ConcurrentHashMap<ContentType, Splitter<TmpFileWrapper>>();

	static {
		register(new FFMPEGConverter());
		register(new ADPConverter());
		register(new FFMPEGMerger());
		register(new FFMPEGSplitter());
	}

	public static void register(Converter<TmpFileWrapper> inConverter) {
		for (final ContentType aContentType : inConverter.getAvailableInput()) {
			CONVERTERS_INPUT.put(aContentType, inConverter);
		}

		for (final ContentType aContentType : inConverter.getAvailableOutput()) {
			CONVERTERS_OUTPUT.put(aContentType, inConverter);
		}
	}

	public static void register(Merger<TmpFileWrapper> inMerger) {
		for (final ContentType atype : inMerger.getSupportedInput()) {
			MERGE_INPUT.put(atype, inMerger);
		}
	}

	public static void register(Splitter<TmpFileWrapper> inSplitter) {
		for (final ContentType atype : inSplitter.getSupportedInput()) {
			SPLIT_INPUT.put(atype, inSplitter);
		}
	}

	public static TmpFile convert(ContentType input, ContentType output, TmpFileWrapper inWrapper) {

		if (input == output) {
			final TmpFile theResult = inWrapper.get();
			inWrapper.clear();
			return theResult;
		}
		final Converter<TmpFileWrapper> theInputConverter = CONVERTERS_INPUT.get(input);

		if (theInputConverter != null) {
			if (theInputConverter.canGenerate(output))
				return theInputConverter.convert(inWrapper, input, output);

			final Converter<TmpFileWrapper> theOutputConverter = CONVERTERS_OUTPUT.get(output);

			if (theOutputConverter != null) {
				final Set<ContentType> theTypes = new HashSet<ContentType>(theOutputConverter.getAvailableInput());
				theTypes.retainAll(theInputConverter.getAvailableOutput());

				if (!theTypes.isEmpty()) {
					final ContentType theMiddleType = theTypes.iterator().next();
					final TmpFileWrapper theTmpWrapper = new TmpFileWrapper();
					theTmpWrapper.add(theInputConverter.convert(inWrapper, input, theMiddleType));
					return theOutputConverter.convert(theTmpWrapper, theMiddleType, output);
				}
			}

		}

		return null;
	}

	public static TmpFile join(ContentType output, Map<TmpFile, ContentType> inFiles) {
		final List<TmpFileWrapper> theFiles = new LinkedList<TmpFileWrapper>();

		final Merger<TmpFileWrapper> theMerger = MERGE_INPUT.get(output);
		if (theMerger != null) {
			try {
				for (Entry<TmpFile, ContentType> anEntry : inFiles.entrySet()) {
					final TmpFileWrapper theWrapper = new TmpFileWrapper();
					theWrapper.add(anEntry.getKey());

					final TmpFileWrapper theMergerWrapper = new TmpFileWrapper();
					theMergerWrapper.add(convert(anEntry.getValue(), output, theWrapper));
					theFiles.add(theMergerWrapper);
				}

				return theMerger.merge(theFiles, output);

			} finally {
				for (final TmpFileWrapper aWrapper : theFiles) {
					aWrapper.clean();
				}
			}
		}

		return null;
	}

	public static TmpFile split(TmpFileWrapper inWrapper, ContentType input, ContentType output, int from, int length) {
		final Splitter<TmpFileWrapper> theSplitter = SPLIT_INPUT.get(input);

		if (theSplitter != null) {
			final TmpFileWrapper theSplitterWrapper = new TmpFileWrapper(theSplitter.split(inWrapper, input, from, length));
			return convert(input, output, theSplitterWrapper);
		}

		return null;
	}
}
