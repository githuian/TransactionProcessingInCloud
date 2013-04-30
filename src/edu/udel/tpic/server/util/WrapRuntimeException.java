package edu.udel.tpic.server.util;

public class WrapRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param cause
     *            the cause
     */
    public WrapRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     * 
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public WrapRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
