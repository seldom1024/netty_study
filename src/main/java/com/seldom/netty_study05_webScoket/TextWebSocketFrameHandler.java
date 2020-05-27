package com.seldom.netty_study05_webScoket;

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
 * @description:
 * @author: Seldom
 * @time: 2020/5/24 21:42
 */

/**
 * 这里的泛型是TextWebSocketFrame，表示一个文本帧（Frame）
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 定义一个channelGroup，用来保存多个用户
     * 一个用户表示一个channel，将他们加入到一个组
     */
    private  static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //一定要输出msg的text，不然得不到正确消息
        System.out.println("服务端收到的消息:"+msg.text());
        /**
         * 其实这里writeAndFlush（）里面传入的是object类型，说明无论传什么参数都不会报错
         * 但是我们这里是要传一个TextWebSocketFrame对象，所以不能传单独的字符串，传了也传不出去
         * 因为我们只用了TextWebSocketFrame
         */
        //ctx.channel().writeAndFlush("123");
        //ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器的时间："+ LocalDateTime.now()+"\n"+msg.text()));
        channelGroup.writeAndFlush(new TextWebSocketFrame("服务器的时间："+ LocalDateTime.now()+"\n"+msg.text()));
    }

    /**
     * web客户连接后，会打印这行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /*
        id表示唯一，有长有短，长的asLongText，唯一。短的asShortText()不唯一
         */
        System.out.println("handlerAdded的ID："+ctx.channel().id().asLongText());
        channelGroup.add(ctx.channel());
    }

    /**
     * 一个很有趣的现象，如果客户端刷新一下，实际上会调用这个方法，因为连接断了，新建了一个连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved的ID："+ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生");
        //异常一旦发生就要关闭连接
        ctx.close();
    }
}
