package br.app.mentesaudavel.api.modules.authentication.application.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponseDTO {
    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("expiresIn")
    Long expiresIn;
}
