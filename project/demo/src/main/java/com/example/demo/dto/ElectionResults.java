package com.example.demo.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ElectionResults {

    private int id;

    private String title;

    private String winner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public HashMap<String, Integer> getResults() {
        return results;
    }

    public void setResults(HashMap<String, Integer> results) {
        this.results = results;
    }

    private HashMap<String, Integer>  results;
}