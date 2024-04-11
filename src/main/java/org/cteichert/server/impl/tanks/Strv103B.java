package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Strv103B extends Tank {
    public Strv103B() {
        super("44", "Strv. 103B", 9000, 10, TankType.TANK_DESTROYER);
    }
}
