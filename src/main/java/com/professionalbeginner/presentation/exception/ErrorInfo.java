package com.professionalbeginner.presentation.exception;

/**
 * @author Kempenich Florian
 */
public class ErrorInfo {

    public final String url;
    public final String exception;

    public ErrorInfo(String url, Exception exception) {
        this.url = url;
        this.exception = exception.getLocalizedMessage();
    }

}
