package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class KaRi extends Tank {
    public KaRi() {
        super("20", "Ka Ri", 7200, 8, TankType.TANK_DESTROYER);
    }
}
