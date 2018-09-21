package com.mgmotor.core.servlets;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourcePool;
import com.mgmotor.core.utils.MgmDatasourceUtil;

/**
 * @author Anomitra
 *
 */
public class AwsRdsDatasourceConnectorServlet extends SlingSafeMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3879102247664183743L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AwsRdsDatasourceConnectorServlet.class);

	@Reference
	private DataSourcePool dataSourcePool;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
			MgmDatasourceUtil rds = new MgmDatasourceUtil(dataSourcePool);
			String prpdStmt = "select user_name from login_admin";
			PreparedStatement preparedStatement = rds.getPreparedStatement(prpdStmt);
			ResultSet rs = preparedStatement.executeQuery();
			LOGGER.info("SQL Query Data");
			while (rs.next()) {
				LOGGER.info(rs.getString(1) + ",");
			}
		} catch (Exception e) {
			LOGGER.error("Exception encountered:::", e);
		}
	}

}
