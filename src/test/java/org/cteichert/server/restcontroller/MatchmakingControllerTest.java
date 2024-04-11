package org.cteichert.server.restcontroller;

import org.cteichert.server.bean.Tank;
import org.cteichert.server.impl.gamemodes.StandardGameMode;
import org.cteichert.server.impl.tanks.*;
import org.cteichert.server.requests.MatchmakingRequest;
import org.cteichert.server.utils.Maths;
import org.cteichert.server.utils.ReflectionHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MatchmakingControllerTest {
    @Autowired
    MatchmakingController matchmakingController;

    static List<Tank> allAvailableTanks = new ArrayList<>();

    @BeforeAll
    public static void setup() {
        try {
            List<Class<? extends Tank>> classesByType = ReflectionHelper.getClassesByType(Tank.class);
            for (Class<? extends Tank> aClass : classesByType) {
                allAvailableTanks.add(aClass.getConstructor().newInstance());
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void fullTestMatchmaking() throws InterruptedException {
        for (int userId = 0; userId <= 10_000; userId++) {
            MatchmakingRequest matchmakingRequest = new MatchmakingRequest();
            matchmakingRequest.setUserId(String.valueOf(userId));
            matchmakingRequest.setGameMode(new StandardGameMode());

            int randomTankId = Maths.getRandomNumberInRange(0, allAvailableTanks.size() - 1);
            matchmakingRequest.setTankId(allAvailableTanks.get(randomTankId).getTankId());

            matchmakingController.addToWaitingList(matchmakingRequest);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void testMatchmaking() {
        createTeamA();
        createTeamB();
    }

    private void createTeamB() {
        // tank(tankId=30, name=ISU-122-2, power=5450, level=8, type=TANK_DESTROYER), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank(tankId=15, name=ELC Even 90, power=6750, level=8, type=LIGHT), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank(tankId=26, name=Type 63, power=5200, level=8, type=HEAVY), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank(tankId=4, name=CS 53, power=3200, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank(tankId=9, name=Borrasuque, power=5900, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank(tankId=4, name=CS 53, power=3200, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank(tankId=29, name=MKpz 68 (P), power=4880, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank(tankId=1.1, name=Mars, power=5000, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank(tankId=16, name=Saladin, power=3000, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank(tankId=14, name=AMBT, power=6200, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // Tank(tankId=5, name=Loewe, power=4000, level=8, type=HEAVY), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // Tank(tankId=4, name=CS 53, power=3200, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // Tank(tankId=29, name=MKpz 68 (P), power=4880, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // Tank(tankId=19, name=Jagdtiger Prototyp, power=5700, level=8, type=TANK_DESTROYER), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // Tank(tankId=28, name=Nomad, power=5000, level=8, type=TANK_DESTROYER), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        createMatchmakingRequest(1, new ISU_122_2().getTankId());
        createMatchmakingRequest(2, new ELC_Even_90().getTankId());
        createMatchmakingRequest(3, new Type63().getTankId());
        createMatchmakingRequest(4, new CS53().getTankId());
        createMatchmakingRequest(5, new Borrasuque().getTankId());

        createMatchmakingRequest(6, new CS53().getTankId());
        createMatchmakingRequest(7, new Mkpz68P().getTankId());
        createMatchmakingRequest(8, new Mars().getTankId());
        createMatchmakingRequest(9, new Saladin().getTankId());
        createMatchmakingRequest(10, new AMBT().getTankId());

        createMatchmakingRequest(11, new Loewe().getTankId());
        createMatchmakingRequest(12, new CS53().getTankId());
        createMatchmakingRequest(13, new Mkpz68P().getTankId());
        createMatchmakingRequest(14, new Jagdtiger_pr().getTankId());
        createMatchmakingRequest(15, new Nomad().getTankId());
    }

    private void createTeamA() {
        // tank=Tank(tankId=22, name=Scorpion, power=5800, level=8, type=TANK_DESTROYER), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=28, name=Nomad, power=5000, level=8, type=TANK_DESTROYER), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=9, name=Borrasuque, power=5900, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=23, name=TS-54, power=6750, level=8, type=HEAVY), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=9, name=Borrasuque, power=5900, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=9, name=Borrasuque, power=5900, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=28, name=Nomad, power=5000, level=8, type=TANK_DESTROYER), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=16, name=Saladin, power=3000, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=10, name=Bisonte C45, power=6800, level=8, type=HEAVY), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=29, name=MKpz 68 (P), power=4880, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=117, name=Obsidian, power=3350, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=16, name=Saladin, power=3000, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=27, name=GSOR 1010, power=4880, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // tank=Tank(tankId=9, name=Borrasuque, power=5900, level=8, type=MEDIUM), gameMode=GameMode(name=Standardgefecht, playerCount=15))"
        // And
        // MatchmakingUser(userId=7, tank=Tank(tankId=2, name=EBR, power=3500, level=8, type=LIGHT), gameMode=GameMode(name=Standardgefecht, playerCount=15))
        createMatchmakingRequest(16, new Scorpion().getTankId());
        createMatchmakingRequest(17, new Nomad().getTankId());
        createMatchmakingRequest(18, new Borrasuque().getTankId());
        createMatchmakingRequest(19, new TS54().getTankId());
        createMatchmakingRequest(20, new Borrasuque().getTankId());

        createMatchmakingRequest(21, new Borrasuque().getTankId());
        createMatchmakingRequest(22, new Nomad().getTankId());
        createMatchmakingRequest(23, new Saladin().getTankId());
        createMatchmakingRequest(24, new BisonteC54().getTankId());
        createMatchmakingRequest(25, new Mkpz68P().getTankId());

        createMatchmakingRequest(26, new Obsidian().getTankId());
        createMatchmakingRequest(27, new Saladin().getTankId());
        createMatchmakingRequest(28, new GSOR1010().getTankId());
        createMatchmakingRequest(29, new Borrasuque().getTankId());
        createMatchmakingRequest(30, new Ebr().getTankId());
    }

    private void createMatchmakingRequest(int userId, String tankId) {
        MatchmakingRequest matchmakingRequest = new MatchmakingRequest();
        matchmakingRequest.setUserId(String.valueOf(userId));
        matchmakingRequest.setGameMode(new StandardGameMode());
        matchmakingRequest.setTankId(tankId);
        matchmakingController.addToWaitingList(matchmakingRequest);
    }
}