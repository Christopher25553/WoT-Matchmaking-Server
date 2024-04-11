package org.cteichert.server.impl.maps;

import org.cteichert.server.bean.GameMap;
import org.cteichert.server.bean.TankType;

public class Malinovka extends GameMap {
    @Override
    protected void configureValidTankTypesPerMap() {
        validTankTypesPerMap.put(TankType.HEAVY, 15);
        validTankTypesPerMap.put(TankType.MEDIUM, 15);
        validTankTypesPerMap.put(TankType.TANK_DESTROYER, 6);
        validTankTypesPerMap.put(TankType.LIGHT, 3);
    }
}
