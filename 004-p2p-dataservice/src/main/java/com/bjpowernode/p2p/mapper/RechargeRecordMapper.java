package com.bjpowernode.p2p.mapper;

import com.bjpowernode.p2p.model.RechargeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RechargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    /**
     * 查询当前用户投资记录条数
     * @param uid
     * @return
     */
    Integer selectTotalRowsByUid(@Param("uid") Integer uid);

    /**
     * 查询当前用户充值记录
     * @param paramMap
     * @return
     */
    List<RechargeRecord> selectInfoByUid(Map<String, Object> paramMap);
}