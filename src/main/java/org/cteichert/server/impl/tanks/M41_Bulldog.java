package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class M41_Bulldog extends Tank {
    public M41_Bulldog() {
        super("8", "M41 Bulldog", 4000, 8, TankType.LIGHT);
    }
}
