package com.akveo.bundlejava.mailing;


public class SendMailException extends Exception {

    public SendMailException(String message) {
        super(message);
    }

    public SendMailException(Throwable exception) {
        super(exception);
    }
}
