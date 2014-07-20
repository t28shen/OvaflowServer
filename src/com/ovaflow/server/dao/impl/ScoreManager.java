package com.ovaflow.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.ovaflow.server.dao.ScoreInterface;
import com.ovaflow.server.dto.Score;

public class ScoreManager implements ScoreInterface{
	private int NewHighScore(String UserName, int BMId, int Score,int Combo) 
	{
		int flag = 0;
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate1 = null;
		PreparedStatement prestate2 = null;
		PreparedStatement prestate3 = null;
		try {
			prestate1 = con
					.prepareStatement("select * from tab_highscoreinfo where UserName ='"
							+ UserName + "' and BMId = '"+ BMId + "'");
			ResultSet res = prestate1.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				prestate2 = con
						.prepareStatement("update tab_highscoreinfo set Score = '" + Score + "', Combo = '" + Combo
								 + "' where UserName = '" + UserName + "' and BMId ='"
								 + BMId + "'");
				prestate2.execute();
				flag = 1;
					break;
				}
			if(flag == 0)
			{
				prestate3 = con
						.prepareStatement("insert into tab_highscoreinfo values(?,?,?,?)");
				prestate3.setString(1, UserName);
				prestate3.setInt(2, BMId);
				prestate3.setInt(3, Score);
				prestate3.setInt(4, Combo);
				prestate3.execute();
				flag = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					if (prestate1 != null) {prestate1.close();}
					if (prestate2 != null) {prestate2.close();}
					if (prestate3 != null) {prestate3.close();}
					con.close();
				} catch (SQLException ex2) {
				}
			}
		}
		return flag;
	}
	private Score GetHighScore(String UserName, int BMId)
	{
		int flag = 0;
		Score answer = null;
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_highscoreinfo where UserName ='"
							+ UserName + "' and BMId = '"+ BMId + "'");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
					flag = 1;
					answer = new Score(res.getString(1),res.getInt(2),res.getInt(3),res.getInt(4));
					break;
			}
			if(flag != 1)
			{
				answer = new Score(UserName,BMId,-1,-1);
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
		return answer;
	}
	
	private Vector<Score> ScoreBarod(int BMId)
	{
		Vector<Score> los = new Vector<Score>();
		Connection con = Connect.myConnect(); // 定义一个MYSQL链接对象
		PreparedStatement prestate = null;
		try {
			prestate = con
					.prepareStatement("select * from tab_highscoreinfo where BMId = '"+ BMId + "' ORDER BY Score DESC Limit 3");
			ResultSet res = prestate.executeQuery();
			// ResultSetMetaData resd = res.getMetaData();
			while (res.next()) {
				Score a = new Score(res.getString(1),res.getInt(2),res.getInt(3),res.getInt(4));
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
	public int newhighscore(String UserName, int BMId, int Score, int Combo) {
		return NewHighScore(UserName,BMId,Score,Combo);
	}
	@Override
	public Score gethighscore(String UserName, int BMId) {
		return GetHighScore(UserName,BMId);
	}
	@Override
	public Vector<Score> scorebarod(int BMId) {
		return ScoreBarod(BMId);
	}
	
	

}
