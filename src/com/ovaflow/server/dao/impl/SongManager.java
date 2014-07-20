package com.ovaflow.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.ovaflow.server.dao.SongInterface;
import com.ovaflow.server.dto.Song;

public class SongManager implements SongInterface {
	
	private String songinfo(int SongId)
	{
		String s = null;
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_songinfo where SongId='"+SongId+"'");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
					s = res.getString(4);
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
		return s;
	}
	
	private int havesong(String UserName, int SongId)
	{
		int flag = 0;
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_usrelation where UserName ='"
							+ UserName  + "'and SongId='"+SongId+"'");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
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
	
	private int addsong(String UserName, int SongId)
	{
		int flag = 0;
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("insert into tab_usrelation values(?,?)");
			prestate.setString(1, UserName);
			prestate.setInt(2, SongId);
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
	
	private Vector<Song> allsong()
	{
		Vector<Song> los = new Vector<Song>();
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_songinfo");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				Song a = new Song(res.getInt(1),res.getString(2),res.getString(3),res.getString(4));
				los.add(a);
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
		
		return los;
	}
	
	private Vector<Song> searchs(String Singer)
	{
		Vector<Song> los = new Vector<Song>();
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_songinfo where Singer = '"+ Singer + "'");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				Song a = new Song(res.getInt(1),res.getString(2),res.getString(3),res.getString(4));
				los.add(a);
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
		
		return los;
	}
	
	
	
	private Vector<Song> searchn(String SongName)
	{
		Vector<Song> los = new Vector<Song>();
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_songinfo where SongName = '"+ SongName + "'");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				Song a = new Song(res.getInt(1),res.getString(2),res.getString(3),res.getString(4));
				los.add(a);
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
		
		return los;
	}
	
	

	@Override
	public int haveSong(String UserName, int SongId) {
		return havesong(UserName,SongId);
	}

	@Override
	public int addSong(String UserName, int SongId) {
		return addsong(UserName,SongId);
	}

	@Override
	public Vector<Song> SearchS(String Singer) {
		return searchs(Singer);
	}

	@Override
	public Vector<Song> SearchN(String SongName) {
		return searchn(SongName);
	}

	@Override
	public Vector<Song> AllSong() {
		return allsong();
	}

	@Override
	public String SongInfo(int SongId) {
		return songinfo(SongId);
	}
	

}
