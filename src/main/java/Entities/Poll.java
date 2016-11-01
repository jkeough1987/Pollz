package Entities;

import javax.persistence.*;

@Entity
@Table(name = "polls")
public class Poll {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String pollName;

    @Column(nullable = false)
    String pollTopic;

    @Column(nullable = false)
    int responseA;

    @Column(nullable = false)
    int responseB;

    @Column(nullable = true)
    Integer responseC;

    @Column(nullable = true)
    Integer responseD;

    @Column(nullable = true)
    Integer responseE;

    @Column(nullable = true)
    Integer responseF;

    @ManyToOne
    User user;

    public Poll() {
    }

    public Poll(String pollName, String pollTopic, int responseA, int responseB, User user) {
        this.pollName = pollName;
        this.pollTopic = pollTopic;
        this.responseA = responseA;
        this.responseB = responseB;
        this.user = user;
    }

    public Poll(String pollName, String pollTopic, int responseA, int responseB, Integer responseC, User user) {
        this.pollName = pollName;
        this.pollTopic = pollTopic;
        this.responseA = responseA;
        this.responseB = responseB;
        this.responseC = responseC;
        this.user = user;
    }

    public Poll(String pollName, String pollTopic, int responseA, int responseB, Integer responseC, Integer responseD, User user) {
        this.pollName = pollName;
        this.pollTopic = pollTopic;
        this.responseA = responseA;
        this.responseB = responseB;
        this.responseC = responseC;
        this.responseD = responseD;
        this.user = user;
    }

    public Poll(String pollName, String pollTopic, int responseA, int responseB, Integer responseC, Integer responseD, Integer responseE, User user) {
        this.pollName = pollName;
        this.pollTopic = pollTopic;
        this.responseA = responseA;
        this.responseB = responseB;
        this.responseC = responseC;
        this.responseD = responseD;
        this.responseE = responseE;
        this.user = user;
    }

    public Poll(String pollName, String pollTopic, int responseA, int responseB, Integer responseC, Integer responseD, Integer responseE, Integer responseF, User user) {
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

    public int getResponseA() {
        return responseA;
    }

    public void setResponseA(int responseA) {
        this.responseA = responseA;
    }

    public int getResponseB() {
        return responseB;
    }

    public void setResponseB(int responseB) {
        this.responseB = responseB;
    }

    public Integer getResponseC() {
        return responseC;
    }

    public void setResponseC(Integer responseC) {
        this.responseC = responseC;
    }

    public Integer getResponseD() {
        return responseD;
    }

    public void setResponseD(Integer responseD) {
        this.responseD = responseD;
    }

    public Integer getResponseE() {
        return responseE;
    }

    public void setResponseE(Integer responseE) {
        this.responseE = responseE;
    }

    public Integer getResponseF() {
        return responseF;
    }

    public void setResponseF(Integer responseF) {
        this.responseF = responseF;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
