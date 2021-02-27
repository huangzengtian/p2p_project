package com.bjpowernode.p2p.mapper;

import com.bjpowernode.p2p.model.FinanceAccount;
import org.apache.ibatis.annotations.Param;

public interface FinanceAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceAccount record);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int updateByPrimaryKey(FinanceAccount record);

    /**
     * 根据用户id查询余额
     * @param uid
     * @return
     */
    FinanceAccount selectByUid(Integer uid);

    /**
     * 用户账户扣除投资金额
     * @param uid
     * @param bidMoney
     */
    void updateFinance(@Param("uid") Integer uid,
                       @Param("bidMoney") Double bidMoney);
}