package com.udla.management.exceptions;

public class ManagementException extends Exception {

    public ManagementException(final String message) {super(message);}

    public ManagementException(final String message, final Throwable cause) {super(message, cause);}

    public ManagementException(final Throwable cause) {super(cause);}

}
