package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Saladin extends Tank {
    public Saladin() {
        super("16", "Saladin", 3000, 8, TankType.MEDIUM);
    }
}
