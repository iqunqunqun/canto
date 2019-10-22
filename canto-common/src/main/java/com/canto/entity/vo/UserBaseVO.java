package com.canto.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * @author iqunqunqun
 * @className UserBaseVO
 * @since 2019/9/18 23:00
 */

@Data
@EqualsAndHashCode
public class UserBaseVO {
    /**
     * 用户ID
     */
    @TableId("uid")
    private Long uid;

    /**
     * 2正常用户 3禁言用户 4虚拟用户 5运营
     */
    @TableField("user_role")
    private Integer userRole;

    /**
     * 注册来源：1-手机号 2-邮箱 3-QQ 4-微信
     */
    @TableField("register_source")
    @NotNull(message = "注册来源不能为空")
    private Integer registerSource;

    /**
     * 用户账号，必须唯一
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 用户性别 0-未知 1-female 2-male
     */
    @TableField("gender")
    private Boolean gender;

    /**
     * 用户生日
     */
    @TableField("birthday")
    private LocalDateTime birthday;

    /**
     * 手机号码(唯一)
     */
    @TableField("mobile")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^((\\+?86)|(\\(\\+86\\)))?(13[0-9][0-9]{8}|14[0-9]{9}|15[0-9][0-9]{8}|17[0-9][0-9]{8}|18[0-9" +
            "][0-9]{8})$", message = "手机号不合法")
    private String mobile;

    /**
     * 手机号码绑定时间
     */
    @TableField("mobile_bind_time")
    private LocalDateTime mobileBindTime;

    /**
     * 邮箱(唯一)
     */
    @TableField("email")
    @Email
    private String email;

    /**
     * 邮箱绑定时间
     */
    @TableField("email_bind_time")
    private LocalDateTime emailBindTime;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


    /**
     * 验证码
     */
    private String code;
}