package com.seldom.IO_study02_NIO.NIONet;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/5/28 13:03
 */
public class NIOClient {
    public static void main(String[] args) throws Exception{
        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 服务端 IP port
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
        // 连接
        if (!socketChannel.connect(address)){
            while (!socketChannel.finishConnect()){
                System.out.println("Connecting... do other thing...");
            }
            // 如果连接成功，发数据
            String str = "Hello Seldom";
            // The new buffer will be backed by the given byte array
            // 无需指定大小
            ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
            // 发送数据，将 buffer 写入 channel
            socketChannel.write(buffer);
            System.in.read();
        }
    }
}
