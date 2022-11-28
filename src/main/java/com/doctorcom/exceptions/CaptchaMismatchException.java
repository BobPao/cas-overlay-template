package com.doctorcom.exceptions;

import javax.security.auth.login.LoginException;

public class CaptchaMismatchException extends LoginException {

    public CaptchaMismatchException(String msg) {
        super(msg);
    }

    public CaptchaMismatchException() {
        super();
    }

}
