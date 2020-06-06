package com.seldom.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.Buffer;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/6/6 19:39
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {

        // 创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello World!!", CharsetUtil.UTF_8);

        // 使用相关的方法
        if (byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            // 转 String
            System.out.println(new String(content, CharsetUtil.UTF_8));

            System.out.println("byteBuf = " + byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());

            // 可读的字节数
            System.out.println(byteBuf.readableBytes());

            // 循环读取
            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.print((char) byteBuf.getByte(i));
            }
            System.out.println();
            System.out.println(byteBuf.getCharSequence(0, 4, CharsetUtil.UTF_8));
        }
    }
}
