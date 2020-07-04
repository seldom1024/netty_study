package com.seldom.netty.inboundhandleandout.client;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/7/4 17:31
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器:" + msg);
    }

    /**
     * channelActive 发送数据
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client 发送数据：");
        /**
         * 尝试去发送 字符串
         * 分析：
         * 1. 服务器 8 字节 读取 依然显示 long
         * MyByteToLongDecoder dy
         * 从客户端:/127.0.0.1:1321 读取long: 7017280452178371428
         * MyByteToLongDecoder dy
         * 从客户端:/127.0.0.1:1321 读取long: 7017280452178371428
         *
         * 2. MyLongToByteEncoder 并没有被调用 父类 MessageToByteEncoder
         * 原因：
         * write 方法
         * if (acceptOutboundMessage(msg)) { // 判断数据类型，如果是就处理，不是就直接跳过
         *                 @SuppressWarnings("unchecked")
         *                 I cast = (I) msg;
         *                 buf = allocateBuffer(ctx, cast, preferDirect);
         *                 try {
         *                     encode(ctx, cast, buf);
         *                 } finally {
         *                     ReferenceCountUtil.release(cast);
         *                 }
         *
         *                 if (buf.isReadable()) {
         *                     ctx.write(buf, promise);
         *                 } else {
         *                     buf.release();
         *                     ctx.write(Unpooled.EMPTY_BUFFER, promise);
         *                 }
         *                 buf = null;
         *             } else {
         *                 ctx.write(msg, promise);
         *             }
         */
        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));
        //ctx.writeAndFlush(12346L); // 发送 long

    }
}
