package com.seldom.IO_study02_NIO.NIOFileChannel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: 向文件输出
 * @author: Seldom
 * @time: 2020/5/25 23:53
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {
        String str = "seldom01";

        // 创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("D:/seldom.txt");

        // 通过 fileOutputStream 获取对应的 FileChannel
        // channel 的真实类型是 FileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();

        // 创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 将 str 放进 换成缓冲区
        byteBuffer.put(str.getBytes());

        // 对 换缓冲区反转
        byteBuffer.flip();

        // 将缓冲区 数据写入 channel
        channel.write(byteBuffer);
        fileOutputStream.close();
    }
}
