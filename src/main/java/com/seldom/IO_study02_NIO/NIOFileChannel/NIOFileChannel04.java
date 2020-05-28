package com.seldom.IO_study02_NIO.NIOFileChannel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @description: 使用 transferFrom  transferTo
 * @author: Seldom
 * @time: 2020/5/26 23:41
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception{
        // 创建流
        FileInputStream fileInputStream = new FileInputStream("D:/a.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("D:/b.jpg");

        // 获取channel
        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChannel = fileOutputStream.getChannel();

        // 使用 transferFrom
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        // or
        inChannel.transferTo(0, inChannel.size(), outChannel);
        fileInputStream.close();
        fileOutputStream.close();
    }
}
