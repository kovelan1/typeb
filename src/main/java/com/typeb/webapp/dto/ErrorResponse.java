package com.typeb.webapp.dto;

/**
 * Error body returned by the API, serialised as {@code { "error": "<message>" }}.
 */
public record ErrorResponse(String error) {

    public static final ErrorResponse INVALID_INPUT = new ErrorResponse("Invalid Input");
}
