package com.ovaflow.server.dto;

public class Account {
	private String accountId;
	private String username;
	private String email;

	public Account(String accountId, String username, String email) {
		this.accountId = accountId;
		this.username = username;
		this.email = email;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
