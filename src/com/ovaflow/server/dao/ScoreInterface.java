package com.ovaflow.server.dao;

import java.util.Vector;

import com.ovaflow.server.dto.Score;

public interface ScoreInterface {
	
	public int newhighscore(String UserName, int BMId, int Score,int Combo);
	
	public Score gethighscore(String UserName, int BMId);
	
	public Vector<Score> scorebarod(int BMId);
	
}
