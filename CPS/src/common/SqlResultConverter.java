package common;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SqlResultConverter {

	public static SqlResult convertResultToSqlResult(ResultSet result) throws SQLException{
		SqlResult toRet = new SqlResult();
		ResultSetMetaData rsmd = result.getMetaData();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			toRet.columnNames.add(rsmd.getColumnLabel(i));
		}

		while (result.next()) {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				toRet.rowData.add(result.getObject(i));
			}
		}
		return toRet;
	}
}

