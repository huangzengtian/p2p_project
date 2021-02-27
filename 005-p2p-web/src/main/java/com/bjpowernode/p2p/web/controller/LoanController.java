package com.bjpowernode.p2p.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.model.LoanInfo;
import com.bjpowernode.p2p.model.vo.PageVO;
import com.bjpowernode.p2p.service.LoanInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoanController {
    @Reference(interfaceClass = LoanInfoService.class, check = false, version = "1.0.0", timeout = 15000)
    private LoanInfoService loanInfoService;

    /**
     * 根据产品类型（非必须）分页显示产品列表
     * @param model
     * @param productType
     * @return
     */
    @RequestMapping("/loan/loan")
    public String toLoan(Model model,
                         @RequestParam(value = "productType", required = false) Integer productType,
                         @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "9") Integer pageSize) {

        //查询总记录数
        Integer totalRows = loanInfoService.queryTotalRowsByProductType(productType);
        //总页数
        Integer totalPages = (totalRows-1)/pageSize+1;
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageSize", pageSize);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("productType", productType);
        currentPage = (currentPage-1)*pageSize;
        paramMap.put("currentPage", currentPage);
        paramMap.put("pageSize", pageSize);
        List<LoanInfo> loanInfoList = loanInfoService.queryByProductType(paramMap);
        PageVO<LoanInfo> pageVO = new PageVO<>();
        pageVO.setTotalRows(totalRows);
        pageVO.setTotalPages(totalPages);
        pageVO.setDataList(loanInfoList);
        model .addAttribute("pageVO", pageVO);
        return "loan";
    }
}
