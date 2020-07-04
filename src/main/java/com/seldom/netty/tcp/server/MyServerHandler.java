package com.seldom.netty.tcp.server;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 21:08
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int i = 1;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);

        // 转成 string
        String message = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("服务器收到数据：" + message);
        System.out.println("服务端收到信息量："  + i++);

        // 下发数据,随机 id
        ByteBuf byteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
