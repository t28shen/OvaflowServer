package com.ovaflow.server.dao;

import java.util.Vector;

import com.ovaflow.server.dto.BeatMap;

public interface BeatMapInterface {
	
	public BeatMap BeatMapInfo(int BeatMapId);
    
    public Vector<BeatMap> OwnBeatMap(String UserName);
    
    public Vector<BeatMap> OwnSearchC(String UserName, String Creater);
    
    public Vector<BeatMap> OwnSearchN(String UserName, String BeatMapName);
    
    public Vector<BeatMap> NotOwnBeatMap(String UserName);
    
    public Vector<BeatMap> NotOwnSearchC(String UserName, String Creater);
    
    public Vector<BeatMap> NotOwnSearchN(String UserName, String BeatMapName);
    
    public int AddBM(String UserName, int BMId);

}
