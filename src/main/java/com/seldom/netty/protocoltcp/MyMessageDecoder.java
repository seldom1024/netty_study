package com.seldom.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 22:18
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");

        // 将得到的 二进制字节码 -> MessageProtocol 数据包（对象）
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);

        // 封装
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(content);

        // 传递
        out.add(messageProtocol);
    }
}
