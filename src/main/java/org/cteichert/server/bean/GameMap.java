package org.cteichert.server.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class GameMap {
    protected MatchmakingLevelType matchmakingLevelType;
    protected GameMode gameMode;
    protected List<MatchmakingUser> matchmakingTeamAList = new ArrayList<>();
    protected List<MatchmakingUser> matchmakingTeamBList = new ArrayList<>();
    protected Map<TankType, Integer> validTankTypesPerMap = new HashMap<>();

    protected abstract void configureValidTankTypesPerMap();

    public GameMap() {
        configureValidTankTypesPerMap();
    }
}
