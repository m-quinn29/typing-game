package com.m_quinn29.typing_game.controller;

import com.m_quinn29.typing_game.model.RaceProgressMessage;
import com.m_quinn29.typing_game.model.RaceState;
import com.m_quinn29.typing_game.service.RaceService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class RaceWebSocketController {
    private final RaceService raceService;
    private final SimpMessagingTemplate messagingTemplate;

    // TODO: Configure Sockets - Springboot documentation
    public RaceWebSocketController(RaceService raceService, SimpMessagingTemplate messagingTemplate) {
        this.raceService = raceService;
        this.messagingTemplate = messagingTemplate;
    }


    @MessageMapping("/race/{raceId}/progress")
    public void progress(@DestinationVariable String raceId, RaceProgressMessage progressMessage) {
        RaceState raceState = raceService.updateRaceProgress(raceId, progressMessage);

        messagingTemplate.convertAndSend("/topic/race/" + raceId + raceState);
    }
}




