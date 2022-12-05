package com.example.yibeiting.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.yibeiting.entity.Answer;
import com.example.yibeiting.entity.Work;
import com.example.yibeiting.service.AnswerService;
import com.example.yibeiting.service.AuthService;
import com.example.yibeiting.service.WorkService;
import com.example.yibeiting.util.Base64Util;
import com.example.yibeiting.util.DateUtil;
import com.example.yibeiting.util.FileUtil;
import com.example.yibeiting.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@Slf4j
public class UserController {

    @Autowired
    AuthService authService;

    @Autowired
    WorkService workService;

    @Autowired
    AnswerService answerService;

    /**
     * 通用文字识别（普通精度版）
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    @RequestMapping(path = "/translate",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject accurateBasic(MultipartFile file) {
        // 请求url
        String filePath="";
        String result="";
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
//        filePath=FileUtils.saveMultipartFile(file,"D:\\ChuanZhiWeb\\upload\\translate");
        filePath= FileUtil.saveMultipartFile(file,"/usr/project/upload/translate");
        try {
            // 本地文件路径
            String filePath0 = filePath;
//            byte[] imgData = com.baidu.ai.aip.utils.FileUtil.readFileByBytes(filePath);
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = authService.getAuth();

            result = HttpUtil.post(url, accessToken, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(result);
    }

    //手写文字识别
    @RequestMapping(path = "handwriting",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject handwriting(MultipartFile file) {
        String filePath = "";
        String result = "";
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/handwriting";
        // 本地文件路径
        filePath = filePath=FileUtil.saveMultipartFile(file,"/usr/project/upload/handwrite");
        //filePath=FileUtils.saveMultipartFile(file,"D:\\ChuanZhiWeb\\upload\\translate");
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = authService.getAuth();

            result = HttpUtil.post(url, accessToken, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(result);
    }

    //将文字转为语音url
    @RequestMapping(path = "/trans2speech",method = RequestMethod.GET)
    @ResponseBody
    public List transfer2Speech(String content) throws JSONException {
        List<String> list = new ArrayList<>();
        String[] arr = content.split(" ");
        for(String s:arr){
            log.info("内容为：{}",s);
            String tmp = workService.transfer2Speech(s);
            list.add(tmp);
        }
        return list;
    }

    //创建作业
    @RequestMapping(path = "/createWork",method = RequestMethod.POST)
    @ResponseBody
    public List createWork(@RequestBody Work work) throws JSONException, ParseException {
        List<String> list = new ArrayList<>();
        String content = work.getContent();
        list = transfer2Speech(content);
        String s = JSON.toJSONString(list);
        work.setRecords(s);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String s0 = sdf.format(date);
//        Date date2 = sdf.parse(s0);
        Date resDate = DateUtil.strToDateyyyMMdd(s0);
        work.setCreateTime(resDate);
        workService.insertWork(work);
        return list;
    }

    //获取作业列表
    @RequestMapping(path = "/getWorklist",method = RequestMethod.GET)
    @ResponseBody
    public List getHomeWorkList(String ticket) throws ParseException {
        System.out.println("ticket:"+ticket);
        List<Work> list = new ArrayList<>();
        list=workService.getHomeWorkList(ticket);
        for (Work work:list){
            Date createTime = work.getCreateTime();
            if(createTime == null){
                createTime = new Date();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s0 = sdf.format(createTime);
            Date resDate = DateUtil.strToDateyyyMMdd(s0);
            work.setCreateTime(resDate);
//            System.out.println(work);
            log.info("获取作业列表中的作业：{}",work);
        }
        return list;
    }

    //获取作业详情
    @RequestMapping(path = "/getWorkDetail",method = RequestMethod.GET)
    @ResponseBody
    public Work getWorkDetail(int workId) throws ParseException {
        Work work = workService.getDetail(workId);
        Date createTime = work.getCreateTime();
        if(createTime == null){
            createTime = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s0 = sdf.format(createTime);
        Date resDate = DateUtil.strToDateyyyMMdd(s0);
        work.setCreateTime(resDate);
        log.info("already got work:{}",work);
        return work;
    }

    //更新作业
    @RequestMapping(path = "/update",method = RequestMethod.POST)
    @ResponseBody
    public int updateWork(@RequestBody Work work, int workId){
        workService.updateWork(work,workId);
        return 0;
    }

    //创建答案
    @RequestMapping(path = "/createAnswer",method = RequestMethod.POST)
    @ResponseBody
    public int createAnswer(@RequestBody Answer answer) throws JSONException {
        log.info("The got answer is:{}",answer);
        answerService.insertAnswer(answer);
        int workId = answer.getWorkId();
        int score = answer.getScore();
        //作业状态： 0表示未完成  1表示不及格<60  2表示良好60~85  3表示优秀>85
        int status = 0;
        if (score <60){
            status = 1;
        }else if (score >= 60 && score <= 85){
            status = 2;
        }else if (score > 85 && score <= 100){
            status = 3;
        }
        workService.updateWorkStatus(workId,status);
        return 0;
    }

    //获取答案详情
    @RequestMapping(path = "/getAnswerDetail",method = RequestMethod.GET)
    @ResponseBody
    public List getAnswerDetail(int workId){
        List<Answer> list = new ArrayList<>();
        list= answerService.getAnswerByworkId(workId);
        return list;
    }

    //根据答案id删除答案
    @RequestMapping(path = "/deleteAnswer",method = RequestMethod.POST)
    @ResponseBody
    public int deleteAnswer(int answerId){
        return answerService.deleteAnswer(answerId);
    }

    //根据作业id删除作业
    @RequestMapping(path = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public int deleteWork(Integer workId){
        log.info("删除作业id为{}的作业",workId);
        return  workService.deleteWork(workId);
    }

}
