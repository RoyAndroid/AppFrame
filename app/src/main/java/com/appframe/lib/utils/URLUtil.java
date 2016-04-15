package com.appframe.lib.utils;

import com.appframe.lib.Log.L;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roy
 * Date: 15/11/29
 */
public class URLUtil {
    /**
     * 在URL上添加或更新参数
     */
    public static String appendParam(String url, String key, String value){

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put(key, value);

        return appendParam(url, params);
    }

    /**
     * 在URL中添加或更新参数集合
     * @param url
     * @param params
     * @return
     */
    public static String appendParam(String url, Map<String, String> params){

        Map<String, String> current = getParams(url);

        //udpate url param.
        if(current!=null){
            for(String key: current.keySet()) {
                if(!params.containsKey(key))
                    params.put(key, current.get(key));
            }
        }

        String[] urlparts = url.split("\\?");

        //normal start here
        StringBuilder result = new StringBuilder(urlparts[0]);
        result.append("?");

        for(String key: params.keySet()) {
            result.append(key).append("=").append(params.get(key)).append("&");
        }

        L.d(result.toString());

        return result.toString();

    }

    /**
     * 获取URL中的值
     * @param url
     * @param key
     * @return
     */
    public static String getParamValue(String url, String key){

        Pattern p = Pattern.compile("([\\?|&])" + key + "=([^&?]*)", Pattern.CASE_INSENSITIVE);

        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            return matcher.group(2);
        }

        return null;
    }

    /**
     * 获取URL中的键值对
     * @param url
     * @return
     */
    public static Map<String, String> getParams(String url){

        Pattern p = Pattern.compile("([\\?|&])(.+?)=([^&?]*)", Pattern.CASE_INSENSITIVE);

        Map<String, String> kvs = new LinkedHashMap<String, String>();

        Matcher matcher = p.matcher(url);
        while (matcher.find()) {
            kvs.put(matcher.group(2), matcher.group(3));
        }
        return kvs;
    }
}
