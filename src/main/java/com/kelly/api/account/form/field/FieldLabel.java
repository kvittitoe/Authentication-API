package com.kelly.api.account.form.field;

public enum FieldLabel {

	EMAIL() {
		@Override
		public boolean isEmail() {return true;}
		
		@Override
		public String getName() {return "Email";}
	},
	PASSWORD() {
		@Override
		public boolean isPassword() {return true;}
		
		@Override
		public String getName() {return "Password";}
	},
	FIRST_NAME() {
		@Override
		public boolean isFirst() {return true;}
		
		@Override
		public String getName() {return "First name";}
	},
	LAST_NAME() {
		@Override
		public boolean isLast() {return true;}
		
		@Override
		public String getName() {return "Last name";}
	},
	DISPLAY_NAME() {
		@Override
		public boolean isDisplayName() {return true;}
		
		@Override
		public String getName() {return "Display name";}
	},
	OLD_PASSWORD() {
		@Override
		public boolean isOldPassword() {return true;}
		
		@Override
		public String getName() {return "Current password";}
	},
	TOKEN() {
		@Override
		public boolean isToken() {return true;}
		
		@Override
		public String getName() {return "Token";}
	};
	
	public boolean isEmail() {return false;}
	public boolean isPassword() {return false;}
	public boolean isFirst() {return false;}
	public boolean isLast() {return false;}
	public boolean isDisplayName() {return false;}
	public boolean isOldPassword() {return false;}
	public boolean isToken() {return false;}
	public String getName() {return "Label";}
}
