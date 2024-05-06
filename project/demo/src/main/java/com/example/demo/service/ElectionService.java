package com.example.demo.service;

import com.example.demo.WebAppOutbound;
import com.example.demo.dto.Election;
import com.example.demo.dto.ElectionResults;
import com.example.demo.dto.VoteMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service

public class ElectionService {

    private final WebAppOutbound.PubsubOutboundGateway messagingGateway;


//    @Autowired
//    @Qualifier("createResultsDirectory")
    private HashMap<Integer, ElectionResults> electionResults;

//    @Autowired
//    @Qualifier("createElectionDirectory")
    private HashMap<Integer, Election> electionDirectory;


    public ElectionService(WebAppOutbound.PubsubOutboundGateway messagingGateway) {
        this.messagingGateway = messagingGateway;
        this.electionDirectory = new HashMap<>();
        this.electionResults = new HashMap<>();
    }

    public void sendResults(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        VoteMessage voteMessage;
        try {
            voteMessage = objectMapper.readValue(message, VoteMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Election election = electionDirectory.get(voteMessage.getId());
        if(!election.getCandidates().contains(voteMessage.getChoice())) {
            System.out.println("Invalid candidate: " + voteMessage);
        } else if(electionResults.get(election.getId()) == null) {
            ElectionResults electionResults = new ElectionResults();
            electionResults.setId(election.getId());
            electionResults.setTitle(election.getTitle());
            electionResults.setResults(new HashMap<>());
            electionResults.getResults().put(voteMessage.getChoice(),  1);

            System.out.println(electionResults);
            this.electionResults.put(election.getId(), electionResults);
        } else {

            Integer currentResult = this.electionResults.get(election.getId()).getResults().get(voteMessage.getChoice());
            this.electionResults.get(election.getId()).getResults().put(voteMessage.getChoice(),  currentResult != null ? currentResult + 1 : 1);
        }
        System.out.println(this.electionResults);
    }

    public void addElection(Election message) {
        this.electionDirectory.put(message.getId(), message);
    }
}
