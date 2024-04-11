package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class WZ_132_1 extends Tank {
    public WZ_132_1() {
        super("42", "WZ-132-1", 7200, 10, TankType.LIGHT);
    }
}
