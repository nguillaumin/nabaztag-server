package net.violet.db.records.factories;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.AbstractSQLRecord;
import net.violet.db.records.Record;
import net.violet.db.records.SQLSpecification;
import net.violet.db.records.Record.JoinRecordsWalker;
import net.violet.db.records.Record.RecordWalker;

import org.apache.log4j.Logger;

public class RecordFactoryImpl<Intf extends Record<Intf>, Impl extends AbstractSQLRecord<Intf, Impl>> implements RecordFactory<Intf> {

	private static final Logger LOGGER = Logger.getLogger(RecordFactoryImpl.class);

	private final SQLSpecification<Impl> mSQLSpecification;

	protected RecordFactoryImpl(SQLSpecification<Impl> inSpecification) {
		this.mSQLSpecification = inSpecification;
	}

	public Intf find(long id) {
		try {
			return AbstractSQLRecord.findByKey(this.mSQLSpecification, this.mSQLSpecification.getTableKeys()[0], id);
		} catch (final SQLException se) {
			RecordFactoryImpl.LOGGER.fatal(se, se);
		}
		return null;
	}

	protected SQLSpecification<Impl> getSQLSpecification() {
		return this.mSQLSpecification;
	}

	public Map<Long, Intf> findAllMapped() {
		try {
			final Map<Long, Intf> theMap = new HashMap<Long, Intf>();
			for (final Intf aRecord : AbstractSQLRecord.findAll(this.mSQLSpecification, null, null, null)) {
				theMap.put(aRecord.getId(), aRecord);
			}
			return theMap;
		} catch (final SQLException e) {
			RecordFactoryImpl.LOGGER.fatal(e, e);
		}

		return Collections.emptyMap();
	}

	public long count(String[] inJoinTables, String inCondition, List<Object> inValues, String inGroupBy) {
		try {
			return AbstractSQLRecord.count(this.mSQLSpecification, inJoinTables, inCondition, inValues, inGroupBy);
		} catch (final SQLException e) {
			RecordFactoryImpl.LOGGER.fatal(e, e);
		}

		return 0;
	}

