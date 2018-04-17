package com.coder520.user.controller;

import com.coder520.common.constants.Constants;
import com.coder520.common.resp.ApiResult;
import com.coder520.user.entity.User;
import com.coder520.user.entity.UserElement;
import com.coder520.user.service.UserService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping("/login")
    public ApiResult login(@RequestBody @Valid User user, HttpSession session) {
        UserElement ue = userService.login(user);
        if (session.getAttribute(Constants.REQUEST_USER_SESSION) == null) {
            session.setAttribute(Constants.REQUEST_USER_SESSION, ue);
        }
        return new ApiResult<>("登陆成功！", ue);
    }

    @RequestMapping("/register")
    public ApiResult register(@RequestBody @Valid User user) throws Exception {
        userService.registerUser(user);
        return new ApiResult("注册成功！");
    }
}
