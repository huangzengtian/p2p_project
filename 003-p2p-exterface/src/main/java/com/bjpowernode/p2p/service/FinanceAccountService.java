package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.FinanceAccount;

public interface FinanceAccountService {
    /**
     * 获取用户余额
     * @param uid
     * @return
     */
    FinanceAccount queryByUid(Integer uid);
}
