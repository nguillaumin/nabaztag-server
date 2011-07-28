package net.violet.platform.datamodel.factories.mock;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.ObjectHasReadContent;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.ContentFactory;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.mock.ContentMock;

public class ContentFactoryMock extends RecordFactoryMock<Content, ContentMock> implements ContentFactory {

	ContentFactoryMock() {
		super(ContentMock.class);
	}

	public boolean usesFiles(Files inFile) {

		for (final Content aContent : findAllMapped().values()) {
			if (aContent.getFile().getId().equals(inFile.getId())) {
				return true;
			}
		}
		return false;
	}

	public int walkOlderByAction(VAction inAction, int inSkip, RecordWalker<Content> inContentWalker) {
		int nbProcessed = 0;
		int skip = inSkip;
		final SortedMap<Long, Content> theContents = new TreeMap<Long, Content>(findAllMapped());
		final List<Entry<Long, Content>> theList = new LinkedList<Entry<Long, Content>>(theContents.entrySet());
		final ListIterator<Entry<Long, Content>> theIterator = theList.listIterator(theList.size());

		while (theIterator.hasPrevious()) {
			final Content aContent = theIterator.previous().getValue();
			if (aContent.getAction() == inAction) {
				if (skip == 0) {
					try {
						inContentWalker.process(aContent);
					} finally {
						nbProcessed++;
					}
				} else {
					skip--;
				}
			}
		}
		return nbProcessed;
	}

	public int walkWithoutAction(RecordWalker<Content> inContentWalker) {
		int nbProcessed = 0;

		for (final Content aContent : findAll()) {
			if (aContent.getAction() == null) {
				inContentWalker.process(aContent);
				nbProcessed++;
			}
		}

		return nbProcessed;
	}

	public List<Content> findMostRecentsByAction(VAction inAction, int inNbContent, boolean skipNewContent) {
		final SortedSet<Content> theSortedSet = new TreeSet<Content>(new Comparator<Content>() {

			public int compare(Content o1, Content o2) {
				return o2.getId().compareTo(o1.getId());
			}

		});
		for (final Content aContent : findAll()) {
			if (((skipNewContent && (aContent.getFile().getId() != Files.NEW_CONTENT_FILE_ID)) || !skipNewContent) && (aContent.getAction() != null) && aContent.getAction().getId().equals(inAction.getId())) {
				theSortedSet.add(aContent);
			}
		}

		final List<Content> theResult = new LinkedList<Content>(theSortedSet);

		if (theResult.size() > inNbContent) {
			return theResult.subList(0, inNbContent);
		}
		return theResult;
	}

	public Content createNewContent(VAction inAction, Files inFile, String inTitle, String inLink, String inIdXml) {
		return new ContentMock(0, inAction, inFile, inTitle, inLink, inIdXml);
	}

	public long insert(VAction inAction, Files inFile, String inTitle) {
		return insert(inAction, inFile, inTitle, net.violet.common.StringShop.EMPTY_STRING, null);
	}

	public long insert(VAction inAction, Files inFile, String inTitle, String inLink, String inIdXml) {
		final Content theContent = createNewContent(inAction, inFile, inTitle, inLink, inIdXml);
		if (theContent != null) {
			return theContent.getId();
		}
		return 0;

	}

	public List<Content> findUnreadByAction(VAction inAction, VObject inVObject, int inMaxContent) {
		final List<Content> recentContents = findMostRecentsByAction(inAction, inMaxContent, true);
		final ObjectHasReadContent objectContent = Factories.OBJECT_HAS_READ_CONTENT.findByActionAndVObject(inAction, inVObject);

		final List<Content> result = new LinkedList<Content>();

		Content c;

		while (!recentContents.isEmpty() && ((c = recentContents.remove(0)).getId().longValue() > objectContent.getContent().getId().longValue()) && result.add(c)) {
			;
		}

		return result;
	}

	public Content findOldestByAction(VAction inAction, int inOldestIndex) {
		final LinkedList<Content> theList = new LinkedList<Content>(findMostRecentsByAction(inAction, inOldestIndex, false));
		return theList.getLast();
	}

}
