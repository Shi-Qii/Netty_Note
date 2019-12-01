package com.frizo.nettynote.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class HeapBufAndDirectBufDemo {

    public static void main(String[] args) {
        // 建立一個 heap 緩衝區 (data 存在 JVM heap 中)
        ByteBuf heapBuf = Unpooled.buffer();
        heapBuf.writeBytes("Hello, I'm headBuf !".getBytes());

        // 建立一個 direct 緩衝區 (data 存在 JVM heap 之外，讀取時不用中間緩存區，可直接本地調用)
        ByteBuf directBuf = Unpooled.directBuffer();
        directBuf.writeBytes("Hello, I'm directBuf !".getBytes());

        readByteBuf(heapBuf);
        readByteBuf(directBuf);
    }

    private static void readByteBuf(ByteBuf buf){
        // hasArray() 方法返回是否有支撐陣列，有就是 heapBuffer，沒有就是 directBuffer。

        // headBuf 部分
        if(buf.hasArray()){
            byte[] array = buf.array(); // 取得資料陣列
            int offset = buf.arrayOffset() + buf.readerIndex(); // 取得第一個 byte 偏移量
            int length = buf.readableBytes(); // 取得可讀 byte 長度
            System.out.println("heapBuffer 處理 :>> " + new String(array));
        }

        // directBuffer 部分
        if(!buf.hasArray()){
            int length = buf.readableBytes(); // 取得可讀 byte 長度
            byte[] array = new byte[length];
            buf.getBytes(buf.readerIndex(), array); // 從索引 0 開始讀出並放入 array 中
            System.out.println("directBuffer 處理 :>> " + new String(array));
        }
    }
}
