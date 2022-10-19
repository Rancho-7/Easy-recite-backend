package com.example.yibeiting.Utils;

import org.json.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CommonUtils {

    public static JSONObject stringtoJSONObject(String str){
        JSONObject jsonObject=JSONObject.parseObject(str);
        return  jsonObject;
    }

    public static Map<String, Object> parseJSON2Map(JSONObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 最外层解析
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            // 如果内层还是数组的话，继续解析
//            if (v instanceof JSONArray) {
//                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//                @SuppressWarnings("unchecked")
//                Iterator<JSONObject> it = ((JSONArray) v).iterator();
//                while (it.hasNext()) {
//                    JSONObject json2 = it.next();
//                    list.add(parseJSON2Map(json2));
//                }
//                map.put(k.toString(), list);
//            } else {
//                map.put(k.toString(), v);
//            }
            map.put(k.toString(), v);
        }
        return map;
    }
}
