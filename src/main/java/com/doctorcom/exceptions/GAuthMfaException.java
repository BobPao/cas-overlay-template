package com.doctorcom.exceptions;

import javax.security.auth.login.LoginException;

public class GAuthMfaException extends LoginException {

    public GAuthMfaException() {
    }

    public GAuthMfaException(String msg) {
        super(msg);
    }
}
