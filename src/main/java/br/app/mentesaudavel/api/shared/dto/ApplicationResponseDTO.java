package br.app.mentesaudavel.api.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.hateoas.Link;

import java.util.List;

@Getter
@Data
@Builder
public class ApplicationResponseDTO<T> {

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("details")
    private T details;

    @JsonProperty("links")
    private List<Link> links;

    public ApplicationResponseDTO(Integer status, String message, T details, List<Link> links) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.links = links;
    }
}

