package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.Record.RecordWalker;
import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.ContentImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.ObjectHasReadContentImpl;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.VActionImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.ContentFactory;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class ContentFactoryImpl extends RecordFactoryImpl<Content, ContentImpl> implements ContentFactory {

	private static final Logger LOGGER = Logger.getLogger(ContentFactoryImpl.class);

	ContentFactoryImpl() {
		super(ContentImpl.SPECIFICATION);
	}

	public boolean usesFiles(Files inFile) {
		return count(null, StringShop.FILE_ID_CONDITION, Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

	public int walkOlderByAction(VAction inAction, int inSkip, RecordWalker<Content> inWalker) {
		final List<Object> theValues = Collections.singletonList((Object) inAction.getId());
		return walk(StringShop.ACTION_ID_CONDITION, theValues, StringShop.ID_DESC, inSkip, inWalker);
	}

	public int walkWithoutAction(RecordWalker<Content> inContentWalker) {
		return walk(" action_id IS NULL ", null, null, 0, inContentWalker);
	}

	public List<Content> findMostRecentsByAction(VAction inAction, int inNbContent, boolean skipNewContent) {
		final List<Content> theList = new LinkedList<Content>();
		theList.addAll(findAll(StringShop.ACTION_ID_CONDITION + (skipNewContent ? " AND file_id <> 1 " : net.violet.common.StringShop.EMPTY_STRING) + " ORDER BY " + StringShop.ID_DESC + " LIMIT ?", Arrays.asList(new Object[] { inAction.getId(), inNbContent }), null));

		return theList;
	}

	public ContentImpl createNewContent(VAction inAction, Files inFile, String inTitle, String inLink, String inIdXml) {
		return new ContentImpl((VActionImpl) inAction, inFile, inTitle, inLink, inIdXml);
	}

	public long insert(VAction inAction, Files inFile, String inTitle) {
		return insert(inAction, inFile, inTitle, net.violet.common.StringShop.EMPTY_STRING, null);
	}

	public long insert(VAction inAction, Files inFile, String inTitle, String inLink, String inIdXml) {
		final ContentImpl theRecord = createNewContent(inAction, inFile, inTitle, inLink, inIdXml);
		try {
			return theRecord.insertRecord(ContentImpl.NEW_COLUMNS);
		} catch (final SQLException e) {
			ContentFactoryImpl.LOGGER.fatal(e, e);
		}
		return 0;

	}

	public List<Content> findUnreadByAction(VAction inAction, VObject inVObject, int inMaxContent) {
		final List<Object> theValues = Arrays.asList(new Object[] { inAction.getId(), inAction.getId(), inVObject.getId() });

		return findAll(new String[] { ObjectHasReadContentImpl.SPECIFICATION.getTableName() }, " content." + StringShop.ACTION_ID_CONDITION + StringShop.CONDITION_ACCUMULATOR + "object_has_read_content." + StringShop.ACTION_ID_CONDITION + StringShop.CONDITION_ACCUMULATOR + " object_id = ? AND content.id > " + StringShop.CONTENT_ID, theValues, StringShop.ID_DESC, 0, inMaxContent);
	}

	public Content findOldestByAction(VAction inAction, int inOldestIndex) {
		final LinkedList<Content> list = new LinkedList<Content>(findMostRecentsByAction(inAction, inOldestIndex, false));
		return list.getLast();
	}

}
