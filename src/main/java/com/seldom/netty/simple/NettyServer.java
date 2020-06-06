package com.seldom.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @description: netty server
 * @author: Seldom
 * @time: 2020/5/31 15:03
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 创建  bossGroup workerGroup
         * 说明：
         * 1. 创建两个线程组 bossGroup workerGroup
         * 2. bossGroup 只处理 accept，真正和客服端业务处理由 workerGroup 完成
         * 3. 两个都是无限循环
         * 4. bossGroup workerGroup 含有的子线程（NioEventLoop）的个数，默认是实际 cpu 核数 *2
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            // 使用链式编程来进行设置
            bootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用 NioServerSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道测试对象

                        // 给 pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            // 可以使用一个集合管理 SocketChannel，再推送数据时候，将业务加入到各个channel对应的NIOEventLoop 的 taskQueue 或者 scheduleTaskQueue
                            System.out.println("客户SocketChannel hashCode：" + sc.hashCode());
                            sc.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给 workerGroup 的 NioEventLoop 的对应的管道设置处理器

            System.out.println("Server is ready...");

            // 绑定一个端口，并且同步处理，返回一个 ChannelFuture
            ChannelFuture cf = bootstrap.bind(6668).sync();

            // 注册一个监听器，监控我们关♥的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("监听端口成功");
                    }else {
                        System.out.println("失败");
                    }
                }
            });
            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
