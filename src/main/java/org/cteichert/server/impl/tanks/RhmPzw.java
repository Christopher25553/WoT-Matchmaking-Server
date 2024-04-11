package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class RhmPzw extends Tank {
    public RhmPzw() {
        super("43", "Rhm. Pzw.", 7750, 10, TankType.LIGHT);
    }
}
