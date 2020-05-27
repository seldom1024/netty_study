package com.seldom.netty_study01_http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @description: 响应
 * @author: Seldom
 * @time: 2020/5/24 16:34
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        // 获取请求类型
        System.out.println(msg.getClass());

        // 获取远程地址
        System.out.println(ctx.channel().remoteAddress());


        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest)msg;
            System.out.println("请求方法:"+request.method().name());

            URI uri = URI.create(request.uri());
            if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("favicon.ico");
                return;
            }

            System.out.println("执行了channelRead0");
            ByteBuf hello_world = Unpooled.copiedBuffer("Hello world", CharsetUtil.UTF_8);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    hello_world);
            response.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, hello_world.readableBytes());

            ctx.writeAndFlush(response);

            // 主动关闭
            ctx.close();
        }
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added");
        super.handlerAdded(ctx);
    }


}
