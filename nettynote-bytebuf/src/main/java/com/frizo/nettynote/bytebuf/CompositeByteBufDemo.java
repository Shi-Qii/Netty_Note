package com.frizo.nettynote.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

public class CompositeByteBufDemo {
    public static void main(String[] args) {
        // 使用 heapBuf 建立 header 資訊
        ByteBuf header = Unpooled.buffer();
        header.writeBytes("User-Agent : Mozilla".getBytes());

        // 使用 directBuf 建立 body 資訊
        ByteBuf body = Unpooled.directBuffer();
        body.writeBytes("<html>...</html>".getBytes());

        // 建立 CompositeByteBuf 並加入 header 與 body
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        messageBuf.addComponents(header, body);
        messageBuf.writerIndex(header.writerIndex() + body.writerIndex()); // 這裡手動重置 writerIndex，否則 readableBytes() 失效。
        printAllComponent(messageBuf);

        // 讀出 CompositeByteBuf 所有資料，與 DirectBuf 操作一致。
        byte[] array = new byte[messageBuf.readableBytes()];
        messageBuf.getBytes(messageBuf.readerIndex(), array);
        System.out.println(new String(array));

        // 刪除索引 0 的 ByteBuf
        messageBuf.removeComponent(0);
        printAllComponent(messageBuf);
    }

    private static void printAllComponent(CompositeByteBuf bufs){
        bufs.forEach(msg ->{
            System.out.println(msg.toString());
        });
    }

}