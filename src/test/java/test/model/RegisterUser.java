package test.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUser {
    private String email, password;
}