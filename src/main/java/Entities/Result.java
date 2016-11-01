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
    private String userId;

    @Column(nullable = false)
    private int pullId;

    @Column(nullable = false)
    private String answer;

    public Result() {
    }

    public Result(String topic, String userId, int pullId, String answer) {
        this.topic = topic;
        this.userId = userId;
        this.pullId = pullId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPullId() {
        return pullId;
    }

    public void setPullId(int pullId) {
        this.pullId = pullId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
