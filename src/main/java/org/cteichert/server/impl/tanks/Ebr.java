package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Ebr extends Tank {
    public Ebr() {
        super("2", "EBR", 3500, 8, TankType.LIGHT);
    }
}
