package com.bjpowernode.p2p.mapper;

import com.bjpowernode.p2p.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 获取平台总用户数
     * @return
     */
    Integer selectTotalUsers();

    User selectByPhoneAndLoginPassword(@Param("phone") String phone,
                                       @Param("loginPassword") String loginPassword);

    User selectByPhone(@Param("phone") String phone);
}