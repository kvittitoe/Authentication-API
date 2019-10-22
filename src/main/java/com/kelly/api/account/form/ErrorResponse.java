package com.kelly.api.account.form;

import com.kelly.api.account.form.error.FormError;

public interface ErrorResponse {

	abstract FormError getFormError();
}
