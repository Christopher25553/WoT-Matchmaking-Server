package org.cteichert.server.responses;

import lombok.Data;
import org.cteichert.server.bean.User;

@Data
public class LoginUserResponse {
    private User user;
    private String token;

    public LoginUserResponse() {
    }

    public LoginUserResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public String getType() {
        return "LoginUserResponse";
    }
}