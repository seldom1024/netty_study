package com.seldom.netty_study02_simpleSocket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @description: 客户端
 * @author: Seldom
 * @time: 2020/5/24 18:08
 */
public class MyClient {

    public static void main(String[] args) throws InterruptedException {
        // 事件循环组,只有一个循环组
        EventLoopGroup eventLoopGroup=new NioEventLoopGroup();
        try{
            /**
             *  注意
             *  1.客户端不在是ServerBootstrap而是Bootstrap
             *  2.反射类不是NioServerSocketChannel而是NioSocketChannel
             *  3.一般使用handler，而不是用childHandler
             *  4.不是bind绑定端口而是connect
             */
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new MyClientInitializer());

            ChannelFuture channelFuture =bootstrap.connect("localhost",8898).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
