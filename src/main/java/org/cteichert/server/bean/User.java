package org.cteichert.server.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;

@Data
public class User {
    @Id
    private String userId;
    @NotBlank
    @JsonProperty("username")
    private String userName;
    @NotBlank
    @JsonIgnore
    private String pwd;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }
}
