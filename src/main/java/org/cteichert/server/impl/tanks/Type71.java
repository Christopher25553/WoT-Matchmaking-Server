package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Type71 extends Tank {
    public Type71() {
        super("31", "Type 71", 8100, 10, TankType.HEAVY);
    }
}
