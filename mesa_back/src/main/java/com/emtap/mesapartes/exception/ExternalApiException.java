package com.emtap.mesapartes.exception;

public class ExternalApiException extends RuntimeException {
    private final int statusCode;
    private final String responseBody;

    public ExternalApiException(int statusCode, String responseBody) {
        super("Error externo (" + statusCode + "): " + responseBody);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
