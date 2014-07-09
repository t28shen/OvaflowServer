package com.ovaflow.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ovaflow.server.dao.AccountInterface;
import com.ovaflow.server.dto.Account;
public class AccountManager implements AccountInterface {
	
	// check valid user login 0: not valid, 1: valid
	private Account LoginCheck(String UserAccount, String Password) {
		String Name = null;
		int RMB = -1;
		Account ac = null;
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_userinfo where UserAccount ='"
							+ UserAccount + "'");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				if (Password.equals(res.getString(3))) {
					Name = res.getString(2);
					RMB = res.getInt(4);
					break;
				}
			}
			ac = new Account(UserAccount,Name,null,RMB);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					prestate.close();
					con.close();
				} catch (SQLException ex2) {
				}
			}
		}
		return ac;
	}

	// check unique UserAccount 0: not unique 1: unique
	private int OnlyUACheck(String UserAccount) {
		int flag = 1;
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_userinfo where UserAccount = '"
							+ UserAccount + "'");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				flag = 0;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					prestate.close();
					con.close();
				} catch (SQLException ex2) {
				}
			}
		}
		return flag;
	}

	// check unique UserAccount 0: not unique 1: unique
	private int OnlyUNCheck(String UserName) {
		int flag = 1;
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_userinfo where UserName = '"
							+ UserName + "'");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				flag = 0;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					prestate.close();
					con.close();
				} catch (SQLException ex2) {
				}
			}
		}
		return flag;
	}

	// add new user
	private int NewUser(String UserAccount, String UserName, String Password) {
		int flag = 0;
		Connection con = Connect.myConnect();
		; // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("insert into tab_userinfo values(?,?,?,?)");
			prestate.setString(1, UserAccount);
			prestate.setString(2, UserName);
			prestate.setString(3, Password);
			prestate.setInt(4, 400);
			boolean res = prestate.execute();
			if (!res) {
				flag = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					prestate.close();
					con.close();
				} catch (SQLException ex2) {
				}
			}
		}
		return flag;
	}

	private int RenewRMB(String UserName, int Money) {
		int RMB = -1;
		Connection con = Connect.myConnect();
		; // 定义一个MYSQL链接对象
		PreparedStatement prestate1 = null;
		PreparedStatement prestate2 = null;
		try {
			prestate1 = con
					.prepareStatement("select * from tab_userinfo where UserName = '"
							+ UserName + "'");
			ResultSet res = prestate1.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				RMB = res.getInt(4) + Money;
			}
			prestate2 = con.prepareStatement("update tab_userinfo set RMB ='"
					+ RMB + "'");
			prestate2.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					prestate1.close();
					prestate2.close();
					con.close();
				} catch (SQLException ex2) {
				}
			}
		}
		return RMB;
	}

	@Override
	public Account fetchAccountInfo(String username, String password) {
		return LoginCheck(username,password);
	}

	@Override
	public void registerNewAccount(String username, String password,
			String email, int RMB) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Account> fetchFriendsInfo(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
