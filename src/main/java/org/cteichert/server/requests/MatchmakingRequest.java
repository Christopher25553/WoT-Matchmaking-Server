package org.cteichert.server.requests;

import lombok.Data;
import org.cteichert.server.bean.GameMode;

@Data
public class MatchmakingRequest {
    private String userId;
    private String tankId;
    private GameMode gameMode;
}
