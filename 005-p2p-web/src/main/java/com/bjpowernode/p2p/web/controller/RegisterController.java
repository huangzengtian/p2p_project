package com.bjpowernode.p2p.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bjpowernode.p2p.MyConst;
import com.bjpowernode.p2p.ResultUtils;
import com.bjpowernode.p2p.model.User;
import com.bjpowernode.p2p.service.FinanceAccountService;
import com.bjpowernode.p2p.service.UserService;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class RegisterController {
    @Reference(interfaceClass = UserService.class, version = "1.0.0", check = false, timeout = 15000)
    private UserService userService;

    @Reference(interfaceClass = FinanceAccountService.class, version = "1.0.0", check = false, timeout = 15000)
    private FinanceAccountService financeAccountService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 跳转注册页面
     * @return
     */
    @RequestMapping("/loan/page/register")
    public String toRegister() {
        return "register";
    }

    /**
     * 查询手机号是否存在
     */
    @RequestMapping("/user/queryExistByPhone")
    @ResponseBody
    public Map<String, Object> checkPhone(String phone) {
        User user = userService.queryExistByPhone(phone);
        return user!=null? ResultUtils.successMsg("此手机号已被注册"):ResultUtils.failMsg("");
    }

    /**
     * 获取验证码
     */
    @RequestMapping("/register/getMessageCode")
    @ResponseBody
    public Map<String, Object> getMessageCode(String phone) {
        redisTemplate.opsForValue().set(phone, 123456, 10, TimeUnit.DAYS);
        return ResultUtils.successMsg("");
    }

    /**
     * 核对验证码
     */
    @RequestMapping("/register/checkMessageCode")
    @ResponseBody
    public Map<String, Object> checkMessageCode(String phone, String messageCode) {
        String redisMessageCode = (String) redisTemplate.opsForValue().get(phone);
        System.out.println(redisMessageCode);
        if (redisMessageCode == null) {
            return ResultUtils.failMsg("验证码错误null");
        }
        if (StringUtils.equals(messageCode, redisMessageCode)) {
            return ResultUtils.successMsg("");
        }
        return ResultUtils.failMsg("验证码错误啊");
    }


    /**
     * 注册功能
     */
    @RequestMapping("/user/register.do")
    @ResponseBody
    public Map<String, Object> register(String phone,
                                        String loginPassword,
                                        HttpSession session) {
        try {
            User user = userService.register(phone, loginPassword);
            session.setAttribute(MyConst.SESSION_USER, user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failMsg("网络繁忙注册失败");
        }
        return ResultUtils.successMsg("");
    }
}
