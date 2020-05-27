package com.seldom.netty_study05_webScoket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/5/24 21:41
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**
         * 因为这是基于http协议的，所以使用http编解码器。前面已经讲过了
         */
        pipeline.addLast(new HttpServerCodec());
        // 以块的方式去写
        pipeline.addLast(new ChunkedWriteHandler());
        /**
         *  特别重要，http数据在传输过程是分段的
         *  HttpObjectAggregator,而他就是将多个段聚合起来。
         */
        pipeline.addLast(new HttpObjectAggregator(8192));
        /**
         * 对于 webSocket，他的数据传输是以 frame（帧）的形式传递
         * 可以查看 WebSocketFrame，他有六个子类
         * /ws,表示的 websocket 的地址。即： ws://ip:port/ws 最后的 ws
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler());

    }
}
