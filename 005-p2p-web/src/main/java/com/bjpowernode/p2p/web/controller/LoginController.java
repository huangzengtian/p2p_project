package com.bjpowernode.p2p.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.MyConst;
import com.bjpowernode.p2p.ResultUtils;
import com.bjpowernode.p2p.model.User;
import com.bjpowernode.p2p.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Controller
public class LoginController {
    @Reference(interfaceClass = UserService.class, check = false, version = "1.0.0", timeout = 15000)
    private UserService userService;

    /**
     * 跳转登陆页面
     * @return
     */
    @RequestMapping("/loan/page/login")
    public String toLogin() {
        return "login";
    }

    /**
     * 登陆检查
     * @param phone
     * @param loginPassword
     * @param session
     * @return
     */
    @RequestMapping("/user/queryByPhoneAndLoginPassword")
    @ResponseBody
    public Map<String, Object> loginCheck(String phone,
                          String loginPassword,
                          HttpSession session) {

        User user = userService.queryByPhoneAndLoginPassword(phone, loginPassword);
        if (ObjectUtils.allNotNull(user)) {
            //修改最后登陆时间
            user.setLastLoginTime(new Date());
            userService.edit(user);
            session.setAttribute(MyConst.SESSION_USER, user);
            return ResultUtils.successMsg("");
        }
        return ResultUtils.failMsg("账号不存在或密码错误！");
    }

    /**
     * 退出登陆
     * @param session
     * @return
     */
    @RequestMapping("/loan/logout")
    public String logout(HttpSession session) {
        User user = (User)session.getAttribute(MyConst.SESSION_USER);
        if (ObjectUtils.allNotNull(user)) {
            session.removeAttribute(MyConst.SESSION_USER);
        }
        return "redirect:/loan/page/login";
    }
}
