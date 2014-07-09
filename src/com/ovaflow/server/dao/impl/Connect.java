package com.ovaflow.server.dao.impl;

import java.sql.*;

public class Connect {
	public static Connection myConnect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); // MYSQL驱动
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mydb", "root", "root"); // 链接本地MYSQL
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		return con;
	}
}
