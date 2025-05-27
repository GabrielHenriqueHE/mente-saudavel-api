package br.app.mentesaudavel.api.shared.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Map;

@Builder
@Data
public class ErrorDetailsDTO {

    @JsonProperty("path")
    private String path;

    @JsonProperty("method")
    private String method;

    @JsonProperty("errors")
    private Map<String, String[]> errors;

    @JsonProperty("timestamp")
    private OffsetDateTime timestamp;
}
