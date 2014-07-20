package com.ovaflow.server.dto;

public class BeatMap {

	private int BMId;
	private int SongId;
	private String BMName;
	private String Creater;
	private String Poniter;
	
	public BeatMap(int BMId, int SongId,String BMName ,String Creater, String Poniter)
	{
		this.BMId = BMId;
		this.SongId = SongId;
		this.BMName = BMName;
		this.Creater = Creater;
		this.Poniter = Poniter;
	}
	
	public int getBMId()
	{
		return BMId;
	}
	
	public int getSongId()
	{
		return SongId;
	}
	
	public String getCreater()
	{
		return Creater;
	}
	
	public String getPoniter()
	{
		return Poniter;
	}
	
	public String getBMName()
	{
		return BMName;
	}

}
