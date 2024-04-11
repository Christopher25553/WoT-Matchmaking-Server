package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Nomad extends Tank {
    public Nomad() {
        super("28", "Nomad", 5000, 8, TankType.TANK_DESTROYER);
    }
}
