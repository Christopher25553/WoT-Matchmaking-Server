package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Sheridan extends Tank {
    public Sheridan() {
        super("41", "Sheridan", 8100, 10, TankType.LIGHT);
    }
}
