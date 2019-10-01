package com.canto.service;

import com.canto.result.JsonResult;

/**
 * <p></p>
 *
 * @author iqunqunqun
 * @className ISmsService
 * @since 2019/9/21 19:58
 */

public interface IUserSmsService {


    /**
     * <p>发送短信</p>
     *
     * @param phone : 电话号码
     * @return com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse
     * @author chenyf
     * @date 2019/9/21 20:25
     */
    JsonResult sendSms(String phone);
}
