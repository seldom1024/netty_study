package com.seldom.IO_study02_NIO.NIOZeroCopy.newIO;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/5/28 18:19
 */
public class NewIOServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();
        socket.bind(new InetSocketAddress(7001));

        ByteBuffer buffer = ByteBuffer.allocate(4096);

        while (true){
            SocketChannel accept = serverSocketChannel.accept();
            int read= 0;
            while (true){
                int read1 = accept.read(buffer);
                if (read1 <= 0){
                    break;
                }
                read += read1;
                // 倒带 position = 0;
                //        mark = -1;
                buffer.rewind();
            }
            System.out.println(read);
        }
    }
}
