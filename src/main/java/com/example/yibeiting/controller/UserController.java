package com.example.yibeiting.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.yibeiting.service.AuthService;
import com.example.yibeiting.service.WorkService;
import com.example.yibeiting.util.Base64Util;
import com.example.yibeiting.util.FileUtil;
import com.example.yibeiting.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Controller
@Slf4j
public class UserController {

    @Autowired
    AuthService authService;

    @Autowired
    WorkService workService;

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

}
