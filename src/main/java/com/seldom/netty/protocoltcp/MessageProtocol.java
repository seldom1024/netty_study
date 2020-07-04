package com.seldom.netty.protocoltcp;

import io.netty.util.CharsetUtil;

import java.util.Arrays;

/**
 * @description: 自定义协议包
 * @author: Seldom
 * @time: 2020/7/4 21:40
 */
public class MessageProtocol {
    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public MessageProtocol() {
    }

    public MessageProtocol(byte[] content) {
        this.content = content;
        this.len = content.length;
    }
    public MessageProtocol(String content) {
        this.content = content.getBytes(CharsetUtil.UTF_8);
        this.len = this.content.length;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageProtocol{" +
                "len=" + len +
                ", content=" + new String(content) +
                '}';
    }
}
