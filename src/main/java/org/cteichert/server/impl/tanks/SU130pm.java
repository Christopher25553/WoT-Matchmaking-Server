package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class SU130pm extends Tank {
    public SU130pm() {
        super("21", "SU 130 pm", 7000, 8, TankType.TANK_DESTROYER);
    }
}
