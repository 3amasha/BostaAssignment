package bosta.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForgetPasswordRequest {
    public String email;

    public ForgetPasswordRequest(String email) {
        this.email = email;
    }
}
