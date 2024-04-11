package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class WZ_111_QL extends Tank {
    public WZ_111_QL() {
        super("36", "WZ-111 QL", 8450, 10, TankType.HEAVY);
    }
}
