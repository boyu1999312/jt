package com.jt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {

    //查看服务器端口
    @RequestMapping("getMsg")
    public String getMsg(){
        String str = "this is 8093";
        System.out.println(str);
        return str;
    }
}
