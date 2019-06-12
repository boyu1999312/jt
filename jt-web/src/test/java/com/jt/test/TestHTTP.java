package com.jt.test;


import com.jt.util.HttpClientService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;

@SpringBootTest
    @RunWith(SpringRunner.class)
    public class TestHTTP {

    @Autowired
    private CloseableHttpClient client;
    @Autowired
    private HttpClientService clientService;

    @Test
    public void test01() throws IOException {
       /* CloseableHttpClient aDefault = HttpClients.createDefault();*/
        String url = "https://www.baidu.com";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse execute = client.execute(httpPost);
        String html = EntityUtils.toString(execute.getEntity());
        System.out.println(html);
    }

    @Test
    public void test02(){
        String csdnUrl = "https://so.csdn.net/so/search/s.do";
        HashMap<String, String> map = new HashMap<>();
        map.put("q", "Python工程师");
        map.put("t", "%20");
        map.put("u", "");
        String html = clientService.doGet(csdnUrl, map);
        System.out.println("html: " + html);
    }

}
