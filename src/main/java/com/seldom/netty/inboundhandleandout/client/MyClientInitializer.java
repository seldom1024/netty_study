package com.seldom.netty.inboundhandleandout.client;

import com.seldom.netty.inboundhandleandout.server.MyByteToLongDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 17:25
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 加入出站 handler，对数据编码
        pipeline.addLast(new MyLongToByteEncoder());

        //
        pipeline.addLast(new MyByteToLongDecoder());

        // 加入一个自定义的 handler， 处理业务
        pipeline.addLast(new MyClientHandler());
    }
}
