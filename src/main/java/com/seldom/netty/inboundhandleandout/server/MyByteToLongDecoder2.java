package com.seldom.netty.inboundhandleandout.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 19:38
 */

/**
 * ReplayingDecoder 不需要判断可读长度
 */
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println("MyByteToLongDecoder dy");
        out.add(in.readLong());
    }
}
