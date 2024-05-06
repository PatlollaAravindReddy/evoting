package com.example.demo;

import com.example.demo.dto.Election;
import com.example.demo.dto.VoteMessage;
import com.example.demo.service.ElectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/send")
@Slf4j
public class WebAppOutboundController {

    @Autowired
    ElectionService electionService;

    private final WebAppOutbound.PubsubOutboundGateway messagingGateway;

    public WebAppOutboundController(WebAppOutbound.PubsubOutboundGateway messagingGateway) {
        this.messagingGateway = messagingGateway;
    }

    @PostMapping("/")
    public void sendMessage(@RequestBody Election message){
        messagingGateway.sendToPubsub(message.toString());
        electionService.addElection(message);
        System.out.println("Successfully send message to outbound channels ");
    }
}