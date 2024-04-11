package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class SuperConqueror extends Tank {
    public SuperConqueror() {
        super("6", "Super Conqueror", 10000, 10, TankType.HEAVY);
    }
}
