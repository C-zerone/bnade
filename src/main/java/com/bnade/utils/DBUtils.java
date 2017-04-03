package com.bnade.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

public class DBUtils {
	
	private static final String CONFIG_FILE = "jdbc.properties";
	private static DataSource DS;
	
	public static DataSource getDataSource() {
		if (DS == null) {
			Properties pops = new Properties();
			try {
				pops.load(DBUtils.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
				DS = BasicDataSourceFactory.createDataSource(pops);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return DS;
	}
	
	public static boolean isTableExist(String tableName) throws SQLException {
		boolean isTableExist = false;
		Connection con = DBUtils.getDataSource().getConnection();
		try {
			DatabaseMetaData  dbMeta = con.getMetaData();
			ResultSet rs = dbMeta.getTables(null, null, tableName, new String[]{"TABLE"});
			if (rs.next()) {			
				isTableExist = true;
			} else {
				isTableExist = false;
			}
		} finally {
			DbUtils.closeQuietly(con);
		}		
		return isTableExist;
	}
	
	public static void main(String[] args) throws SQLException {
		System.out.println(DBUtils.getDataSource().getConnection() != null);
		System.out.println(DBUtils.isTableExist("t_realm"));
		System.out.println(DBUtils.isTableExist("xxxx"));
	}
}
