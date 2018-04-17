package com.coder520.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张晨旭
 * @DATE 2018/4/10
 */
@Controller
@RefreshScope
public class UserController {

    @Value("${name}")
    private String name;

    @RequestMapping("/test")
    public String getName() {
        System.out.println(name);
        return name;
    }

}
