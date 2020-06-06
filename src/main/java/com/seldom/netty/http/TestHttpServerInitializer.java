package com.seldom.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentDecoder;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/5/31 20:11
 */
public class TestHttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        // 获取管道
        ChannelPipeline pipeline = ch.pipeline();

        // 加入一个netty提供的 HttpServerCodec codec => [coder -> decoder]
        /**
         * HttpServerCodec 说明：
         * 1. 是 netty 提供的处理 http 的编解码器
         */
        pipeline.addLast(new HttpServerCodec());
        // 增加自己的
        pipeline.addLast(new TestHttpServerHandler());
    }
}
