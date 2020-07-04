package com.seldom.netty.inboundhandleandout.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 17:19
 */

/**
 * Long 是上一个传下的的数据类型
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("从客户端:" + ctx.channel().remoteAddress() + " 读取long: " + msg);
        ctx.writeAndFlush(987654L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
