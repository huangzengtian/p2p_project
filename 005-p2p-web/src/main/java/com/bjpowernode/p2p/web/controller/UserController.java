package com.bjpowernode.p2p.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.MyConst;
import com.bjpowernode.p2p.model.*;
import com.bjpowernode.p2p.model.vo.PageVO;
import com.bjpowernode.p2p.service.BidInfoService;
import com.bjpowernode.p2p.service.FinanceAccountService;
import com.bjpowernode.p2p.service.RechargeRecordService;
import com.bjpowernode.p2p.service.UserService;
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
public class UserController {
    @Reference(interfaceClass = UserService.class, check = false, version = "1.0.0", timeout = 15000)
    private UserService userService;

    @Reference(interfaceClass = FinanceAccountService.class, check = false, version = "1.0.0", timeout = 15000)
    private FinanceAccountService financeAccountService;

    @Reference(interfaceClass = BidInfoService.class, check = false, version = "1.0.0", timeout = 15000)
    private BidInfoService bidInfoService;

    @Reference(interfaceClass = RechargeRecordService.class, check = false, version = "1.0.0", timeout = 15000)
    private RechargeRecordService rechargeRecordService;

    /**
     * 查询用户余额
     * @param session
     * @return
     */
    @RequestMapping("/financeAccount/queryByUid.json")
    @ResponseBody
    public FinanceAccount queryByUid(HttpSession session) {
        User user = (User) session.getAttribute(MyConst.SESSION_USER);
        Integer uid = user.getId();
        return financeAccountService.queryByUid(uid);
    }

    /**
     * 个人中心页面详情
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/loan/myCenter")
    public String toMyCenter(HttpSession session,
                             Model model,
                             @RequestParam(value = "currentPage", defaultValue = "1", required = false) Integer currentPage,
                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        //显示余额
        User user = (User) session.getAttribute(MyConst.SESSION_USER);
        Integer uid = user.getId();
        FinanceAccount financeAccount = financeAccountService.queryByUid(uid);
        model.addAttribute("financeAccount", financeAccount);

        //显示最近投资记录(根据用户id查投资记录详情，并分页)
        //根据用户id查询总记录数
        Integer totalRows = bidInfoService.queryTotalRowsByUid(uid);
        //总页数
        Integer totalPages = (totalRows-1)/pageSize+1;
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        currentPage = (currentPage-1)*pageSize;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", uid);
        paramMap.put("currentPage", currentPage);
        paramMap.put("pageSize", pageSize);
        List<BidInfo> bidInfoList = bidInfoService.queryBidInfoByUid(paramMap);
        PageVO<BidInfo> bidInfoPageVO = new PageVO<>(totalPages, totalRows, bidInfoList);
        model.addAttribute("bidInfoPageVO", bidInfoPageVO);
        //显示最近充值记录

        //显示最近收益记录
        return "myCenter";
    }

    /**
     * 个人投资记录详情
     * @return
     */
    @RequestMapping("/loan/myInvest")
    public String toMyInvest(HttpSession session,
                             Model model,
                             @RequestParam(value = "currentPage", defaultValue = "1", required = false) Integer currentPage,
                             @RequestParam(value = "pageSize", defaultValue = "6", required = false) Integer pageSize) {

        User user = (User) session.getAttribute(MyConst.SESSION_USER);
        Integer uid = user.getId();
        //根据用户id查询总记录数
        Integer totalRows = bidInfoService.queryTotalRowsByUid(uid);
        //总页数
        Integer totalPages = (totalRows-1)/pageSize+1;
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        currentPage = (currentPage-1)*pageSize;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", uid);
        paramMap.put("currentPage", currentPage);
        paramMap.put("pageSize", pageSize);
        List<BidInfo> bidInfoList = bidInfoService.queryBidInfoByUid(paramMap);
        PageVO<BidInfo> bidInfoPageVO = new PageVO<>(totalPages, totalRows, bidInfoList);
        model.addAttribute("bidInfoPageVO", bidInfoPageVO);
        return "myInvest";
    }

    /**
     * 个人充值记录详情
     */
    @RequestMapping("/loan/myRecharge")
    public String toMyRecharge(HttpSession session,
                               Model model,
                               @RequestParam(value = "currentPage", defaultValue = "1", required = false) Integer currentPage,
                               @RequestParam(value = "pageSize", defaultValue = "6", required = false) Integer pageSize) {

        User user = (User) session.getAttribute(MyConst.SESSION_USER);
        Integer uid = user.getId();
        //查询当前用户充值记录条数
        Integer totalRows = rechargeRecordService.queryTotalRowsByUid(uid);
        //总页数
        Integer totalPages = (totalRows-1)/pageSize+1;
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        currentPage = (currentPage-1)*pageSize;
        //分页查询当前用户投资记录
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", uid);
        paramMap.put("currentPage", currentPage);
        paramMap.put("pageSize", pageSize);
        List<RechargeRecord> rechargeRecordList = rechargeRecordService.queryInfoByUid(paramMap);
        PageVO<RechargeRecord> pageVO = new PageVO<>(totalPages, totalRows, rechargeRecordList);
        model.addAttribute("pageVO", pageVO);
        return "myRecharge";
    }
}
