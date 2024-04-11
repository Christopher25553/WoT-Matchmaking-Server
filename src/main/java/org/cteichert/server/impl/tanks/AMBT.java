package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class AMBT extends Tank {
    public AMBT() {
        super("14", "AMBT", 6200, 8, TankType.MEDIUM);
    }
}
