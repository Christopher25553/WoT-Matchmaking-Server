package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class TP53 extends Tank {
    public TP53() {
        super("25", "53TP", 5750, 8, TankType.HEAVY);
    }
}
