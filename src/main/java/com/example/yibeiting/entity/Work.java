package com.example.yibeiting.entity;

import java.util.Date;


public class Work {
    //作业主键
    private int workId;

    //用户身份凭证
    private String ticket;

    //标题
    private String tittle;

    //作业描述
    private String description;

    //作业内容
    private String content;

    //作业状态（未完成，及格，不及格）
    private int status;

    //创建时间
    private Date createTime;

    //录音url
    private String records;

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Work{" +
                "workId=" + workId +
                ", ticket='" + ticket + '\'' +
                ", tittle='" + tittle + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
