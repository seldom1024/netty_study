package com.seldom.netty_study03_socket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description: 多个客户端连接服务端,可以相互发送消息
 *                 并且有上下线通知
 * @author: Seldom
 * @time: 2020/5/24 19:06
 */
public class ChatServer {
    public static void main(String[] args) throws  Exception{
        //事件循环组
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new ChatServerInitializer());
            ChannelFuture channelFuture=serverBootstrap.bind(8898).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
