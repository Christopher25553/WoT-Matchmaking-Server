package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Mars extends Tank {
    public Mars() {
        super("1.1", "Mars", 5000, 8, TankType.MEDIUM);
    }
}
