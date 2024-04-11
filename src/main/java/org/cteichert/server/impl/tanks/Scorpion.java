package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Scorpion extends Tank {
    public Scorpion() {
        super("22", "Scorpion", 5800, 8, TankType.TANK_DESTROYER);
    }
}
