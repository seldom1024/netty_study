package com.seldom.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/5/31 15:48
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    // 通道就绪出发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello Server : 喵", CharsetUtil.UTF_8));
    }

    // 当通道有读事件时候，会触发
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf  = (ByteBuf) msg;
        System.out.println("服务器回复的消息： " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址： " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
