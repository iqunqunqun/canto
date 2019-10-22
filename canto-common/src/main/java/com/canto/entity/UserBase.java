package com.canto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户基础信息表
 * </p>
 *
 * @author ivan
 * @since 2019-09-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserBase implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "uid", type = IdType.AUTO)
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


}
