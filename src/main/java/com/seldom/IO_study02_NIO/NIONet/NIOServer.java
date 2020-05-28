package com.seldom.IO_study02_NIO.NIONet;

import io.netty.channel.ServerChannel;

import javax.naming.CompositeName;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @author: Seldom
 * @time: 2020/5/28 12:40
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        // 创建 ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到 Selector 对象
        Selector selector = Selector.open();
        // 绑定 端口，监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 把 serverSocketChannel 注册到 selector 关心连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            // 等待1s，如果没有事件发生，返回
            if (selector.select(1000) == 0){
                //System.out.println("no connection...");
                continue;
            }
            // 如果返回>0,获取相关的 selectionKeys 集合
            // 1. 如果返回>0，已经获取到关注的事件
            // 2. selector.selectedKeys() 返回关注时间的集合
            // 通过 selectionKeys 反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                // 获取 SelectionKey
                SelectionKey key = keyIterator.next();
                // 根据 key 发生的时间，做不同的处理
                if(key.isAcceptable()){ // 如果是 OP_ACCEPT,有新的连接
                    // 该客户端生成一个 SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 不设置 报错 IllegalBlockingModeException
                    socketChannel.configureBlocking(false);
                    System.out.println("Client want to connect..." + socketChannel.hashCode());
                    // 将当前的 SocketChannel 注册到 Selector,关注事件，同时给 socketChannel 关联一个 Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("connect accept and selectionKey length: " + selector.keys().size());
                }

                if (key.isReadable()){ // 发生 OP_READ
                    // 通过 key 获取 channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取 channel 关联的 buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from client: " + new String(buffer.array(), 0, buffer.position()));
                }

                // 手动从集合中移除当前的 selectionKeys，防止重复操作（多线程）
                keyIterator.remove();
            }
        }
    }
}
