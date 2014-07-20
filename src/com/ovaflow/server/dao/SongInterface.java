package com.ovaflow.server.dao;

import java.util.Vector;

import com.ovaflow.server.dto.Song;

public interface SongInterface {
	
	public String SongInfo(int SongId);
	
	public Vector<Song> AllSong();
	
	public int haveSong(String UserName,int SongId);
	
	public int addSong(String UserName,int SongId);
	
	public Vector<Song> SearchS(String Singer);
	
	public Vector<Song> SearchN(String SongName);
	
	

}
