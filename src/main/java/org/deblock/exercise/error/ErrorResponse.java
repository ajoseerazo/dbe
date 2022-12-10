package org.deblock.exercise.error;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents errors objects to being sent as JSON to requests
 */
public class ErrorResponse {
    @JsonProperty
    private String reason;

    public ErrorResponse(String reason) {
        this.reason = reason;
    }
}
