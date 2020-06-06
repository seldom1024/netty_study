package com.seldom.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/6/6 19:16
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {

        // 创建一个ByteBuf
        /**
         * Creates a new big-endian Java heap buffer with the specified {@code capacity}, which
         * expands its capacity boundlessly on demand.  The new buffer's {@code readerIndex} and
         * {@code writerIndex} are {@code 0}.
         */
        // 在netty的buffer中，不需要flip反转
        // 底层维护 readerIndex 和 writerIndex
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        // 输出
         for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readerIndex(i));
        }
    }
}
