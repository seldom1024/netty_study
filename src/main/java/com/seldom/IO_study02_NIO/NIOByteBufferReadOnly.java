package com.seldom.IO_study02_NIO;

import java.nio.ByteBuffer;

/**
 * @description: 只读 buffer
 * @author: Seldom
 * @time: 2020/5/26 23:56
 */
public class NIOByteBufferReadOnly {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        // 写入
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte)i);
        }

        // 读取
        byteBuffer.flip();

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        System.out.println(readOnlyBuffer.getClass());

        // 读取
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
        // 不可写入
        readOnlyBuffer.put((byte) 2);
    }
}
