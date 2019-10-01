/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AliyunSmsConstants.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.canto.constant;

import com.canto.enums.ErrorCodeEnum;
import com.canto.exception.BusinessException;
import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * The class Aliyun sms constants.
 *
 * @author paascloud.net@gmail.com
 */
public class AliyunSmsConstants {

    /**
     * 短信模板枚举
     */
    public enum SmsTemplateEnum {

        /**
         * 通用模板(短信内容:您的验证码是${code}，该验证码5分钟内有效，请勿泄漏于他人！)
         */
        CANTO_GLOBAL_TEMPLATE("CANTO_GLOBAL_TEMPLATE", "SMS_174585414", "code"),
        ;

        private String busType;

        private String templateCode;

        private String smsParamName;

        public static SmsTemplateEnum getEnum(String templateCode) {
            SmsTemplateEnum smsTemplateEnum = null;
            for (SmsTemplateEnum ele : SmsTemplateEnum.values()) {
                if (templateCode.equals(ele.getTemplateCode())) {
                    smsTemplateEnum = ele;
                    break;
                }
            }
            return smsTemplateEnum;
        }

        public static boolean isSmsTemplate(String smsTemplateCode) {

            if (StringUtils.isEmpty(smsTemplateCode)) {
                throw new BusinessException(ErrorCodeEnum.UC10010001);
            }
            List<String> templateCodeList = getTemplateCodeList();

            return templateCodeList.contains(smsTemplateCode);
        }

        public static List<SmsTemplateEnum> getList() {
            return Arrays.asList(SmsTemplateEnum.values());
        }

        public static List<String> getTemplateCodeList() {
            List<String> templateCodeList = Lists.newArrayList();
            List<SmsTemplateEnum> list = getList();
            for (SmsTemplateEnum templateEnum : list) {
                if (StringUtils.isEmpty(templateEnum.getTemplateCode())) {
                    continue;
                }
                templateCodeList.add(templateEnum.getTemplateCode());
            }
            return templateCodeList;
        }

        SmsTemplateEnum(String busType, String templateCode, String smsParamName) {
            this.busType = busType;
            this.templateCode = templateCode;
            this.smsParamName = smsParamName;
        }

        public String getBusType() {
            return busType;
        }

        public String getTemplateCode() {
            return templateCode;
        }

        public String getSmsParamName() {
            return smsParamName;
        }
    }


}
