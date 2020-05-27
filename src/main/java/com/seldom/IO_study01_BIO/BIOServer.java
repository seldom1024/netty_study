package com.seldom.IO_study01_BIO;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: 使用传统的 BIO 作为服务（浪费资源）
 * @author: Seldom
 * @time: 2020/5/25 22:35
 */
public class BIOServer {
    /**
     * 1. 维护一个线程池与连接通讯
     * 2. 如果按客户有连接进来，则创建线程处理
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8899);

        System.out.println("Server Starting");
        // 循环等待连接
        while (true) {
            // 阻塞等待连接
            Socket socket = serverSocket.accept();
            executorService.submit(() -> {
                handler(socket);
            });
            System.out.println("Client connecting");
        }
    }

    public static void handler(Socket socket) {
        char[] chars = new char[100];
        try {
            System.out.println("Thread id : " + Thread.currentThread().getId() + " Name :" + Thread.currentThread().getName());
            InputStreamReader reader = new InputStreamReader(socket.getInputStream(), CharsetUtil.UTF_8);
            // 一直读取
            while (true) {
                System.out.println("Thread id : " + Thread.currentThread().getId() + " Name :" + Thread.currentThread().getName());
                // 阻塞读取
                int read = reader.read(chars);
                if (read != -1) {
                    System.out.println(new String(chars, 0, read));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("Client Closed");
            } catch (IOException ignored) {
            }
        }
    }
}
