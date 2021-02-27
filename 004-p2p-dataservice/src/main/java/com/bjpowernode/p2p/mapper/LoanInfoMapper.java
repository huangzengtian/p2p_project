package com.bjpowernode.p2p.mapper;

import com.bjpowernode.p2p.model.LoanInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LoanInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoanInfo record);

    int insertSelective(LoanInfo record);

    LoanInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoanInfo record);

    int updateByPrimaryKey(LoanInfo record);

    /**
     * 获取历史平均年化收益率
     * @return
     */
    Double selectAvgRate();

    /**
     * 根据产品类型查询产品信息
     * @param paramMap
     * @return
     */
    List<LoanInfo> selectByProductType(Map<String, Object> paramMap);

    /**
     * 根据产品类型查询产品数量
     * @param productType
     * @return
     */
    Integer selectTotalRowsByProductType(@Param("productType") Integer productType);

    /**
     * 产品剩余可投金额扣除投资金额，并判断是否满标
     * @param loanId
     * @param bidMoney
     * @param productStatus
     */
    void updateLoanInfo(@Param("loanId") Integer loanId,
                @Param("bidMoney") Double bidMoney,
                @Param("productStatus") Integer productStatus);
}