package com.seldom.netty.webSocket;

import com.sun.corba.se.internal.CosNaming.BootstrapServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @description: 实现web端的长连接
 * @author: Seldom
 * @time: 2020/6/7 12:04
 */
public class MySocketServer {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(1);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    // 基于http协议，使用http编解码
                    pipeline.addLast(new HttpServerCodec());
                    // 以块方式写，添加 ChunkedWriteHandler 处理器
                    pipeline.addLast(new ChunkedWriteHandler());

                    /**
                     * 说明：
                     * 1. http 数据传输过程中是分段的，HttpObjectAggregator 可以将多个段聚合
                     * 2. 这就是为什么：当浏览器发送大量数据时，就会发送多次 http 请求
                     */
                    pipeline.addLast(new HttpObjectAggregator(8192));

                    /**
                     *
                     * 说明：
                     * 1. 对应 websocket ,它的数据是以帧（frame）形式传递
                     * 2. 可以看到 WebsocketFrame 下面有 6 个子类
                     * 3. 浏览器发送请求时 ws://localhost:7000/hello 表示请求的 url
                     * 4. WebSocketServerProtocolHandler 核心功能是将 http 协议升级为 ws 协议，保持长连接
                     * 5. 通过一个状态码 101
                     */
                    pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                    // 自己的逻辑
                    pipeline.addLast(new MyTextFrameSocketHandler());
                }
            });
            ChannelFuture sync = bootstrap.bind(7000).sync();
            sync.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
