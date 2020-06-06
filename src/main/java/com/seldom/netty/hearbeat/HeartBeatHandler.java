package com.seldom.netty.hearbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/6/6 21:03
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    /**
     *
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String evenType = null;
            switch (event.state()){
                case ALL_IDLE:
                    evenType = "读写空闲";
                    break;
                case READER_IDLE:
                    evenType = "写空闲";
                    break;
                case WRITER_IDLE:
                    evenType = "读空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "超时事件：" + evenType);
        }
    }
}
