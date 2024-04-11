package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Lion extends Tank {
    public Lion() {
        super("34", "Lion", 8125, 10, TankType.MEDIUM);
    }
}
