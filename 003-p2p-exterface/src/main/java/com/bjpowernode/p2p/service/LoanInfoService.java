package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.LoanInfo;

import java.util.List;
import java.util.Map;

public interface LoanInfoService {
    /**
     * 查询平均历史年化收益率
     * @return
     */
    Double queryAvgRate();

    /**
     * 根据产品类型查询产品信息
     * @param paramMap
     * @return
     */
    List<LoanInfo> queryByProductType(Map<String, Object> paramMap);

    /**
     * 根据产品id查询产品信息
     * @param id
     * @return
     */
    LoanInfo queryById(Integer id);

    /**
     * 根据产品类型查询产品数量
     * @param productType
     * @return
     */
    Integer queryTotalRowsByProductType(Integer productType);

    /**
     * 投资记录表添加投资记录
     * 账户表扣除投资金额
     * 产品信息表扣除剩余投资金额，并判断是否满标
     * @param bidMoney
     * @param loanId
     * @param uid
     */
    void bid(Double bidMoney, Integer loanId, Integer uid, Integer productStatus);
}
