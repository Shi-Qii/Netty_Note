# ByteBuf

<br>

-------------------

<br>

## 簡介

一般的 Java NIO 提供的 Byte 容器是 ByteBuffer，Netty 使用自製的 ByteBuf 來替代。

ByteBuf 功能相較 ByteBuffer 更強大且靈活。是一個能更好的搭配 Netty 進行通信的資料載體。

<br>

## 說明

1.  [HeapBufAndDirectBufDemo.java](./src/main/java/com/frizo/nettynote/bytebuf/HeapBufAndDirectBufDemo.java)
    
    *   ByteBuf 的使用模式分為三種先介紹前兩種 `HeapBuf` 和 `DirectBuf`。
    
        *   HeapBuf 是將資料存在 JVM Heap 裡面，這種模式稱為 `支撐陣列 (backing array)`。
            特點就是可以快速的分配及釋放空間。
            
        *   DirectBuf 的資料是存儲在 Heap 之外，被 Channel 發送出去時不必再被複製一次到直接緩衝區。
            缺點部分剛好是 HeapBuf 的優點。
            
    <br>

2.  [CompositeByteBufDemo.java](./src/main/java/com/frizo/nettynote/bytebuf/CompositeByteBufDemo.java)

    *  `CompositeByteBuf` 是第三種使用模式，可以將任意多個 ByteBuf 組合成一個個體 ByteBuf。
    
    *   要注意 readableBytes() 事實上是 readableBytes() writerIndex - readerIndex。CompositeByteBuf 組合多個 ByteBuf 後 
        writeIndex 會歸零，需要手動調整。