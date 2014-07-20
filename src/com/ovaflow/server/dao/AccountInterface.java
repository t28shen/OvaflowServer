package com.ovaflow.server.dao;



import com.ovaflow.server.dto.Account;

public interface AccountInterface {

	/**
	 * Fetches user's account information
	 * 
	 * @param username
	 * @param password
	 * @return User account or null if none found
	 */
	public Account fetchAccountInfo(String username, String password);

	public int registerNewAccount(String useraccount, String username, String password);
	
	public int renewRMB(String username, String add);

}
