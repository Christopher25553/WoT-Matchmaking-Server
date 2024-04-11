package org.cteichert.server.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.cteichert.server.bean.MatchmakingUser;
import org.cteichert.server.requests.MatchmakingRequest;
import org.cteichert.server.services.MatchmakingService;
import org.cteichert.server.services.TankService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matchmaking")
@CrossOrigin(origins = "*")
@Slf4j
public final class MatchmakingController {
    private final MatchmakingService matchmakingService;
    private final TankService tankService;

    public MatchmakingController(MatchmakingService matchmakingService, TankService tankService) {
        this.matchmakingService = matchmakingService;
        this.tankService = tankService;
    }

    @PostMapping("/addToWaitingList")
    public void addToWaitingList(@RequestBody MatchmakingRequest matchmakingRequest) {
        matchmakingService.addToWaitingList(
                new MatchmakingUser(
                        matchmakingRequest.getUserId(),
                        tankService.findTankById(matchmakingRequest.getTankId()),
                        matchmakingRequest.getGameMode()));
    }
}