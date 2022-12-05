package com.example.yibeiting.service;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import com.example.yibeiting.dao.WorkMapper;
import com.example.yibeiting.entity.Work;
import com.example.yibeiting.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WorkService {

    private static final String APP_ID = "****";
    private static final String API_KEY = "****";
    private static final String SECRET_KEY = "****";
    public static final String URL = "http://106.15.67.178:8080/";
    private volatile static AipSpeech client;

    @Autowired
    WorkMapper workMapper;

    //将作业文字转为语音
    public String transfer2Speech(String content) throws JSONException {
        String uid = CommonUtil.generateUUID();
        AipSpeech client = getInstance();
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
//        HashMap<String, Object> options = new HashMap<String, Object>();
//        options.put("spd", "0");
//        options.put("pit", "5");
//        options.put("per", "4");
        TtsResponse res = client.synthesis(content,"zh", 1, null);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        String filename;
        StringBuilder str = new StringBuilder("");
        str.append(URL);
        if (data != null) {
            try {
                filename = uid + ".mp3";
                str.append("speech/").append(filename);
                File file = new File(filename);
                File file2 = new File("/usr/project/upload/speech/" + filename);
                Util.writeBytesToFileSystem(data, filename);
                file.renameTo(file2);
                String abslote = file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }
        log.info("转换后的url:{}",str);
        return str.toString();
    }

    /**单例 懒加载模式 */
    public static AipSpeech getInstance(){
        if (client==null){
            synchronized (AipSpeech.class){
                if (client==null) {
                    client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
                }
            }
        }
        return client;
    }

    //获取作业列表
    public List getHomeWorkList(String ticket){
        List<Work> list = new ArrayList<>();
        list=workMapper.selectByTicket(ticket);
        return list;
    }

    //获取作业答案
    public String getContent(int workId){
        String content = workMapper.getContent(workId);
        return content;
    }

    //插入作业
    public int insertWork(Work work){
        workMapper.insertWork(work);
        return 0;
    }

    //根据id获取作业详情
    public Work getDetail(int workId){
        return workMapper.selectById(workId);
    }

    //修改作业状态
    public int updateWorkStatus(int workId,int status){
        workMapper.updateWorkStatus(workId,status);
        return 0;
    }

    //修改作业
    public int updateWork(Work work,int workId){
        workMapper.updateWork(work,workId);
        return 0;
    }

    //根据作业id删除作业
    public int deleteWork(int workId){
        workMapper.deleteWork(workId);
        return 0;
    }
}
