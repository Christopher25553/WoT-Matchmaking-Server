package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class TP50_Proto extends Tank {
    public TP50_Proto() {
        super("24", "50TP Proto", 6150, 8, TankType.HEAVY);
    }
}
