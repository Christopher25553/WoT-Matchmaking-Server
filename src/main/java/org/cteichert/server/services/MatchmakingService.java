package org.cteichert.server.services;

import lombok.extern.slf4j.Slf4j;
import org.cteichert.server.bean.*;
import org.cteichert.server.utils.Maths;
import org.cteichert.server.utils.ReflectionHelper;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MatchmakingService {
    /**
     * User+Tank information and the Long-Number which represents the time the user is currenty waiting in seconds
     **/
    private final Map<MatchmakingUser, Integer> waitingUserTimeLeftMap = new HashMap<>();
    private final List<GameMap> currentUnfilledMaps = new ArrayList<>();
    private final List<GameMap> allAvailableMaps = new ArrayList<>();

    public MatchmakingService() {
        try {
            List<Class<? extends GameMap>> classesByType = ReflectionHelper.getClassesByType(GameMap.class);
            for (Class<? extends GameMap> aClass : classesByType) {
                allAvailableMaps.add(aClass.getConstructor().newInstance());
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        startMatchmakingProcess();
    }

    public void addToWaitingList(MatchmakingUser matchmakingUser) {
        waitingUserTimeLeftMap.put(matchmakingUser, 0);
    }

    public void startMatchmakingProcess() {
        try {
            new Thread(() -> {
                //TODO waitinglist durchlaufen
                //TODO alle gamemaps durchlaufen und schauen ob es ein match gibt
                //TODO falls nicht wartet der user 10 sekunden und wird danach nochmal überprüft
                //TODO wenn ein user 10 sekunden erreicht hat ohne ein match wird eine neue GameMap aufgemacht worin der user hinzugefügt wird
                // es sei denn es gibt bereits 20 maps welche noch nicht gefüllt wurden.
                // wartet ein user länger als 30 sekunden wird die power toleranz von 25% abweichung auf 30% erhöt und bei 60 sekunden auf 40%
                // Danach gibt es keine weiteren Erhöhungen!
                //TODO geprüft wird: level(100%), tankType(100%), power(25%,30%,40%-> wie oben beschrieben).
                //TODO ebenso darf die Obergrenze der tankTypes, welche innerhalb der GameMap->validTankTypesPerMap steht nicht überschritten werden!

                //TODO if GameMap is completely fullfilled then execute messageAllClientsForGameStart method to notify all clients with the result!
                while (true) {
                    try {
                        for (MatchmakingUser matchmakingUser : waitingUserTimeLeftMap.keySet()) {
                            Tank matchTank = matchmakingUser.getTank();

                            if (currentUnfilledMaps.isEmpty()) {
                                addNewGameMap(matchmakingUser);
                            }

                            boolean found = false;
                            for (int i = 0; i < currentUnfilledMaps.size(); i++) {
                                GameMap gameMap = currentUnfilledMaps.get(i);

                                if (checkTankLevelMatchLevelType(gameMap, matchTank)
                                        && checkTankTypeMatchForGameMap(gameMap, matchTank)) {
                                    if (gameMap.getMatchmakingTeamAList().size() < gameMap.getGameMode().getPlayerCount()) {
                                        gameMap.getMatchmakingTeamAList().add(matchmakingUser);

                                        waitingUserTimeLeftMap.remove(matchmakingUser);
                                        found = true;

                                        break;
                                    } else if (gameMap.getMatchmakingTeamBList().size() < gameMap.getGameMode().getPlayerCount()
                                            && checkTankTypeMatchForTeamBalance(gameMap, matchTank)) {
                                        Integer currentlyWaintingInSeconds = waitingUserTimeLeftMap.get(matchmakingUser);

                                        if (matchTankPower(
                                                gameMap,
                                                matchTank,
                                                currentlyWaintingInSeconds)) {
                                            gameMap.getMatchmakingTeamBList().add(matchmakingUser);

                                            waitingUserTimeLeftMap.remove(matchmakingUser);
                                            found = true;

                                            // check if gameMap is full
                                            if (gameMap.getMatchmakingTeamBList().size() == gameMap.getGameMode().getPlayerCount()) {
                                                informAllClientsForGameStart(gameMap);
                                                currentUnfilledMaps.remove(gameMap);
                                            }

                                            break;
                                        }
                                    }
                                }
                            }

                            if (!found) {
                                GameMap map = currentUnfilledMaps.stream()
                                        .filter(gameMap -> checkTankLevelMatchLevelType(gameMap, matchTank))
                                        .filter(gameMap -> checkTankTypeMatchForGameMap(gameMap, matchTank))
                                        .filter(gameMap -> checkTankTypeMatchForTeamBalance(gameMap, matchTank))
                                        .findAny()
                                        .orElse(null);
                                // we have no map which would match the MatchLevel of any gameMap so we have to create a new one
                                if (map == null) {
                                    GameMap gameMap = this.addNewGameMap(matchmakingUser);
                                    gameMap.getMatchmakingTeamAList().add(matchmakingUser);
                                    waitingUserTimeLeftMap.remove(matchmakingUser);
                                }
                            }
                        }
                    } catch (Exception ex) {
                    }
//                    try {
//                        Thread.sleep(100);
//                        System.out.println("Still waiting " + waitingUserTimeLeftMap.keySet().size());
//                        System.out.println("Still unfilled " + currentUnfilledMaps.size());
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
                }
            }).start();
        } catch (Exception e) {
            log.error("Error while try find a Match", e);
        }
    }

    private GameMap addNewGameMap(MatchmakingUser matchmakingUser) {
        int randomMap = Maths.getRandomNumberInRange(0, allAvailableMaps.size());
        try {
            GameMap newGameMap = allAvailableMaps.get(randomMap).getClass().getConstructor().newInstance();

            int randomLevelType = Maths.getRandomNumberInRange(0, MatchmakingLevelType.values().length-1);
            newGameMap.setMatchmakingLevelType(MatchmakingLevelType.values()[randomLevelType]);
            newGameMap.setGameMode(matchmakingUser.getGameMode());

            currentUnfilledMaps.add(newGameMap);

            return newGameMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkTankTypeMatchForGameMap(GameMap gameMap, Tank matchTank) {
        TankType matchTankType = matchTank.getType();

        long currentTankTypeCount = gameMap.getMatchmakingTeamAList().stream()
                .map(MatchmakingUser::getTank)
                .map(Tank::getType)
                .filter(matchTankType::equals)
                .count();

        return gameMap.getValidTankTypesPerMap().get(matchTankType) > currentTankTypeCount;
    }

    private boolean checkTankTypeMatchForTeamBalance(GameMap gameMap, Tank matchTank) {
        long countA = gameMap.getMatchmakingTeamAList().stream()
                .map(MatchmakingUser::getTank)
                .map(Tank::getType)
                .filter(type -> matchTank.getType().equals(type))
                .count();

        long countB = gameMap.getMatchmakingTeamBList().stream()
                .map(MatchmakingUser::getTank)
                .map(Tank::getType)
                .filter(type -> matchTank.getType().equals(type))
                .count();

        return countA > countB;
    }

    private boolean checkTankLevelMatchLevelType(GameMap gameMap, Tank matchTank) {
        MatchmakingLevelType levelType = gameMap.getMatchmakingLevelType();
        switch (levelType) {
            case PLUS_ONE -> {
                int maxLevel = gameMap.getMatchmakingTeamAList().stream()
                        .map(MatchmakingUser::getTank)
                        .max(Comparator.comparingInt(Tank::getLevel))
                        .map(Tank::getLevel)
                        .orElse(matchTank.getLevel());

                if (matchTank.getLevel() == maxLevel || matchTank.getLevel() + 1 == maxLevel) {
                    return true;
                }
            }
            case EQUALS -> {
                int maxLevel = gameMap.getMatchmakingTeamAList().stream()
                        .map(MatchmakingUser::getTank)
                        .max(Comparator.comparingInt(Tank::getLevel))
                        .map(Tank::getLevel)
                        .orElse(matchTank.getLevel());

                if (matchTank.getLevel() == maxLevel) {
                    return true;
                }
            }
            case MINUS_ONE -> {
                int minLevel = gameMap.getMatchmakingTeamAList().stream()
                        .map(MatchmakingUser::getTank)
                        .min(Comparator.comparingInt(Tank::getLevel))
                        .map(Tank::getLevel)
                        .orElse(matchTank.getLevel());

                if (matchTank.getLevel() == minLevel || matchTank.getLevel() - 1 == minLevel) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matchTankPower(GameMap gameMap, Tank matchTank, int currentlyWaintingInSeconds) {
//        List<MatchmakingUser> matchmakingTeamAList = gameMap.getMatchmakingTeamAList();
//        List<MatchmakingUser> matchmakingUserList = matchmakingTeamAList.subList(gameMap.getMatchmakingTeamBList().size(), matchmakingTeamAList.size());
//        List<Tank> tanksFromTeamA = matchmakingUserList.stream()
//                .map(MatchmakingUser::getTank)
//                .toList();
//
//        for (Tank tankFromTeamA : tanksFromTeamA) {
//            double powerDifferenceInPercent = 100 - ((double) 100 / Math.max(tankFromTeamA.getPower(), matchTank.getPower()) * Math.min(tankFromTeamA.getPower(), matchTank.getPower()));
//
//            double powerMaxDifference = 45;
////            if (currentlyWaintingInSeconds > 60) {
////                powerMaxDifference = 45;
////            } else if (currentlyWaintingInSeconds > 30) {
////                powerMaxDifference = 35;
////            }
//
//            if (Maths.round(powerDifferenceInPercent, 0) <= powerMaxDifference) {
                return true;
//            }
//        }
//        return false;
    }

    /**
     * Method which has to invoke all clients
     */
    public void informAllClientsForGameStart(GameMap gameMap) {
        List<MatchmakingUser> list1 = gameMap.getMatchmakingTeamAList();
        List<MatchmakingUser> list2 = gameMap.getMatchmakingTeamBList();

        list1.sort((o1, o2) -> {
            Tank tank1 = o1.getTank();
            Tank tank2 = o2.getTank();

            int compareLevel = Integer.compare(tank2.getLevel(), tank1.getLevel());
            if (compareLevel != 0) {
                return compareLevel;
            }

            int compareTankType = tank2.getType().compareTo(tank1.getType());
            if (compareTankType != 0) {
                return compareTankType;
            }

            return Integer.compare(tank2.getPower(), tank1.getPower());
        });
        list2.sort((o1, o2) -> {
            Tank tank1 = o1.getTank();
            Tank tank2 = o2.getTank();

            int compareLevel = Integer.compare(tank2.getLevel(), tank1.getLevel());
            if (compareLevel != 0) {
                return compareLevel;
            }

            int compareTankType = tank2.getType().compareTo(tank1.getType());
            if (compareTankType != 0) {
                return compareTankType;
            }

            return Integer.compare(tank2.getPower(), tank1.getPower());
        });


        Table textTable = new Table(8, BorderStyle.DESIGN_CASUAL, ShownBorders.ALL, true);

        textTable.addCell("User");
        textTable.addCell("Tank");
        textTable.addCell("Tank Class");
        textTable.addCell("Tank Level");
//        textTable.addCell("Tank Power");

        textTable.addCell("User");
        textTable.addCell("Tank");
        textTable.addCell("Tank Class");
        textTable.addCell("Tank Level");
//        textTable.addCell("Tank Power");

        for (int i = 0; i < list1.size(); i++) {
            MatchmakingUser userTeamA = list1.get(i);
            textTable.addCell(userTeamA.getUserId());

            Tank tankTeamA = userTeamA.getTank();
            textTable.addCell(tankTeamA.getName());
            textTable.addCell(tankTeamA.getType().name());
            textTable.addCell(String.valueOf(tankTeamA.getLevel()));
//            textTable.addCell(String.valueOf(tankTeamA.getPower()));

            MatchmakingUser userTeamB = list2.get(i);
            textTable.addCell(userTeamB.getUserId());

            Tank tankTeamB = userTeamB.getTank();
            textTable.addCell(tankTeamB.getName());
            textTable.addCell(tankTeamB.getType().name());
            textTable.addCell(String.valueOf(tankTeamB.getLevel()));
//            textTable.addCell(String.valueOf(tankTeamB.getPower()));
        }
        System.out.println(gameMap.getClass().getSimpleName() + " (" + gameMap.getGameMode().getName() + ")");
//        System.out.println("Matchmaking-LevelType = " + gameMap.getMatchmakingLevelType().name());
        System.out.println(textTable.render());
        System.out.println();
        System.out.println();
    }
}
