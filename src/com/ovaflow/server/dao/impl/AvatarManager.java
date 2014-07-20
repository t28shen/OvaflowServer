package com.ovaflow.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.ovaflow.server.dao.AvatarInterface;
import com.ovaflow.server.dto.Avatar;

public class AvatarManager implements AvatarInterface{
	
	
	private Vector<Avatar> ownavatar(String UserName)
	{
		Vector<Avatar> loa = new Vector<Avatar>();
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select ai.* from tab_uarelation as ref  inner join tab_avatarinfo as ai on  ref.UserName ='"+ UserName + "'and ai.AvatarId = ref.AvatarId");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				Avatar a = new Avatar(res.getInt(1),res.getString(2),res.getInt(3),res.getString(4));
				loa.add(a);
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
		
		return loa;
	}
	
	private Vector<Avatar> notownavatar(String UserName)
	{
		Vector<Avatar> loa = new Vector<Avatar>();
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_avatarinfo as A left join (select ai.* from tab_uarelation as ref  left join tab_avatarinfo as ai on  ref.UserName = '"+ UserName + "'and ai.AvatarId = ref.AvatarId) as B using(AvatarID) where B.AvatarId is null");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				Avatar a = new Avatar(res.getInt(1),res.getString(2),res.getInt(3),res.getString(4));
				loa.add(a);
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
		
		return loa;
	}
	
	private int buyavatar(String UserName, int AvatarId, int Price, int RMB) {
		int flag = 0;
		Connection con = Connect.myConnect();
		; // 定义一个MYSQL链接对象
		PreparedStatement prestate1 = null;
		PreparedStatement prestate2 = null;
		int remain = RMB - Price;
		if(remain < 0){return flag;}
		try {
			prestate1 = con
					.prepareStatement("insert into tab_uarelation values(?,?)");
			prestate1.setString(1, UserName);
			prestate1.setInt(2, AvatarId);
			boolean res = prestate1.execute();
			prestate2 = con
					.prepareStatement("update tab_userinfo set RMB ='"
							+ remain + "'where UserName = '"+ UserName + "'");
			res = res & prestate2.execute();
			if (!res) {
				flag = 1;
			}

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
		return flag;
	}
	
	private int changeavatar(String UserName, int AvatarId) {
		int flag = 0;
		Connection con = Connect.myConnect();
		; // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("update tab_userinfo set CurrentAvatar ='"
							+ AvatarId + "'where UserName = '"+ UserName + "'");
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
	

	@Override
	public Vector<Avatar> OwnAvatar(String UserName) {
		return ownavatar(UserName);
	}

	@Override
	public Vector<Avatar> NotOwnAvatar(String UserName) {
		// TODO Auto-generated method stub
		return notownavatar(UserName);
	}

	@Override
	public int BuyAvatar(String UserName, int AvatarId, int Price,int RMB) {
		// TODO Auto-generated method stub
		return buyavatar(UserName,AvatarId,Price, RMB);
	}

	@Override
	public int ChangeAvatar(String UserName, int AvatarId) {
		// TODO Auto-generated method stub
		return changeavatar(UserName,AvatarId);
	}

}
