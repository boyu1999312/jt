package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MsgController {

    @RequestMapping("getMsg")
    public String getMsg(){
        String str = "this is 8092";
        System.out.println(str);
        return str;
    }
}
