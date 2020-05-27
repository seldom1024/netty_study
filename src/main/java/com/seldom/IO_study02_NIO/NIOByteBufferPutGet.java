package com.seldom.IO_study02_NIO;

import javax.swing.*;
import java.nio.ByteBuffer;

/**
 * @description: put 什么类型 get 就应该使用相应类型取数据
 * @author: Seldom
 * @time: 2020/5/26 23:49
 */
public class NIOByteBufferPutGet {
    public static void main(String[] args) {
        // 创建 buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        // 存放数据
        byteBuffer.putInt(100);
        byteBuffer.putLong(9);
        byteBuffer.putChar('张');
        byteBuffer.putShort((short) 4);

        // 取出
        byteBuffer.flip();
        // 注意顺序，就算没有异常，也会出现数据异常
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getChar());
        System.out.println(byteBuffer.getShort());
    }
}
