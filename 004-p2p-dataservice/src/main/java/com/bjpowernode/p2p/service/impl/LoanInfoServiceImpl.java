package com.bjpowernode.p2p.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.MyConst;
import com.bjpowernode.p2p.mapper.BidInfoMapper;
import com.bjpowernode.p2p.mapper.FinanceAccountMapper;
import com.bjpowernode.p2p.mapper.LoanInfoMapper;
import com.bjpowernode.p2p.model.BidInfo;
import com.bjpowernode.p2p.model.LoanInfo;
import com.bjpowernode.p2p.service.LoanInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = LoanInfoService.class, version = "1.0.0", timeout = 15000)
public class LoanInfoServiceImpl implements LoanInfoService {
    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Double queryAvgRate() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Double avgRate = (Double) redisTemplate.opsForValue().get(MyConst.AVG_RATE);
        if (!ObjectUtils.allNotNull(avgRate)) {
            synchronized (this) {
                avgRate = (Double) redisTemplate.opsForValue().get(MyConst.AVG_RATE);
                if (!ObjectUtils.allNotNull(avgRate)) {
                    avgRate = loanInfoMapper.selectAvgRate();
                    redisTemplate.opsForValue().set(MyConst.AVG_RATE, avgRate, 1, TimeUnit.DAYS);
                }
            }
        }
        return avgRate;
    }

    @Override
    public List<LoanInfo> queryByProductType(Map<String, Object> paramMap) {
        return loanInfoMapper.selectByProductType(paramMap);
    }

    @Override
    public LoanInfo queryById(Integer id) {
        return loanInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer queryTotalRowsByProductType(Integer productType) {
        return loanInfoMapper.selectTotalRowsByProductType(productType);
    }

    @Override
    @Transactional
    public void bid(Double bidMoney, Integer loanId, Integer uid, Integer productStatus) {
        // 投资记录表添加投资记录
        BidInfo bidInfo = new BidInfo();
        bidInfo.setLoanId(loanId);
        bidInfo.setUid(uid);
        bidInfo.setBidMoney(bidMoney);
        bidInfo.setBidTime(new Date());
        bidInfo.setBidStatus(1);
        bidInfoMapper.insert(bidInfo);
        // 账户表扣除投资金额
        financeAccountMapper.updateFinance(uid,bidMoney);
        // 产品信息表扣除剩余投资金额，并判断是否满标
        loanInfoMapper.updateLoanInfo(loanId, bidMoney, productStatus);

    }


}
