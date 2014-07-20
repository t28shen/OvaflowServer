package com.ovaflow.server.dto;

import java.security.NoSuchAlgorithmException;

public class Account {
	private String accountId;
	private String username;
	private int RMB;
	private int CurrentAvatar;

	public Account(String accountId, String username, int RMB, int currentavatar) {
		this.accountId = accountId;
		this.username = username;
		this.RMB = RMB;
		this.CurrentAvatar = currentavatar;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRMB() {
		return RMB;
	}
	
	public int getCA() {
		return CurrentAvatar;
	}
	
	public String hash(int salt) throws NoSuchAlgorithmException {
		if(accountId!=null)
		{
			return Integer.toString((accountId.hashCode() + salt) & 0x7FFFFFFF, 36);
		}
		return null;
	}

}
