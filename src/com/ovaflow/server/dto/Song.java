package com.ovaflow.server.dto;

public class Song {
	private int songid;
	private String songname;
	private String singer;
	private String pointer;
	
	public Song(int songid, String songname, String singer, String pointer)
	{
		this.songid = songid;
		this.songname = songname;
		this.singer = singer;
		this.pointer = pointer;
	}
	
	public int getSongId()
	{
		return songid;
	}
	
	public String getSongName()
	{
		return songname;
	}
	
	public String getSinger()
	{
		return singer;
	}
	
	public String getPointer()
	{
		return pointer;
	}
	
}
