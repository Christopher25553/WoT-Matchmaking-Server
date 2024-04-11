package org.cteichert.server.responses;

import lombok.Data;
import org.cteichert.server.bean.User;

@Data
public class CreateUserResponse {
    private User user;

    public CreateUserResponse() {
    }

    public CreateUserResponse(User user) {
        this.user = user;
    }

    public String getType() {
        return "CreateUserResponse";
    }
}
