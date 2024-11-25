package com.asaki0019.website.models;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDateTime;
import java.util.Objects;

public class Homework {
    private static final Log log = LogFactory.getLog(Homework.class);
    private int id;
    private double score;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
    private LocalDateTime updateTime;

    public Homework(int id, double score, String content, LocalDateTime createTime, LocalDateTime endTime, LocalDateTime updateTime) {
        this.id = id;
        this.score = score;
        this.content = content;
        this.createTime = createTime;
        this.endTime = endTime;
        this.updateTime = updateTime;
    }

    public Homework(String content, double score, LocalDateTime createTime, LocalDateTime endTime) {
        this.content = content;
        this.score = score;
        this.createTime = createTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Homework homework)) return false;
        return id == homework.id && Double.compare(score, homework.score) == 0 && Objects.equals(content, homework.content) && Objects.equals(createTime, homework.createTime) && Objects.equals(endTime, homework.endTime) && Objects.equals(updateTime, homework.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, score, content, createTime, endTime, updateTime);
    }

    @Override
    public String toString() {
        return "Homework{" +
                "id=" + id +
                ", score=" + score +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
