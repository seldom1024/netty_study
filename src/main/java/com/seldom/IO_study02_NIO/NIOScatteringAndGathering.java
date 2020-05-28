package com.seldom.IO_study02_NIO;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * @description:
 * @Scattering： 数据写入到 buffer 时，可以采用 buffer 数组，依次写入 【分散】
 * @Gathering: 从 buffer 读取数据时，可以采用 buffer 数组一次读
 *
 * @author: Seldom
 * @time: 2020/5/28 0:19
 */
public class NIOScatteringAndGathering {
    public static void main(String[] args) throws Exception{

        // 使用 ServerSocketChannel 和 SocketChannel 网络
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到 socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建 buffer 数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 等待客户端连接（telnet）
        SocketChannel socketChannel = serverSocketChannel.accept();

        // 假定就是 8 个字节
        int messageLength = 8;

        // 循环读取
        while (true){
            int byteRead = 0;
            while (byteRead<messageLength){
                long read = socketChannel.read(byteBuffers);
                // 读取
                if (read == -1){
                    break;
                }
                byteRead+=read;
                System.out.println("byteRead: " + byteRead);
                // 使用流打印，看看当前的这个 buffer 的 position 和 limit
                Arrays.asList(byteBuffers).stream().map(buffer->"position: " + buffer.position()
                + ", limit: " + buffer.limit()).forEach(System.out::println);
            }

            // 将所有的 buffer 进行 flip
            Arrays.asList(byteBuffers).forEach(Buffer::flip);
            // 将数据读取显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength){
                long write = socketChannel.write(byteBuffers);
                if (write == 0){
                    break;
                }
                byteWrite+=write;
            }

            // 将所有的 buffer 进行 clear
            Arrays.asList(byteBuffers).forEach(Buffer::clear);

            System.out.println("byteRead: " + byteRead + "\nbyteWrite: " + byteWrite + "\nmessageLength: " + messageLength);
        }

    }
}
