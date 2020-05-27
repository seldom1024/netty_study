package com.seldom.IO_study02_NIO;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

/**
 * @description: 读取文件
 * @author: Seldom
 * @time: 2020/5/26 23:11
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws Exception{

        FileInputStream fileInputStream = new FileInputStream("D:/seldom.txt");

        // 获取 channel
        FileChannel channel = fileInputStream.getChannel();

        // 分配缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 读取信息
        channel.read(byteBuffer);

        // 转换字符串
        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.position()));

        fileInputStream.close();
    }
}
