package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Loewe extends Tank {
    public Loewe() {
        super("5", "Loewe", 4000, 8, TankType.HEAVY);
    }
}
