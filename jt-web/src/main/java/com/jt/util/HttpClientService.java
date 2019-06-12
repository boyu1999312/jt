package com.jt.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

@Service
public class HttpClientService {

    @Autowired
    private CloseableHttpClient client;

    @Autowired
    private RequestConfig requestConfig;

    /**
     * 封装get方法
     * @param url 请求网址
     * @param params 请求参数
     * @param charset 编码格式
     * @return 返回JSON串
     *
     * 无参数: manage.jt.com/xxx
     * 有参数: manage.jt.com/xxx?id=xxx&pid=xxx...
     */
    public String doGet(String url, Map<String, String> params, String charset){
        //1.检验字符集编码格式
        if(StringUtils.isEmpty(charset))
            //表明用户没有指定字符类型，使用默认值
            charset = "utf-8";
        //2.校验map集合是否为空
        if(params != null){
            StringBuilder builder = new StringBuilder("?");
            for (String s : params.keySet()) {
                builder.append(s).append("=").append(params.get(s)).append("&");
            }
            url += builder.deleteCharAt(builder.length()-1).toString();
        }

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String result = null;
        try {
            CloseableHttpResponse execute = client.execute(httpGet);
            //判定状态码
            if(execute.getStatusLine().getStatusCode() == 200){
                result = EntityUtils.toString(execute.getEntity(),charset);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    public String doGet(String url){
        return doGet(url, null , null);
    }
    public String doGet(String url, Map<String, String> params){
        return doGet(url, params , null);
    }


}
