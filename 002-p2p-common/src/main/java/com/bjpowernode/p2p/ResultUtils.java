package com.bjpowernode.p2p;

import java.util.HashMap;
import java.util.Map;

public class ResultUtils {
    private ResultUtils() {
    }

    public static Map<String, Object> successMsg(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        map.put("msg", msg);
        map.put("result", true);
        return map;
    }

    public static Map<String, Object> failMsg(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", -1);
        map.put("msg", msg);
        map.put("result", false);
        return map;
    }
}
