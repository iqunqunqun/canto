package com.canto.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.canto.entity.UserAuth;
import com.canto.mapper.UserAuthMapper;
import com.canto.service.IUserAuthService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户权限表 服务实现类
 * </p>
 *
 * @author ivan
 * @since 2019-09-23
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements IUserAuthService {

}
