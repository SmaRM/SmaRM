package org.smarthome.data.connectors;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.smarthome.shared.configuration.DBConfiguration;

public class MySqlConnector {

	private Connection dbConnection = null;

	public static enum TableID {
		learned_data,
		ar_training
	}

	public MySqlConnector(DBConfiguration dbConfig) throws IOException, SQLException {
		String url = dbConfig.getUrl();
		String user = dbConfig.getUser();
		String passwd = dbConfig.getPasswd();

		dbConnection = DriverManager.getConnection(url, user, passwd);
	}

	public String getTableAsCSV(TableID table) throws SQLException {
		String sqlQuery = "SELECT * FROM " + table.name();

		ResultSet resultSet = sendQuery(sqlQuery);
		return toCSV(resultSet);
	}

	public String getTableAsCSV(TableID table, String startDate, String endDate) throws SQLException {
		String sqlQuery = "SELECT * FROM %s WHERE time BETWEEN \'%s\' AND \'%s\'";
		sqlQuery = String.format(sqlQuery, table.name(), startDate, endDate);

		ResultSet resultSet = sendQuery(sqlQuery);
		return toCSV(resultSet);
	}

	private ResultSet sendQuery(String sqlQuery) throws SQLException {
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery);
		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet;
	}

	private String toCSV(ResultSet resultSet) throws SQLException {
		// Get amount of columns from resultset meta data
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int numColumns = resultSetMetaData.getColumnCount();

		String result = "";
		while (resultSet.next()) {
			for (int i = 1; i <= numColumns; i++) {
				result += resultSet.getString(i);
				if (i != numColumns) {
					result += ",";
				}
			}
			result += "\n";
		}
		return result;
	}
}
