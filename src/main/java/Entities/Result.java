package Entities;

import javax.persistence.*;

@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer pollId;

    @Column(nullable = false)
    private String answer;

    public Result() {
    }

    public Result(String topic, Integer userId, int pullId, String answer) {
        this.topic = topic;
        this.userId = userId;
        this.pollId = pullId;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getPullId() {
        return pollId;
    }

    public void setPullId(int pullId) {
        this.pollId = pullId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
