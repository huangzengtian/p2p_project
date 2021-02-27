package com.bjpowernode.p2p.web.controller;

import java.io.FileInputStream;
import java.math.BigDecimal;

public class TEst {
    public static void main(String[] args) {
        BigDecimal bd1 = new BigDecimal("0.1");
        BigDecimal bd2 = new BigDecimal("0.10");
        System.out.println(bd1.equals(bd2));
        System.out.println(bd1.compareTo(bd2) == 0);

    }
}
