package net.violet.platform.datamodel.mock;

import java.util.List;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Content;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.VAction;
import net.violet.platform.datamodel.factories.Factories;

public class ContentMock extends AbstractMockRecord<Content, ContentMock> implements Content {

	public static final MockBuilder<Content> BUILDER = new MockBuilder<Content>() {

		@Override
		protected Content build(String[] inParamValues) {
			return new ContentMock(Long.parseLong(inParamValues[0]), Long.parseLong(inParamValues[1]), Long.parseLong(inParamValues[2]), inParamValues[3], inParamValues[4], (inParamValues.length == 7 ? inParamValues[6] : null));
		}
	};

	public static List<Content> initAction80(boolean hasIdXml) {
		return ContentMock.BUILDER.generateValuesFromInitFile((hasIdXml) ? 7 : 6, "net/violet/platform/datamodel/mock/contents80Init");
	}

	public static List<Content> initOldAction80(String fileName, boolean hasIdXml) {
		return ContentMock.initOldAction80("/net/violet/platform/daemons/crawlers/vaction/", fileName, hasIdXml);
	}

	public static List<Content> initOldAction80(String inPath, String fileName, boolean hasIdXml) {
		return ContentMock.BUILDER.generateValuesFromInitFile((hasIdXml) ? 7 : 6, inPath + fileName);
	}

	/**
	 * Champs de l'enregistrement.
	 */
	private long mActionId;
	private long mFileId;
	private String title;
	private String link;
	private String id_xml;

	private ContentMock(long inId) {
		super(inId);
	}

	public ContentMock(long inId, long inActionId, long inFileId, String inTitle, String inLink, String inIdXml) {
		this(inId);
		this.mActionId = inActionId;
		this.mFileId = inFileId;
		this.title = inTitle;
		this.link = inLink;
		this.id_xml = inIdXml;

	}

	public ContentMock(long inId, VAction inAction, Files inFile, String inTitle, String inLink, String inIdXml) {
		this(inId, inAction.getId(), inFile.getId(), inTitle, inLink, inIdXml);
	}

	public VAction getAction() {
		return Factories.VACTION.find(this.mActionId);
	}

	public Files getFile() {
		return Factories.FILES.find(this.mFileId);
	}

	public String getId_xml() {
		return this.id_xml;
	}

	public String getLink() {
		return this.link;
	}

	public String getTitle() {
		return this.title;
	}

	public long countObjectHasReadContent() {
		return 0;
	}
}
