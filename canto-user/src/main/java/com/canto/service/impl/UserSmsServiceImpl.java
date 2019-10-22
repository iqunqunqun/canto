package com.canto.service.impl;

import com.alibaba.alicloud.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.canto.constant.GlobalConstant;
import com.canto.result.JsonResult;
import com.canto.service.IUserSmsService;
import com.canto.utils.RandomUtil;
import com.canto.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author iqunqunqun
 * @className ISmsServiceImpl
 * @since 2019/9/21 19:58
 */

@Service
public class UserSmsServiceImpl implements IUserSmsService {

    @Autowired
    private ISmsService smsService;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${canto.sign-name}")
    private String signName;

    @Value("${canto.template-code}")
    private String templateCode;

    @Override
    public JsonResult sendSms(String phone) {
        SendSmsRequest smsRequest = new SendSmsRequest();
        smsRequest.setPhoneNumbers(phone);
        smsRequest.setSignName(signName);
        smsRequest.setTemplateCode(templateCode);
        String code = RandomUtil.createNumberCode(GlobalConstant.Number.FOUR_INT);
        // 将验证码存入redis中，以便后面验证
        redisUtil.set(phone, code, GlobalConstant.VERIFICATION_CODE_EXPIRE_DATE);
        smsRequest.setTemplateParam("{\"code\":\"" + code + "\"}");

        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = smsService.sendSmsRequest(smsRequest);
        } catch (ClientException e) {
            e.printStackTrace();
            sendSmsResponse = new SendSmsResponse();
        }
        return JsonResult.ok(sendSmsResponse);
    }
}
