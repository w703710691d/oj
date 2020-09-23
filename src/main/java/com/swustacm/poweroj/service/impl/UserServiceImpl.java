package com.swustacm.poweroj.service.impl;

import com.swustacm.poweroj.entity.User;
import com.swustacm.poweroj.mapper.UserMapper;
import com.swustacm.poweroj.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xingzi
 * @since 2020-09-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
