package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class Election {



    private int id;

    private String title;

    private List<String> candidates;

    private String starting;

    private String duration;

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

    public List<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<String> candidates) {
        this.candidates = candidates;
    }

    public String getStarting() {
        return starting;
    }

    public void setStarting(String starting) {
        this.starting = starting;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString(){
        return "{" + "\n" +
                    "\"id\": "+ id +",\n" +
                    "\"title\": " + title +",\n" +
                    "\"candidates\": " + candidates.toString()+",\n" +
                    "\"starting\": " + starting + ",\n" +
                    "\"duration\": " + duration + ",\n" +
                "}";
    }
}