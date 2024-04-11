package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class T100LT extends Tank {
    public T100LT() {
        super("7", "T-100 LT", 7500, 10, TankType.LIGHT);
    }
}
