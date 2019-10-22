package com.kelly.api.account.form.error;

import java.util.List;

public class PropertyError {
	private String name;
	private List<String> errors;

	public PropertyError(String name, List<String> errors) {
		this.name = name;
		this.errors = errors;
	}

	public String getName() {
		return name;
	}

	public List<String> getErrors() {
		return errors;
	}
}
