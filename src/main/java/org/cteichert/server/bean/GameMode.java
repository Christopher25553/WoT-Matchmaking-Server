package org.cteichert.server.bean;

import lombok.Data;

@Data
public abstract class GameMode {
    protected String name;
    protected int playerCount;

    public GameMode(String name, int playerCount) {
        this.name = name;
        this.playerCount = playerCount;
    }
}
