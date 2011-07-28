package net.violet.db.records;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SgbdResult {

	private final ResultSet mRs;
	private Statement mStmt;

	public SgbdResult(ResultSet inRs, PreparedStatement inPstmt) {
		this.mRs = inRs;
		this.mStmt = inPstmt;
	}

	public SgbdResult(ResultSet inRs, Statement inStmt) {
		this.mRs = inRs;
		this.mStmt = inStmt;
	}

	public ResultSet getResultSet() {
		return this.mRs;
	}

	public void close() throws SQLException {
		this.mRs.close();
		this.mStmt.close();
		this.mStmt = null;
	}

}
