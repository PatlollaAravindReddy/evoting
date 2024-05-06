package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
@Slf4j
public class WebAppOutboundController {
    private final WebAppOutbound.PubsubOutboundGateway messagingGateway;

    public WebAppOutboundController(WebAppOutbound.PubsubOutboundGateway messagingGateway) {
        this.messagingGateway = messagingGateway;
    }

    @PostMapping("/")
    public void sendMessage(@RequestBody String message){
        System.out.println("Send this message to outbound channgel ");
        messagingGateway.sendToPubsub(message);
    }


}