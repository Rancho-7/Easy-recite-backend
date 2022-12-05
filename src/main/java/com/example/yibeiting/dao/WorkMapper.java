package com.example.yibeiting.dao;


import com.example.yibeiting.entity.Work;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkMapper {

    //查询出某个用户的所有作业
    List<Work> selectByTicket(String ticket);

    //根据作业id查询作业详情
    Work selectById(int workId);

    //插入作业
    int insertWork(Work work);

    //修改作业状态
    int updateWorkStatus(@Param("workId") int workId, @Param("status") int status);

    //修改作业
    int updateWork(Work work,int workId);

    //根据作业id获取答案
    String getContent(int workId);

    //根据作业id删除答案
    int deleteWork(int workId);
}
