package org.cteichert.server.impl.tanks;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.bean.TankType;

public class Mkpz68P extends Tank {
    public Mkpz68P() {
        super("29", "MKpz 68 (P)", 4880, 8, TankType.MEDIUM);
    }
}
