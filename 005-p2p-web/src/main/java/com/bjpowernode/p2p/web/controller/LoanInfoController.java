package com.bjpowernode.p2p.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.MyConst;
import com.bjpowernode.p2p.ResultUtils;
import com.bjpowernode.p2p.model.BidInfo;
import com.bjpowernode.p2p.model.FinanceAccount;
import com.bjpowernode.p2p.model.LoanInfo;
import com.bjpowernode.p2p.model.User;
import com.bjpowernode.p2p.model.vo.PageVO;
import com.bjpowernode.p2p.service.BidInfoService;
import com.bjpowernode.p2p.service.FinanceAccountService;
import com.bjpowernode.p2p.service.LoanInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoanInfoController {
    @Reference(interfaceClass = LoanInfoService.class, check = false, version = "1.0.0",timeout = 15000)
    private LoanInfoService loanInfoService;

    @Reference(interfaceClass = BidInfoService.class, check = false, version = "1.0.0", timeout = 15000)
    private BidInfoService bidInfoService;

    @Reference(interfaceClass = FinanceAccountService.class, check = false, version = "1.0.0", timeout = 15000)
    private FinanceAccountService financeAccountService;

    /**
     * 投资产品详情页面
     * @return
     */
    @RequestMapping("/loan/loanInfo")
    public String toLoanInfo(Integer id,
                             Model model,
                             HttpSession session,
                             @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
                             ) {
        //投资产品信息详情
        LoanInfo loanInfo = loanInfoService.queryById(id);
        model.addAttribute("loanInfo", loanInfo);

        //投资记录列表分页查询(投资信息表和用户表连表查询)
        //根据产品id查询该产品的总投资记录数
        Integer totalRows = bidInfoService.queryTotalByLoanId(id);
        //总页数
        Integer totalPages = (totalRows-1) / pageSize + 1;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loanId", id);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", currentPage);
        currentPage = (currentPage-1)*pageSize;
        paramMap.put("currentPage", currentPage);
        paramMap.put("pageSize", pageSize);
        List<BidInfo> bidInfoList = bidInfoService.queryByUidANdLoanId(paramMap);
        //将分页数据保存到VO类中
        PageVO<BidInfo> pageVO = new PageVO<>();
        pageVO.setTotalRows(totalRows);
        pageVO.setTotalPages(totalPages);
        pageVO.setDataList(bidInfoList);
        model.addAttribute("pageVO", pageVO);

        //查询用户余额
        User user = (User) session.getAttribute(MyConst.SESSION_USER);
        if (ObjectUtils.allNotNull(user)) {
            Integer uid = user.getId();
            FinanceAccount financeAccount = financeAccountService.queryByUid(uid);
            model.addAttribute("financeAccount", financeAccount);
        }
        return "loanInfo";
    }

    @RequestMapping("/loanInfo/bid")
    @ResponseBody
    public Map<String, Object> bid(Double bidMoney,
                                   Integer loanId,
                                   Integer productStatus,
                                   HttpSession session) {
        try {
            User user = (User) session.getAttribute(MyConst.SESSION_USER);
            Integer uid = user.getId();
            loanInfoService.bid(bidMoney, loanId, uid, productStatus);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failMsg("网络繁忙，投资失败");
        }
        return ResultUtils.successMsg("");
    }
}
