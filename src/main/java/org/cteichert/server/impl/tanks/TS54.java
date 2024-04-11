package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class TS54 extends Tank {
    public TS54() {
        super("23", "TS-54", 6750, 8, TankType.HEAVY);
    }
}
