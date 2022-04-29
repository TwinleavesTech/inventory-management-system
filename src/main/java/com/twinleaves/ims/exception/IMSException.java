package com.twinleaves.ims.exception;

public class IMSException extends RuntimeException {

    private String message;
    private Exception exception;
    private FaultCode faultCode;

    public IMSException(String message) {
        super(message);
        this.message = message;
    }

    public IMSException(String message, Exception exception) {
        super(message, exception);
        this.message = message;
        this.exception = exception;
    }

    public IMSException(String message, FaultCode faultCode) {
        super(message);
        this.message = message;
        this.faultCode = faultCode;
    }

    public IMSException(String message, Exception exception, FaultCode faultCode) {
        super(message, exception);
        this.message = message;
        this.exception = exception;
        this.faultCode = faultCode;
    }

}
