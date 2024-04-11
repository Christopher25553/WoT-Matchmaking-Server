package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Manticore extends Tank {
    public Manticore() {
        super("38", "Manticore", 9100, 10, TankType.LIGHT);
    }
}
