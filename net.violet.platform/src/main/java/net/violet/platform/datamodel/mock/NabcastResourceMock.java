package net.violet.platform.datamodel.mock;

import java.sql.Timestamp;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.ApplicationContent;
import net.violet.platform.datamodel.NabcastResource;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectType;

public class NabcastResourceMock extends AbstractMockRecord<NabcastResource, NabcastResourceMock> implements NabcastResource {

	private final Timestamp releaseDate;
	private final Timestamp expirationDate;
	private final ObjectType objectType;
	private final String label;

	protected NabcastResourceMock(long inContentId, Timestamp inReleaseDate, Timestamp inExpirationDate, ObjectType inType, String inLabel) {
		super(inContentId);
		this.releaseDate = inReleaseDate;
		this.expirationDate = inExpirationDate;
		this.objectType = inType;
		this.label = inLabel;
	}

	public NabcastResourceMock(ApplicationContent inContent, Timestamp inReleaseDate, Timestamp inExpirationDate, ObjectType inType, String inLabel) {
		this(inContent.getId(), inReleaseDate, inExpirationDate, inType, inLabel);
	}

	public Timestamp getExpirationDate() {
		return this.expirationDate;
	}

	public Timestamp getReleaseDate() {
		return this.releaseDate;
	}

	public ApplicationContent getContent() {
		return Factories.APPLICATION_CONTENT.find(this.getId());
	}

	public ObjectType getObjectReader() {
		return this.objectType;
	}

	public String getLabel() {
		return this.label;
	}

}
