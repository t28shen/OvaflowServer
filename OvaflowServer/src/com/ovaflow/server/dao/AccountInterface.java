package com.ovaflow.server.dao;

import java.util.List;

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

	/**
	 * Fetches user's friends information
	 * 
	 * @param username
	 * @return list of user's friends info, or null if none found
	 */
	public List<Account> fetchFriendsInfo(String username);
}
