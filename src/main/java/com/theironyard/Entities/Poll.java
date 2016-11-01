package com.theironyard.Entities;

import javax.persistence.*;

@Entity
@Table(name = "polls")
public class Poll {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String pollName;

    @Column(nullable = false)
    private String pollTopic;

    @Column(nullable = false)
    private String responseA;

    @Column(nullable = false)
    private String responseB;

    @Column(nullable = true)
    private String responseC;

    @Column(nullable = true)
    private String responseD;

    @Column(nullable = true)
    private String responseE;

    @Column(nullable = true)
    private String responseF;

    @ManyToOne
    private User user;

    public Poll() {
    }

    public Poll(String pollName, String pollTopic, String responseA, String responseB, String responseC, String responseD, String responseE, String responseF, User user) {
        this.pollName = pollName;
        this.pollTopic = pollTopic;
        this.responseA = responseA;
        this.responseB = responseB;
        this.responseC = responseC;
        this.responseD = responseD;
        this.responseE = responseE;
        this.responseF = responseF;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public String getPollTopic() {
        return pollTopic;
    }

    public void setPollTopic(String pollTopic) {
        this.pollTopic = pollTopic;
    }

    public String getResponseA() {
        return responseA;
    }

    public void setResponseA(String responseA) {
        this.responseA = responseA;
    }

    public String getResponseB() {
        return responseB;
    }

    public void setResponseB(String responseB) {
        this.responseB = responseB;
    }

    public String getResponseC() {
        return responseC;
    }

    public void setResponseC(String responseC) {
        this.responseC = responseC;
    }

    public String getResponseD() {
        return responseD;
    }

    public void setResponseD(String responseD) {
        this.responseD = responseD;
    }

    public String getResponseE() {
        return responseE;
    }

    public void setResponseE(String responseE) {
        this.responseE = responseE;
    }

    public String getResponseF() {
        return responseF;
    }

    public void setResponseF(String responseF) {
        this.responseF = responseF;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
