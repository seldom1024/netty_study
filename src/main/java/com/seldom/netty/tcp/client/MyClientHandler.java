package com.seldom.netty.tcp.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 21:05
 */
public class MyClientHandler  extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String message = new String(bytes, CharsetUtil.UTF_8);
        System.out.println("客户端收到信息：" + message);
        System.out.println("客人端收到信息量：" + ++ count);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用客户端发送 10 条收据 hello,server 编号
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,server" + i, CharsetUtil.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
    }
}
