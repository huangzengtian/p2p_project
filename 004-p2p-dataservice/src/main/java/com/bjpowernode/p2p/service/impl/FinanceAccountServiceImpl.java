package com.bjpowernode.p2p.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.FinanceAccountMapper;
import com.bjpowernode.p2p.model.FinanceAccount;
import com.bjpowernode.p2p.service.FinanceAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = FinanceAccountService.class, version = "1.0.0", timeout = 15000)
public class FinanceAccountServiceImpl implements FinanceAccountService {
    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Override
    public FinanceAccount queryByUid(Integer uid) {
        return financeAccountMapper.selectByUid(uid);
    }
}
