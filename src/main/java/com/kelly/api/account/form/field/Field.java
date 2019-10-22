package com.kelly.api.account.form.field;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class Field {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*Field Properties*/
	private FieldLabel label;
	private String value;
	
	private List<FieldError> errors;
	
	/*Field Validator Properties*/
	private boolean validatorRequired = false;
	private int validatorMinLength = 0;
	private int validatorMaxLength = 0;
	
	public Field(FieldLabel label) {
		this.label = label;
	}

	public FieldLabel getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void addError(FieldError error) {
		if (errors == null) errors = new ArrayList<>();
		errors.add(error);
	}
	
	public List<String> getErrors() {
		List<String> stringList = new ArrayList<>();
		if (errors != null) {
			for (FieldError error: this.errors) {
				String message = error.getMessage(this.label.getName());
				stringList.add(message);
			}
		}
		return stringList;
	}
	
	/*Set required Validators*/
	public void setValidatorRequired() {
		this.validatorRequired = true;
	}
	
	public void setValidatorMaxLength(int length) {
		this.validatorMaxLength = length;
	}
	
	public void setValidatorMinLength(int length) {
		this.validatorMinLength = length;
	}
	
	/*Generic Validators*/
	protected boolean isRequired() {	
		
		if (validatorRequired) {
			FieldError error = FieldError.REQUIRED;
			if (this.value == null) {
				addError(error);
				logger.info(error.getMessage(label.getName()));
				return false;
			}
				
			if (this.value.equals("")) {
				addError(error);
				logger.info(error.getMessage(label.getName()));
				return false;
			}
		}
		
		return true;
	}
	
	protected boolean isMinLength() {
		
		if (this.validatorMinLength > 0) {
			FieldError error = FieldError.MIN_LENGTH;
			if (this.value.length() < this.validatorMinLength) {
				logger.info(error.getMessage(label.getName()));
				addError(error);
				return false;
			}
		}
		
		return true;
	}
	
	protected boolean isMaxLength() {
		
		if (this.validatorMaxLength > 0) {
			FieldError error = FieldError.MAX_LENGTH;
			if (this.value.length() > this.validatorMaxLength) {
				logger.info(error.getMessage(label.getName()));
				addError(error);
				return false;
			}
		}
		
		return true;
	}
	
	protected boolean isValidPattern(FieldError error) {
		String pattern = error.getPattern();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(this.value);
		if (!m.find()) {
			logger.info(error.getMessage(label.getName()));
			addError(error);
			return false;
		}
		
		return true;
	}
	
	public abstract boolean validate();
}