package com.seldom.netty_study02_simpleSocket.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/5/24 17:54
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * ChannelHandlerContext ctx: 表示请求上下文信息。可用于获得channel，远程地址等
     * msg ：表示客户端发送来的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+":"+msg);
        ctx.channel().writeAndFlush("fromServer"+ UUID.randomUUID());

    }

    /**
     * 异常的捕获,一般出现异常，就把连接关闭
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
