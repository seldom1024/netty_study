package com.seldom.netty.protocoltcp.server;


import com.seldom.netty.protocoltcp.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 21:08
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int i = 1;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        System.out.println("服务器收到数据：" + msg);
        System.out.println("服务端收到信息量："  + i++);

        // 下发数据,随机 id
        String response = UUID.randomUUID().toString();
        ctx.writeAndFlush(new MessageProtocol(response));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
