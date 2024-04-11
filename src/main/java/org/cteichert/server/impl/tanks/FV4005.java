package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class FV4005 extends Tank {
    public FV4005() {
        super("45", "FV4005", 8000, 10, TankType.TANK_DESTROYER);
    }
}
