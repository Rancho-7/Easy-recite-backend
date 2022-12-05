package com.example.yibeiting.entity;


public class Answer {
    //答案主键
    private int answerId;

    //作业id
    private int workId;

    //正确答案
    private String correctAnswer;

    //每个单词的得分情况
    private String scoreStatus;

    //总得分
    private int score;

    //用户提交的答案
    private String commitAnswer;

    //单词数量
    private int wordCount;

    //录音url
    private String records;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getScoreStatus() {
        return scoreStatus;
    }

    public void setScoreStatus(String scoreStatus) {
        this.scoreStatus = scoreStatus;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCommitAnswer() {
        return commitAnswer;
    }

    public void setCommitAnswer(String commitAnswer) {
        this.commitAnswer = commitAnswer;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }


    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", workId=" + workId +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", scoreStatus='" + scoreStatus + '\'' +
                ", score=" + score +
                ", commitAnswer='" + commitAnswer + '\'' +
                ", wordCount=" + wordCount +
                ", records='" + records + '\'' +
                '}';
    }
}
