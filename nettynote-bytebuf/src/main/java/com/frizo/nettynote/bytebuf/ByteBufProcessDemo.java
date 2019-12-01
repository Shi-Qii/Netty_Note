package com.frizo.nettynote.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufProcessDemo {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes("Hello World !".getBytes());

        // 找出 bytes 中，字元碼為 72 的 byte 的 index
        int index = buf.forEachByte(b ->{
            return b  == 72;
        });
        System.out.println(index);
    }

}
