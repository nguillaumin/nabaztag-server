package net.violet.platform.datamodel;

import java.sql.SQLException;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;

public class NathanTagImpl extends ObjectRecord<NathanTag, NathanTagImpl> implements NathanTag {

	/**
	 * SQL Specification
	 */
	public static final SQLObjectSpecification<NathanTagImpl> SPECIFICATION = new SQLObjectSpecification<NathanTagImpl>("nathan_tag", NathanTagImpl.class, new SQLKey[] { new SQLKey("tag_id") });

	/**
	 * SQL Fields
	 */
	protected long tag_id;
	protected long tag_categ;
	protected String tag_label;

	/**
	 * Default constructor
	 */
	protected NathanTagImpl() {

	}

	/**
	 * Constructor with id
	 */
	protected NathanTagImpl(long inId) throws SQLException {
		init(inId);
	}

	@Override
	public Long getId() {
		return this.tag_id;
	}

	public Long getCateg() {
		return this.tag_categ;
	}

	public String getLabel() {
		return this.tag_label;
	}

	@Override
	public SQLObjectSpecification<NathanTagImpl> getSpecification() {
		return NathanTagImpl.SPECIFICATION;
	}

}
