package com.example.yibeiting.service;

import com.example.yibeiting.dao.AnswerMapper;
import com.example.yibeiting.entity.Answer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AnswerService {

    @Autowired
    AnswerMapper answerMapper;

    //插入答案
    public int insertAnswer(Answer answer){
        answerMapper.insertAnswer(answer);
        return 0;
    }

    //根据workId获取score
    public int getScoreByworkId(int workId){
       return answerMapper.getScoreByworkId(workId);
    }

    //根据workId获取答案详情
    public List getAnswerByworkId(int workId){
        return answerMapper.getAnswerByworkId(workId);
    }

    //根据answerId删除答案
    public int deleteAnswer(int answerId){
        answerMapper.deleteAnswer(answerId);
        return 0;
    }
}
