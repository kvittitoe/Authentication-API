package com.kelly.api.account.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Password {
	public static String hash(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(12)); 
	}
		
	public static boolean validPassword(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}
}
