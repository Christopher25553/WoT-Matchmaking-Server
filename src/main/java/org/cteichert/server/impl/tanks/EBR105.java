package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class EBR105 extends Tank {
    public EBR105() {
        super("39", "EBR 105", 9500, 10, TankType.LIGHT);
    }
}
