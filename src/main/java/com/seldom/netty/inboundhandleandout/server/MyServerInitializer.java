package com.seldom.netty.inboundhandleandout.server;

import com.seldom.netty.inboundhandleandout.client.MyLongToByteEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 17:12
 */
public class  MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 加入相关的handler
        // 入站的handler进行解码  MyByteToLongDecoder
        pipeline.addLast(new MyByteToLongDecoder());

        // 出站
        pipeline.addLast(new MyLongToByteEncoder());

        // 自定义的 handler 处理业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
