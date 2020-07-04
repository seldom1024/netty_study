package com.seldom.netty.inboundhandleandout.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 17:15
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * decode 会根据接收的数据，被调用多次，直到确定没有新的数据被添加到 list
     * 或者是 ByteBuf 没有更多的字节可读为止
     * 如果 list out 不为空，就会将 list 的内容传递给 下一个 channelInBoundHandler
     * channelInBoundHandler 也会被被调用多次
     *
     *
     * @param ctx 上下文
     * @param in 入站的 ByteBuf
     * @param out List 集合，将解码后的数据传给下一个 handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder dy");
        // long 8 字节
        if (in.readableBytes() >= 8){
            // 判断 8 字节然后再加入数据
            out.add(in.readLong());
        }
    }
}
