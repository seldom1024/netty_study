package com.seldom.IO_study02_NIO.NIOFileChannel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: 文件拷贝(使用一个 buffer)
 * @author: Seldom
 * @time: 2020/5/26 23:27
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception{

        FileInputStream fileInputStream = new FileInputStream("D:/seldom.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("D:/seldom01.txt");

        FileChannel inChannel = fileInputStream.getChannel();
        FileChannel outChannel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true){
            // 清空上次缓存
            buffer.clear();
            // -1 表示读取完毕
            int read = inChannel.read(buffer);
            if (read == -1){
                break;
            }
            // 读写反转
            buffer.flip();
            outChannel.write(buffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
