package com.seldom.IO_study02_NIO.NIOGroupChat;

import javax.swing.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @description: 群了客户端
 * @author: Seldom
 * @time: 2020/5/28 15:49
 */
public class GroupChatClient {
    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public GroupChatClient() throws IOException {
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        selector = Selector.open();
        socketChannel.configureBlocking(false);
        // 注册
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName = socketChannel.getLocalAddress().toString().substring(1);
    }

    // 发数据
    public void sendMsg(String msg){
        msg = userName + " said: " + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取消息
    public void readMsg(){
        try {
            int rendChannels = selector.select();
            if (rendChannels>0){ // 有消息可读
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){ // 可以有多个通道
                    SelectionKey key = iterator.next();
                    if (key.isReadable()){
                        // 获取通道
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        while (true){
                            int read = channel.read(buffer);
                            if (read <= 0){
                                break;
                            }
                            System.out.println(new String(buffer.array(), 0, buffer. position()));
                        }
                    }
                    iterator.remove();
                }
            }else {
                System.out.println("no use channel");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient groupChatClient = new GroupChatClient();

        // 启动线程，3s 读取一次
        new Thread(()->{
            while (true){
                groupChatClient.readMsg();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            groupChatClient.sendMsg(line);
        }
    }
}
