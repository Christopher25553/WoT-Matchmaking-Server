package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class B121 extends Tank {
    public B121() {
        super("37", "121B", 8900, 10, TankType.MEDIUM);
    }
}
