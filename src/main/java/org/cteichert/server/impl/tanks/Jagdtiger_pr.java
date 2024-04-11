package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Jagdtiger_pr extends Tank {
    public Jagdtiger_pr() {
        super("19", "Jagdtiger Prototyp", 5700, 8, TankType.TANK_DESTROYER);
    }
}
