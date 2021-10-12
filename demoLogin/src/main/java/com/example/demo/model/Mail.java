package com.example.demo.model;

import java.util.List;
import java.util.Map;

public class Mail {


    private Map<String, Object> sender;


    public Map<String, Object> getSender() {
        return sender;
    }

    public void setSender(Map<String, Object> sender) {
        this.sender = sender;
    }

    public List<Map<String, Object>> getTo() {
        return to;
    }

    public void setTo(List<Map<String, Object>> to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "sender=" + sender +
                ", to=" + to +
                ", subject='" + subject + '\'' +
                '}';
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private List<Map<String, Object>> to;

    private String subject;

        public Mail() {
        }
}


