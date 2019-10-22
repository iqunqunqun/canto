package com.canto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.canto.entity.UserBase;
import com.canto.entity.vo.SmsVO;
import com.canto.entity.vo.UserBaseVO;
import com.canto.result.JsonResult;

/**
 * <p>
 * 用户基础信息表 服务类
 * </p>
 *
 * @author ivan
 * @since 2019-09-13
 */
public interface IUserBaseService extends IService<UserBase> {

    /**
     * <p>用户注册</p>
     *
     * @param userBaseVO 用户VO对象
     * @return com.canto.result.JsonResult
     * @author chenyf
     * @date 2019/9/21 19:46
     */
    JsonResult register(UserBaseVO userBaseVO);

    /**
     * <p>获取用户详情</p>
     *
     * @param uid ：用户ID
     * @return com.canto.result.JsonResult
     * @author chenyf
     * @date 2019/10/6 20:09
     */
    JsonResult getUserDetail(Long uid);

    /**
     * <p>登录</p>
     *
     * @param smsVO：验证对象
     * @return com.canto.result.JsonResult
     * @author chenyf
     * @date 2019/10/6 20:29
     */
    JsonResult login(SmsVO smsVO);

    /**
     * <p>更新用户基本信息</p>
     *
     * @param userBaseVO：用户传输对象
     * @return com.canto.result.JsonResult
     * @author chenyf
     * @date 2019/10/7 12:39
     */
    JsonResult updateUserDetail(UserBaseVO userBaseVO);
}
