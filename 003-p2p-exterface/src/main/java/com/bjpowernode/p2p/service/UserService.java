package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.User;

public interface UserService {
    /**
     * 查询平台总用户数
     * @return
     */
    Integer queryTotalUsers();

    /**
     * 根据手机号和密码查找用户
     * @param phone
     * @param loginPassword
     * @return
     */
    User queryByPhoneAndLoginPassword(String phone, String loginPassword);

    /**
     * 修改最后登陆时间
     * @param user
     */
    void edit(User user);

    /**
     * 查询手机号是否被注册
     * @param phone
     * @return
     */
    User queryExistByPhone(String phone);

    /**
     * 保存注册的用户信息
     * @param user
     */
    void add(User user);

    /**
     * 保存用户注册信息，并向用户账户添加888元
     * @param phone
     * @param loginPassword
     * @return
     */
    User register(String phone, String loginPassword);
}
