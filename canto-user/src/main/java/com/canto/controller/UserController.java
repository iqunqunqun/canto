package com.canto.controller;


import com.canto.entity.vo.SmsVO;
import com.canto.entity.vo.UserBaseVO;
import com.canto.result.JsonResult;
import com.canto.service.IUserBaseService;
import com.canto.service.IUserSmsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * 用户基础信息表 前端控制器
 * </p>
 *
 * @author ivan
 * @since 2019-09-13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserBaseService userBaseService;

    @Autowired
    private IUserSmsService userSmsService;


    @ApiOperation("注册")
    @PostMapping("/register")
    public JsonResult register(@RequestBody UserBaseVO userBaseVO) {
        return this.userBaseService.register(userBaseVO);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public JsonResult login(@RequestBody SmsVO smsVO) {
        return this.userBaseService.login(smsVO);
    }


    @ApiOperation("发送短信")
    @GetMapping("/sendSms")
    public JsonResult sendSms(@RequestParam String phone) {
        return this.userSmsService.sendSms(phone);
    }

    @ApiOperation("获取用户详情")
    @GetMapping("/getUserDetail")
    public JsonResult getUserDetail(@RequestParam Long uid) {
        return this.userBaseService.getUserDetail(uid);
    }

    @PutMapping("/updateUserDetail")
    public JsonResult updateUserDetail(@RequestBody UserBaseVO userBaseVO) {
        return this.userBaseService.updateUserDetail(userBaseVO);
    }

    @GetMapping("/success")
    public JsonResult success() {
        return JsonResult.ok("登陆成功");
    }
}

