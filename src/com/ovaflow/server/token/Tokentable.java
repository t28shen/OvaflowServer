package com.ovaflow.server.token;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import com.ovaflow.server.dto.Account;

public class Tokentable {
	private Map<String, Account> tokens = new HashMap<String, Account>();
	
	public String addToken(Account account) {
		String token = null;
		try {
			token = account.hash((int)System.currentTimeMillis());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tokens.put(token, account);
		return token;
	}
	
	public Account getAccount(String token) {
		return tokens.get(token);
	}
	
	public int size() {
		return tokens.size();
	}
}
