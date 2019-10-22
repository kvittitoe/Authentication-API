package com.kelly.api.account.form.field;

public enum FieldError {

	MIN_LENGTH() {
		@Override
		public boolean isMinLength() {return true;}
		
		@Override
		public String getMessage(String label) {
			return label + " must be at least " + minLength + " characters in length!";
		};
	},
	MAX_LENGTH() {
		@Override
		public boolean isMaxLength() {return true;}

		@Override
		public String getMessage(String label) {
			return label + " must be no more than " + maxLength + " characters in length!";
		};
	},
	EMAIL () {
		@Override
		public boolean isEmail() {return true;}

		@Override
		public String getMessage(String label) {
			return label + " must be a valid email address!";
		};
		
		@Override
		public String getPattern() {
			return 
				"^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		}
	},
	SPECIAL() {
		@Override
		public boolean isSpecial() {return true;}

		@Override
		public String getMessage(String label) {
			return label + " must contain at least one special character!";
		};
		
		@Override
		public String getPattern() {return "[~!@#$%^&*?]+";}
	},
	NUMBER() {
		public boolean isNumber() {return true;}

		@Override
		public String getMessage(String label) {
			return label + " must contain at least one number!";
		};
		
		@Override
		public String getPattern() {return "[0-9]+";}
	},
	LOWER() {
		public boolean isLower() {return true;}

		@Override
		public String getMessage(String label) {
			return label + " must contain at least one lowercase letter!";
		};
		
		@Override
		public String getPattern() {return "[a-z]+";}
	},
	UPPER() {
		public boolean isUpper() {return true;}

		@Override
		public String getMessage(String label) {
			return label + " must contain at least one uppercase letter!";
		};
		
		@Override
		public String getPattern() {return "[A-Z]+";}
	},
	LETTERS_NUMBERS_ONLY() {
		public boolean isLettersAndNumbers() {return true;}

		@Override
		public String getMessage(String label) {
			return label + " may only contain letter and number characters!";
		};
		
		@Override
		public String getPattern() {return "^[A-Za-z0-9 ]+$";}
	},
	LETTERS_ONLY() {
		public boolean isLetters() {return true;}

		@Override
		public String getMessage(String label) {
			return label + " may only contain letter characters!";
		};
		
		@Override
		public String getPattern() {return "^[A-Za-z -]+$";}
	},
	REQUIRED() {
		public boolean isRequired() {return true;}

		@Override
		public String getMessage(String label) {
			return label + " is a required field!";
		};
	},
	TOKEN() {
		public boolean isValidToken() {return true;}

		@Override
		public String getMessage(String label) {
			return label + " is an invalid token!";
		};
	};
	
	private static int minLength;
	private static int maxLength;
	
	public boolean isMinLength() {return false;}
	public boolean isMaxLength() {return false;}
	public boolean isEmail() {return false;}
	public boolean isSpecial() {return false;}
	public boolean isNumber() {return false;}
	public boolean isLower() {return false;}
	public boolean isUpper() {return false;}
	public boolean isLettersAndNumbers() {return false;}
	public boolean isLetters() {return false;}
	public boolean isRequired() {return false;}
	public boolean isValidToken() {return false;}
	
	public String getLabel() {return null;}
	public String getPattern() {return null;}
	
	public String getMessage(String label) {return null;}
	
	public void setMinLength(int length) {
		FieldError.minLength = length;
	}
	
	public void setMaxLength(int length) {
		FieldError.maxLength = length;
	}
}
