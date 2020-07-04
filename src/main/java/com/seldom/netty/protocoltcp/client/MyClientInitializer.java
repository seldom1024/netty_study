package com.seldom.netty.protocoltcp.client;

import com.seldom.netty.protocoltcp.MyMessageDecoder;
import com.seldom.netty.protocoltcp.MyMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 21:04
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyMessageEncoder());
        pipeline.addLast(new MyMessageDecoder());
        pipeline.addLast(new MyClientHandler());
    }
}
