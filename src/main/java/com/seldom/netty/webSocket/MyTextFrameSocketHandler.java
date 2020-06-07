package com.seldom.netty.webSocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @description: TextWebSocketFrame 表示文本帧
 * @author: Seldom
 * @time: 2020/6/7 12:19
 */
public class MyTextFrameSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // GlobalEventExecutor.INSTANCE 全局事件执行器，单例
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("Server rec msg: " + msg.text());
        channels.writeAndFlush(new TextWebSocketFrame(LocalDateTime.now() + ": " + msg.text()));
        // 回消息
        //ctx.channel().writeAndFlush(new TextWebSocketFrame(LocalDateTime.now() + ": " + msg.text()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded apply : " + ctx.channel().id().asLongText());//唯一id
        System.out.println("handlerAdded apply : " + ctx.channel().id().asShortText());//不唯一
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved apply : " + ctx.channel().id().asLongText());//唯一id
        System.out.println("handlerRemoved apply : " + ctx.channel().id().asShortText());//不唯一
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught apply : " + ctx.channel().id().asLongText());//唯一id
        System.out.println("exceptionCaught apply : " + ctx.channel().id().asShortText());//不唯一
        System.out.println(cause.getMessage());
    }
}
