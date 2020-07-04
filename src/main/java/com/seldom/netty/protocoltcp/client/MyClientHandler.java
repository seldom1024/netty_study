package com.seldom.netty.protocoltcp.client;

import com.seldom.netty.protocoltcp.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 21:05
 */
public class MyClientHandler  extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("客户端收到信息：" + msg.toString());
        System.out.println("客人端收到信息量：" + ++ count);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 使用客户端发送 10 条收据
        for (int i = 0; i < 10; i++) {
            String mes = "今天天气冷，吃火锅";
            byte[] bytes = mes.getBytes(CharsetUtil.UTF_8);
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setContent(bytes);
            messageProtocol.setLen(bytes.length);
            ctx.writeAndFlush(messageProtocol);
        }
    }
}
