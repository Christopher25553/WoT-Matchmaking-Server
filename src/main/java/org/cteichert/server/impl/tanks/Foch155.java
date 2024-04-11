package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Foch155 extends Tank {
    public Foch155() {
        super("47", "Foch 155", 7500, 10, TankType.TANK_DESTROYER);
    }
}
