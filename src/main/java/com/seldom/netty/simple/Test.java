package com.seldom.netty.simple;

import io.netty.util.NettyRuntime;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/5/31 17:41
 */
public class Test {
    public static void main(String[] args) {
        // cpu 核数
        System.out.println(NettyRuntime.availableProcessors());
    }
}
