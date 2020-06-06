package com.seldom.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/5/31 20:11
 */

/**
 * 说明：
 * 1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter 的子类
 * 2. HttpObject 表示客户端和服务端相互通讯的数据封装成 HttpObject
 *
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    // 读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 判断是不是 httpRequest
        if (msg instanceof HttpRequest){
            HttpRequest request = (HttpRequest) msg;

            String uri = request.uri();
            if (uri.equals("/favicon.ico")){
                return;
            }

            System.out.println("msg class ： " + msg.getClass());
            System.out.println("client addr ：" + ctx.channel().remoteAddress());

            // 回复信息 [http协议]
            ByteBuf content = Unpooled.copiedBuffer("Hello， 我是服务器", CharsetUtil.UTF_8);

            // 构造 http 响应
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 返回
            ctx.writeAndFlush(httpResponse);
        }
    }
}
