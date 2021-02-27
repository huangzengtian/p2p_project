package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.RechargeRecord;

import java.util.List;
import java.util.Map;

public interface RechargeRecordService {
    /**
     * 查询当前用户充值记录条数
     * @param uid
     * @return
     */
    Integer queryTotalRowsByUid(Integer uid);

    /**
     * 分页查询当前用户投资记录
     * @param paramMap
     * @return
     */
    List<RechargeRecord> queryInfoByUid(Map<String, Object> paramMap);
}
