package com.seldom.IO_study02_NIO;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: NIOMappedByteBuffer 可以让文件直接在内存（堆外）中进行修改，而如何同步到文件由NIO来完成
 * @author: Seldom
 * @time: 2020/5/27 0:00
 */
public class NIOMappedByteBuffer {
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:/seldom.txt", "rw");

        // 获取对应的 channel
        FileChannel channel = randomAccessFile.getChannel();

        // 实际类型 DirectByteBuffer
        // 参数1 FileChannel.MapMode.READ_WRITE 使用读写模式
        // 参数2 0 起始位置
        // 参数3 5 是映射到内存的大小，即将文件 seldom.txt 的多少字节映射到内存
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
        // 映射大小为5 实际操作只有 0-4 这里抛异常 IndexOutOfBoundsException
        mappedByteBuffer.put(5, (byte) '5');

        randomAccessFile.close();
    }
}