	public int walk(String condition, List<Object> values, String inOrderBy, int inSkip, RecordWalker<Intf> inWalker) {
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(this.mSQLSpecification, condition, values, inOrderBy, inSkip, inWalker);
		} catch (final SQLException anException) {
			RecordFactoryImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public int walk(String condition, List<Object> values, String[] inJoinTables, String inOrderBy, int inSkip, AbstractSQLRecord.SQL_TOOLS inTool, List<String> inDistinct, RecordWalker<Intf> inWalker) {
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(this.mSQLSpecification, condition, values, inJoinTables, inOrderBy, inSkip, inTool, inDistinct, inWalker);
		} catch (final SQLException anException) {
			RecordFactoryImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public int walk(String condition, List<Object> values, String[] inJoinTables, String inOrderBy, int inSkip, AbstractSQLRecord.SQL_TOOLS inTool, List<String> inDistinct, String inGroupBy, RecordWalker<Intf> inWalker) {
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(this.mSQLSpecification, condition, values, inJoinTables, inOrderBy, inSkip, inTool, inDistinct, inGroupBy, inWalker);
		} catch (final SQLException anException) {
			RecordFactoryImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	// FIXME Eclipse can't handle this method correctly.
	public final <I extends AbstractSQLRecord<U, I>, U extends Record<U>> int walk(SQLSpecification<I> inSpec, String condition, List<Object> values, String[] inJoinTables, String inOrderBy, int inSkip, int indexRows, int nbRows, JoinRecordsWalker<Intf, U> inWalker) {
		return walk(inSpec, condition, values, inJoinTables, inOrderBy, inSkip, indexRows, nbRows, null, null, inWalker);
	}

	public <I extends AbstractSQLRecord<U, I>, U extends Record<U>> int walk(SQLSpecification<I> inSpec, String condition, List<Object> values, String[] inJoinTables, String inOrderBy, int inSkip, int indexRows, int nbRows, AbstractSQLRecord.SQL_TOOLS inTool, List<String> inDistinctKeys, JoinRecordsWalker<Intf, U> inWalker) {
		int theResult = 0;
		try {
			theResult = AbstractSQLRecord.walk(this.mSQLSpecification, inSpec, condition, values, inJoinTables, inOrderBy, inSkip, indexRows, nbRows, inTool, inDistinctKeys, inWalker);
		} catch (final SQLException anException) {
			RecordFactoryImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public List<Intf> findAll(String inCondition, List<Object> inValues) {
		return findAll(inCondition, inValues, null);
	}

	public List<Intf> findAll(String inCondition, List<Object> inValues, String inOrderBy) {
		try {
			return AbstractSQLRecord.findAll(this.mSQLSpecification, inCondition, inValues, inOrderBy);
		} catch (final SQLException anException) {
			RecordFactoryImpl.LOGGER.fatal(anException, anException);
		}

		return Collections.emptyList();
	}

	public List<Intf> findAll(String inCondition, List<Object> inValues, String inOrderBy, int inIndexRows, int inNbRows) {
		final List<Intf> theResult = new LinkedList<Intf>();
		try {
			theResult.addAll(AbstractSQLRecord.findAll(this.mSQLSpecification, inCondition, inValues, inOrderBy, inIndexRows, inNbRows));
		} catch (final SQLException anException) {
			RecordFactoryImpl.LOGGER.fatal(anException, anException);
		}
		return theResult;
	}

	public Intf findByKey(int inKeyIndex, Object inKeyValue) {
		try {
			return AbstractSQLRecord.findByKey(this.mSQLSpecification, this.mSQLSpecification.getTableKeys()[inKeyIndex], inKeyValue);
		} catch (final SQLException se) {
			RecordFactoryImpl.LOGGER.fatal(se, se);
		}
		return null;
	}

	public Intf findByKey(int inKeyIndex, Object[] inKeyValues) {
		try {
			return AbstractSQLRecord.findByKey(this.mSQLSpecification, this.mSQLSpecification.getTableKeys()[inKeyIndex], inKeyValues);
		} catch (final SQLException se) {
			RecordFactoryImpl.LOGGER.fatal(se, se);
		}
		return null;
	}

	public List<Intf> findAll(String[] inJoinTables, String condition, List<Object> values, String inOrderBy) {
		try {
			return AbstractSQLRecord.findAll(this.mSQLSpecification, inJoinTables, condition, values, inOrderBy);
		} catch (final SQLException se) {
			RecordFactoryImpl.LOGGER.fatal(se, se);
		}
		return null;
	}

	public List<Intf> findAll(String[] inJoinTables, String inCondition, List<Object> inValues, String inOrderBy, int inIndexRows, int inNbRows) {
		try {
			return AbstractSQLRecord.findAll(this.mSQLSpecification, inJoinTables, inCondition, inValues, inOrderBy, inIndexRows, inNbRows);
		} catch (final SQLException e) {
			RecordFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public List<Intf> findAllDistinct(String[] inJoinTables, String condition, List<Object> values, String inOrderBy) {
		try {
			return AbstractSQLRecord.findAllDistinct(this.mSQLSpecification, inJoinTables, condition, values, inOrderBy);
		} catch (final SQLException se) {
			RecordFactoryImpl.LOGGER.fatal(se, se);
		}
		return null;
	}

	public Intf find(String inCondition, List<Object> inValues) {
		try {
			return AbstractSQLRecord.find(this.mSQLSpecification, inCondition, inValues);
		} catch (final SQLException se) {
			RecordFactoryImpl.LOGGER.fatal(se, se);
		}
		return null;
	}

	public Intf find(String[] inJoinTables, String inCondition, List<Object> inValues) {
		try {
			return AbstractSQLRecord.find(this.mSQLSpecification, inJoinTables, inCondition, inValues);
		} catch (final SQLException se) {
			RecordFactoryImpl.LOGGER.fatal(se, se);
		}
		return null;
	}

	public Intf find(String[] inJoinTables, String inCondition, List<Object> inValues, String orderBy) {
		try {
			return AbstractSQLRecord.find(this.mSQLSpecification, inJoinTables, inCondition, inValues, orderBy);
		} catch (final SQLException se) {
			RecordFactoryImpl.LOGGER.fatal(se, se);
		}
		return null;
	}

}
