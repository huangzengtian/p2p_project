package com.bjpowernode.p2p.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bjpowernode.p2p.MyConst;
import com.bjpowernode.p2p.mapper.BidInfoMapper;
import com.bjpowernode.p2p.model.BidInfo;
import com.bjpowernode.p2p.service.BidInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = BidInfoService.class, version = "1.0.0", timeout = 15000)
public class BidInfoServiceImpl implements BidInfoService{
    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Double queryTotalBidMoney() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Double totalBidMoney = (Double) redisTemplate.opsForValue().get(MyConst.TOTAL_BID_MONEY);
        if (!ObjectUtils.allNotNull(totalBidMoney)) {
            synchronized (this) {
                totalBidMoney = (Double) redisTemplate.opsForValue().get(MyConst.TOTAL_BID_MONEY);
                if (!ObjectUtils.allNotNull(totalBidMoney)) {
                    totalBidMoney = bidInfoMapper.selectTotalBidMoney();
                    redisTemplate.opsForValue().set(MyConst.TOTAL_BID_MONEY, totalBidMoney, 1, TimeUnit.DAYS);
                }
            }
        }
        return totalBidMoney;
    }

    @Override
    public List<BidInfo> queryByUidANdLoanId(Map<String, Object> paramMap) {
        return bidInfoMapper.selectByUidAndLoanId(paramMap);
    }

    @Override
    public Integer queryTotalByLoanId(Integer loanId) {
        return bidInfoMapper.selectTotalByLoanId(loanId);
    }

    @Override
    public List<BidInfo> queryBidInfoByUid(Map<String, Object> paramMap) {
        return bidInfoMapper.selectBidInfoByUid(paramMap);
    }

    @Override
    public Integer queryTotalRowsByUid(Integer uid) {
        return bidInfoMapper.selectTotalRowsByUid(uid);
    }
}
