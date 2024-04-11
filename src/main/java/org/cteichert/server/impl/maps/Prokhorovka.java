package org.cteichert.server.impl.maps;

import org.cteichert.server.bean.GameMap;
import org.cteichert.server.bean.TankType;

public class Prokhorovka extends GameMap {
    @Override
    protected void configureValidTankTypesPerMap() {
        validTankTypesPerMap.put(TankType.HEAVY, 15);
        validTankTypesPerMap.put(TankType.MEDIUM, 15);
        validTankTypesPerMap.put(TankType.TANK_DESTROYER, 7);
        validTankTypesPerMap.put(TankType.LIGHT, 3);
    }
}
