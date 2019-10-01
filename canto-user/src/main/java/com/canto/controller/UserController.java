package com.canto.controller;


import com.canto.entity.vo.UserBaseVO;
import com.canto.result.JsonResult;
import com.canto.service.IUserBaseService;
import com.canto.service.IUserSmsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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


    @ApiOperation("发送短信")
    @PostMapping("/sendSms")
    public JsonResult sendSms(@RequestParam("phone") String phone) {
        return this.userSmsService.sendSms(phone);
    }


    @ApiOperation("测试")
    @GetMapping("/login")
    public JsonResult login() {
        return JsonResult.ok("登陆页面");
    }

    @GetMapping("/success")
    public JsonResult success() {
        return JsonResult.ok("登陆成功");
    }

    @GetMapping("/fail")
    public JsonResult fail() {
        return JsonResult.ok("登陆失败");
    }

}

