package com.bjpowernode.p2p.mapper;

import com.bjpowernode.p2p.model.BidInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BidInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);

    /**
     * 获取平台累计成交额
     * @return
     */
    Double selectTotalBidMoney();

    /**
     * 投资记录列表分页查询(投资信息表和用户表连表查询)
     * @param paramMap
     * @return
     */
    List<BidInfo> selectByUidAndLoanId(Map<String, Object> paramMap);

    /**
     * 根据产品id查询当前产品的总投资记录条数
     * @param loanId
     * @return
     */
    Integer selectTotalByLoanId(Integer loanId);

    /**
     * 根据用户id分页查询投资详情
     * @param paramMap
     * @return
     */
    List<BidInfo> selectBidInfoByUid(Map<String, Object> paramMap);

    /**
     * 根据用户id查询投资记录条数
     * @param uid
     * @return
     */
    Integer selectTotalRowsByUid(@Param("uid") Integer uid);
}