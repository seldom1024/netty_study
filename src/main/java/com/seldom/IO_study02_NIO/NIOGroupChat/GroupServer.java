package com.seldom.IO_study02_NIO.NIOGroupChat;

import io.netty.buffer.search.AbstractMultiSearchProcessorFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @description: 群聊服务端
 * @author: Seldom
 * @time: 2020/5/28 15:19
 */
public class GroupServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupServer(){
        try {
            // 获取选择器
            selector = Selector.open();
            // 获取ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 非阻塞
            listenChannel.configureBlocking(false);
            // 注册
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void listen(){
        try {
            // 循环处理
            while (true){
                int count = selector.select(2000);
                if (count > 0){ // 有事件处理
                    // 遍历
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        // 取 key
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()){ // 请求连接事件
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            // 注册 到 selector
                            sc.register(selector, SelectionKey.OP_READ);
                            // 提示
                            System.out.println(sc.getRemoteAddress() + " online ");
                        }

                        if (key.isReadable()){ // read 事件
                            // 处理读
                            readData(key);
                        }

                        // 删除当前 key
                        iterator.remove();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    // 读取客户端数据
    private void readData(SelectionKey key){
        SocketChannel channel = null;
        try {
            // 得到 channel
            channel = (SocketChannel) key.channel();
            // 创建 buffer
            ByteBuffer buffer = ByteBuffer.allocate(10);
            int count = channel.read(buffer);
            if (count > 0){ // 有数据
                String msg = new String(buffer.array(), 0, buffer.position());
                System.out.println("From Client: " + msg);
                // 向其他客户端转发消息
                sendMsg(channel, msg);
            }
        }catch (IOException e){
            try {
                System.out.println(channel.getRemoteAddress()+" 离线了...");
                sendMsg(channel, channel.getRemoteAddress()+" 离线了...");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // 转发消息
    private void sendMsg(SocketChannel channel, String msg) throws IOException {
        System.out.println("Server sending msg");
        // 遍历所有注册到 selector 上的 SocketChannel，除了自己
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != channel){
                // 转型
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // 写入数据
                dest.write(buffer);
            }
        }
    }


    public static void main(String[] args) {
        GroupServer groupServer = new GroupServer();
        groupServer.listen();
    }
}
