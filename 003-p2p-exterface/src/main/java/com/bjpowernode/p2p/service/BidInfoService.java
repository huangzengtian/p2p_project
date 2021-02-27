package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.BidInfo;

import java.util.List;
import java.util.Map;

public interface BidInfoService {
    /**
     * 获取平台累计成交额
     * @return
     */
    Double queryTotalBidMoney();

    /**
     * 投资记录列表分页查询(投资信息表和用户表连表查询)
     * @param paramMap
     * @return
     */
    List<BidInfo> queryByUidANdLoanId(Map<String, Object> paramMap);

    /**
     * 根据id获取该产品的投资记录条数
     * @param loanId
     * @return
     */
    Integer queryTotalByLoanId(Integer loanId);

    /**
     * 根据用户id分页查询投资记录详情
     * @param paramMap
     * @return
     */
    List<BidInfo> queryBidInfoByUid(Map<String, Object> paramMap);

    /**
     * 根据用户id查询总投资记录条数
     * @param uid
     * @return
     */
    Integer queryTotalRowsByUid(Integer uid);
}
