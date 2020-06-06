package com.seldom.IO_study02_NIO.NIOZeroCopy.newIO;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/5/28 18:28
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));

        FileChannel channel = new FileInputStream("E:\\实习项目\\尚硅谷Netty学习资料\\资料\\protoc-3.6.1-win32.zip").getChannel();
        long start = System.currentTimeMillis();
        // 在 linux 下一个 transferTo 方法就可以完成传输
        // 在 window 下一次调用 transferTo 只能发送 8m，大文件就需要分段传输，而且要注意传输时候的起始位置(除以8m就知道传输多少次了)
        // transferTo 底层两零拷贝
        long total = channel.transferTo(0, channel.size(), socketChannel);
        System.out.println("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - start));

        socketChannel.close();
        channel.close();
    }
}
