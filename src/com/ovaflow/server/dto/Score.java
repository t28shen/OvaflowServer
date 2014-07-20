package com.ovaflow.server.dto;

public class Score {
	private String username;
	private int bmid;
	private int score;
	private int combo;
	
	public Score(String username, int bmid, int score, int combo) {
		this.username = username;
		this.bmid = bmid;
		this.score = score;
		this.combo = combo;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getBmid() {
		return bmid;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getCombo() {
		return combo;
	}
}
