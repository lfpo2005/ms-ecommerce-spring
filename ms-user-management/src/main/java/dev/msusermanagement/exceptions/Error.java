package dev.msusermanagement.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "reason")
    private String reason;

    @JsonProperty(value = "message")
    private String message;

    @JsonProperty(value = "status")
    private String status;

}
