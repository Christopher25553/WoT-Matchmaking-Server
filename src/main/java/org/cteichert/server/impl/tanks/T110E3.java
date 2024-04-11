package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class T110E3 extends Tank {
    public T110E3() {
        super("48", "T110E3", 9800, 10, TankType.TANK_DESTROYER);
    }
}
