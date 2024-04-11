package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Minotauro extends Tank {
    public Minotauro() {
        super("33", "Minotauro", 8450, 10, TankType.TANK_DESTROYER);
    }
}
