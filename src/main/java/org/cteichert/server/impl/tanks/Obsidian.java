package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Obsidian extends Tank {
    public Obsidian() {
        super("117", "Obsidian", 3350, 8, TankType.MEDIUM);
    }
}
