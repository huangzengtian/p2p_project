package com.bjpowernode.p2p.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.MyConst;
import com.bjpowernode.p2p.model.LoanInfo;
import com.bjpowernode.p2p.service.BidInfoService;
import com.bjpowernode.p2p.service.LoanInfoService;
import com.bjpowernode.p2p.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class IndexController {
    @Reference(interfaceClass = BidInfoService.class, version = "1.0.0", check = false, timeout = 15000)
    private BidInfoService bidInfoService;

    @Reference(interfaceClass = LoanInfoService.class, version = "1.0.0", check = false, timeout = 15000)
    private LoanInfoService loanInfoService;

    @Reference(interfaceClass = UserService.class, version = "1.0.0", check = false, timeout = 15000)
    private UserService userService;

    /**
     * 主页信息
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String toIndex(Model model) {
        //平均历史年化收益率
        Double avgRate = loanInfoService.queryAvgRate();
        model.addAttribute(MyConst.AVG_RATE, avgRate);

        //总平台用户数
        Integer totalUsers = userService.queryTotalUsers();
        model.addAttribute(MyConst.TOTAL_USERS, totalUsers);

        //累计成交额
        Double totalBidMoney = bidInfoService.queryTotalBidMoney();
        model.addAttribute(MyConst.TOTAL_BID_MONEY, totalBidMoney);

        //新手宝，取第一个
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("productType", MyConst.PRODUCT_TYPE_SXB);
        paramMap.put("currentPage", 0);
        paramMap.put("pageSize", 1);
        List<LoanInfo> xsbList = loanInfoService.queryByProductType(paramMap);
        model.addAttribute("xsbList", xsbList);

        //优选，取前四个
        paramMap.put("productType", MyConst.PRODUCT_TYPE_YX);
        paramMap.put("currentPage", 0);
        paramMap.put("pageSize", 4);
        List<LoanInfo> yxbList = loanInfoService.queryByProductType(paramMap);
        model.addAttribute("yxbList", yxbList);

        //散标，取前八个
        paramMap.put("productType", MyConst.PRODUCT_TYPE_SB);
        paramMap.put("currentPage", 0);
        paramMap.put("pageSize", 8);
        List<LoanInfo> sbList = loanInfoService.queryByProductType(paramMap);
        model.addAttribute("sbList", sbList);

        return "index";
    }
}
