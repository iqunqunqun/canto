package com.canto.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.canto.entity.UserAuth;
import com.canto.entity.UserBase;
import com.canto.entity.vo.SmsVO;
import com.canto.entity.vo.UserBaseVO;
import com.canto.mapper.UserBaseMapper;
import com.canto.result.JsonResult;
import com.canto.service.IUserAuthService;
import com.canto.service.IUserBaseService;
import com.canto.utils.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户基础信息表 服务实现类
 * </p>
 *
 * @author ivan
 * @since 2019-09-13
 */
@Service
public class UserBaseServiceImpl extends ServiceImpl<UserBaseMapper, UserBase> implements IUserBaseService {

    @Autowired
    private IUserAuthService userAuthService;

    @Autowired
    private RedisUtil redisUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult register(UserBaseVO userBaseVO) {
        // 验证码是否正确
        String code = userBaseVO.getCode();
        String string = redisUtil.get(userBaseVO.getMobile()).toString();
        if (!code.equals(string)) {
            return JsonResult.errorException("验证码错误");
        }

        // 设置基础信息内容
        UserBase userBase = new UserBase();
        BeanUtils.copyProperties(userBaseVO, userBase);
        userBase.setCreateTime(LocalDateTime.now());
        userBase.setMobileBindTime(LocalDateTime.now());
        boolean save = this.save(userBase);
        // 设置用户账号权限
        UserAuth userAuth = new UserAuth();
        userAuth.setUid(userBase.getUid());
        userAuth.setIdentityType(1);
        userAuth.setIdentifier(userBase.getMobile());
        userAuth.setCreateTime(LocalDateTime.now());
        boolean b = userAuthService.save(userAuth);
        return save && b ? JsonResult.ok() : JsonResult.errorException("错误");
    }

    @Override
    public JsonResult getUserDetail(Long uid) {
        UserBase userBase = this.getById(uid);
        if (userBase == null) {
            return JsonResult.errorMsg("不存在此用户");
        }
        return JsonResult.ok(userBase);
    }

    @Override
    public JsonResult login(SmsVO smsVO) {
        // 验证验证码是否过期
        boolean b = redisUtil.hasKey(smsVO.getPhone());
        if (!b) {
            return JsonResult.errorMsg("验证码过期");
        }
        String code = (String) redisUtil.get(smsVO.getPhone());
        // 验证参数是否有效
        if (smsVO.getCode() == null) {
            return JsonResult.errorMsg("请输入验证码");
        }
        // 验证码是否正确
        if (!smsVO.getCode().equals(code)) {
            return JsonResult.errorMsg("验证码有误");
        }
        return JsonResult.ok();
    }

    @Override
    public JsonResult updateUserDetail(UserBaseVO userBaseVO) {
        UserBase userBase = new UserBase();
        BeanUtils.copyProperties(userBaseVO, userBase);
        boolean b = this.updateById(userBase);
        return b ? JsonResult.ok() : JsonResult.errorMsg("更新错误");
    }
}
