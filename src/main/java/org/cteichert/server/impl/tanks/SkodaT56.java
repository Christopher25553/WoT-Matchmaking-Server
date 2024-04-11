package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class SkodaT56 extends Tank {
    public SkodaT56() {
        super("11", "Skoda T56", 7400, 8, TankType.HEAVY);
    }
}
