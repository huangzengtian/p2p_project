package com.bjpowernode.p2p;

import java.util.Random;

public class CodeUtils {
    private CodeUtils() {
    }

    /**
     * 生成n位随机验证码
     * @param num
     * @return
     */
    public static String getCode(Integer num) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (Integer i = 0; i < num; i++) {
            int n = random.nextInt(10);
            sb.append(n);
        }
        return sb.toString();
    }

}
