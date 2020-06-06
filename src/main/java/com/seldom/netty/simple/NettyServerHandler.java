package com.seldom.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.internal.ChannelUtils;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * 说明：
 * 1. 我们自定义一个 handler，需要继承 netty 规定好的某个 HandlerAdapter
 * 2. 这时我们自定义的 Handler 才能成为一个 Handler
 * @author: Seldom
 * @time: 2020/5/31 15:21
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    // 读取数据的事件（我们可以读取客户端的数据）
    /**
     *
     * @param ctx 上下文对象，有管道（pipeline）、通道（channel）、地址
     * @param msg 客户端发送的数据，默认是 Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程: " + Thread.currentThread().getName());
        System.out.println("Server ctx = " + ctx);
        System.out.println("channel and pipeline 的关系");

        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链表

        // 把任务放进 taskQueue 中，异步执行（这样不会因为计算时间过程而阻塞）
        // 先拿到 eventLoop 对象
        channel.eventLoop().execute(()->{
            try {
                Thread.sleep(10 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("异步任务1执行成功" + System.currentTimeMillis(), CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                System.out.println("出现异常： " + e.getMessage());
            }
        });

        // 把任务提交到 scheduledTaskQueue
        channel.eventLoop().schedule(()->{
            try {
                Thread.sleep(10 * 1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("异步任务2执行成功"  + System.currentTimeMillis(), CharsetUtil.UTF_8));
            } catch (InterruptedException e) {
                System.out.println("出现异常： " + e.getMessage());
            }
        }, 10, TimeUnit.SECONDS);


        // 将 msg 转成 ByteBuf (由 netty 提供的， 不是 NIO 的 ByteBuffer)
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("Client send msg: " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("Client addr: " + ctx.channel().remoteAddress());
    }

    // 数据读取完毕后的操作
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush  = write + flush
        // 一般来讲，需要对发送的数据编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello, 客户端", CharsetUtil.UTF_8));
    }



    // 发生异常，关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
