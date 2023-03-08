package test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
    private String id, name, job, createdAt, updateAt, token;
}