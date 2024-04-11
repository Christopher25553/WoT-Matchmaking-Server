package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class VZ55 extends Tank {
    public VZ55() {
        super("35", "VZ 55", 8590, 10, TankType.HEAVY);
    }
}
