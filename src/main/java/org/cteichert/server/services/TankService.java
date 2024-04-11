package org.cteichert.server.services;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.utils.ReflectionHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TankService {
    private final Map<String, Tank> cachedTanks = new HashMap<>();

    public TankService() {
        try {
            List<Class<? extends Tank>> classesByType = ReflectionHelper.getClassesByType(Tank.class);
            for (Class<? extends Tank> aClass : classesByType) {
                Tank tank = aClass.getConstructor().newInstance();
                cachedTanks.put(tank.getTankId(), tank);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Tank findTankById(String tankId) {
        return cachedTanks.get(tankId);
    }
}
