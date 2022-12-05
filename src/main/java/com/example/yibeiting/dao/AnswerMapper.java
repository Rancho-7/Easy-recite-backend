package com.example.yibeiting.dao;

import com.example.yibeiting.entity.Answer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnswerMapper {
    //上传学生答案
    int updateStudentAnswer(String studentAnswer,int workId);

    //插入答案
    int insertAnswer(Answer answer);

    //获取录音链接
    String selectRecords(int workId);

    //根据workId获取score
    int getScoreByworkId(int workId);

    //根据workIk查询答案
    List<Answer> getAnswerByworkId(int workId);

    //根据answerId删除答案
    int deleteAnswer(int answerId);
}
