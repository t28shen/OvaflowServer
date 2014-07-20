package com.ovaflow.server.dao;

import java.util.Vector;

import com.ovaflow.server.dto.Avatar;

public interface AvatarInterface {
	
	public Vector<Avatar> OwnAvatar(String UserName);	
	
	public Vector<Avatar> NotOwnAvatar(String UserName);
	
	public int BuyAvatar(String UserName, int AvatarId, int Price,int RMB);
	
	public int ChangeAvatar(String UserName, int AvatarId);
}
