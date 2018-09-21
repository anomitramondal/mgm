package com.mgmotor.core.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourceNotFoundException;
import com.day.commons.datasource.poolservice.DataSourcePool;

/**
 * @author Anomitra
 *
 */
public class MgmDatasourceUtil {

	private Connection connection = null;
	private Statement statement = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(MgmDatasourceUtil.class);

	public MgmDatasourceUtil(DataSourcePool dataSourcePool) throws SQLException {

		try {
			DataSource dataSource = (DataSource) dataSourcePool.getDataSource("mgmpool");
			LOGGER.error("dataSource details" + dataSource);
			if (dataSource != null && !dataSource.equals("")) {
				connection = dataSource.getConnection();
			} else {
				LOGGER.error("Datasource object is null or empty.. check the SQL pool connection details");
			}
			if (connection != null && !connection.equals("")) {
				statement = connection.createStatement();
			} else {
				LOGGER.error("Datasource object is null or empty.. check the SQL pool connection details");
			}

		} catch (SQLException | DataSourceNotFoundException e) {
			LOGGER.error("Error in creating connection occurred {} TRACE: {}" + e.getMessage(), e);
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public Statement getStatement() {
		return statement;
	}

	public PreparedStatement getPreparedStatement(String sqlQuery) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlQuery);
		} catch (SQLException e) {
			LOGGER.error("Error in getting prepareStatement {} TRACE: {}" + e.getMessage(), e);
		}
		return preparedStatement;
	}
}
