package com.bjpowernode.p2p.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.MyConst;
import com.bjpowernode.p2p.mapper.FinanceAccountMapper;
import com.bjpowernode.p2p.mapper.UserMapper;
import com.bjpowernode.p2p.model.FinanceAccount;
import com.bjpowernode.p2p.model.User;
import com.bjpowernode.p2p.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = UserService.class, version = "1.0.0", timeout = 15000)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Integer queryTotalUsers() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Integer totalUsers = (Integer) redisTemplate.opsForValue().get(MyConst.TOTAL_USERS);
        if (!ObjectUtils.allNotNull(totalUsers)) {
            synchronized (this) {
                totalUsers = (Integer) redisTemplate.opsForValue().get(MyConst.TOTAL_USERS);
                if (!ObjectUtils.allNotNull(totalUsers)) {
                    totalUsers = userMapper.selectTotalUsers();
                    redisTemplate.opsForValue().set(MyConst.TOTAL_USERS, totalUsers, 1, TimeUnit.DAYS);
                }
            }
        }
        return totalUsers;
    }

    @Override
    public User queryByPhoneAndLoginPassword(String phone, String loginPassword) {
        return userMapper.selectByPhoneAndLoginPassword(phone, loginPassword);
    }

    @Override
    public void edit(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User queryExistByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    @Override
    public void add(User user) {
        userMapper.insertSelective(user);
    }

    @Override
    @Transactional
    public User register(String phone, String loginPassword) {
        //保存用户注册信息
        User user = new User();
        user.setPhone(phone);
        user.setLoginPassword(loginPassword);
        user.setAddTime(new Date());
        userMapper.insertSelective(user);

        //向用户账户添加888元
        FinanceAccount financeAccount = new FinanceAccount();
        financeAccount.setAvailableMoney(888.0);
        //通过mybatis查询自增主键方法，在向用户表插入信息的同时，给user的id赋值
        System.out.println("自增id："+user.getId());
        financeAccount.setUid(user.getId());
        financeAccountMapper.insert(financeAccount);
        return null;
    }
}
