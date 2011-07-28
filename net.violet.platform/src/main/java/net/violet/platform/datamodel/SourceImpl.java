package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.platform.util.CCalendar;

public class SourceImpl extends ObjectRecord<Source, SourceImpl> implements Source {

	public static final SQLObjectSpecification<SourceImpl> SPECIFICATION = new SQLObjectSpecification<SourceImpl>("source", SourceImpl.class, new SQLKey[] { new SQLKey("source_id"), new SQLKey("source_path"), new SQLKey(new String[] { "source_path", "source_val" }) });

	protected SourceImpl(long id) throws SQLException {
		super();
		init(id);
	}

	protected SourceImpl() {
		super();
	}

	protected long source_id;
	protected String source_path;
	protected Integer source_val;
	protected Long source_time;
	protected Long source_srv;
	protected String source_dico;

	private static final String[] NEW_COLUMNS = new String[] { "source_val", "source_path", "source_time", "source_srv", "source_dico" };

	public SourceImpl(String path, int val, long time, long srv, String dico) throws SQLException {
		super();
		this.source_val = val;
		this.source_path = path;
		this.source_time = time;
		this.source_srv = srv;
		this.source_dico = dico;
		init(SourceImpl.NEW_COLUMNS);
	}

	public SourceImpl(String path, int val) throws SQLException {
		super();
		this.source_val = val;
		this.source_path = path;
		init(new String[] { "source_val", "source_path" });
	}

	@Override
	public Long getId() {
		return getSource_id();
	}

	@Override
	public SQLObjectSpecification<SourceImpl> getSpecification() {
		return SourceImpl.SPECIFICATION;
	}

	public final long getSource_id() {
		return this.source_id;
	}

	public final String getSource_path() {
		return this.source_path;
	}

	public final long getSource_val() {
		return this.source_val;
	}

	public void setVal(int val) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setSource_val(theUpdateMap, val);
		if (!theUpdateMap.isEmpty()) {
			setSource_time(theUpdateMap, CCalendar.getCurrentTimeInSecond());
			update(theUpdateMap);
		}
	}

	private void setSource_val(Map<String, Object> inUpdateMap, int inValue) {
		if (inValue != this.source_val) {
			this.source_val = inValue;
			inUpdateMap.put("source_val", inValue);
		}
	}

	private void setSource_time(Map<String, Object> inUpdateMap, long inValue) {
		if (inValue != this.source_time) {
			this.source_time = inValue;
			inUpdateMap.put("source_time", inValue);
		}
	}

	public final long getSource_time() {
		return this.source_time;
	}

	public final long getSource_srv() {
		return this.source_srv;
	}

	public final String getSource_dico() {
		return this.source_dico;
	}

}
