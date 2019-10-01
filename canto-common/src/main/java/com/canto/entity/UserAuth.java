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
 * 用户权限表
 * </p>
 *
 * @author ivan
 * @since 2019-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("uid")
    private Long uid;

    /**
     * 1-手机号 2-邮箱 3-QQ 4-微信'
     */
    @TableField("identity_type")
    private Integer identityType;

    /**
     * 手机号 邮箱 用户名或第三方应用的唯一标识
     */
    @TableField("identifier")
    private String identifier;

    /**
     * 密码凭证(站内的保存密码，站外的不保存或保存token)
     */
    @TableField("certificate")
    private String certificate;

    /**
     * 绑定时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新绑定时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


}
