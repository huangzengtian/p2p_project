package com.bjpowernode.p2p.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.mapper.RechargeRecordMapper;
import com.bjpowernode.p2p.model.RechargeRecord;
import com.bjpowernode.p2p.service.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Service(interfaceClass = RechargeRecordService.class, version = "1.0.0", timeout = 15000)
public class RechargeRecordServiceImpl implements RechargeRecordService {
    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public Integer queryTotalRowsByUid(Integer uid) {
        return rechargeRecordMapper.selectTotalRowsByUid(uid);
    }

    @Override
    public List<RechargeRecord> queryInfoByUid(Map<String, Object> paramMap) {
        return rechargeRecordMapper.selectInfoByUid(paramMap);
    }
}
