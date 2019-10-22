package com.canto.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p></p>
 *
 * @author iqunqunqun
 * @className SmsVO
 * @since 2019/10/6 13:30
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class SmsVO {
    /**
     * 电话
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;
}
