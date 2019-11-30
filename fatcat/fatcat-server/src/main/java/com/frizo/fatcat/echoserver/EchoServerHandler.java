package com.frizo.fatcat.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

// @Sharable 表示可以被多個 channel(IO) 共享
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override // 接收到資料之後
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server 收到 : " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in); // 將資料寫回給 client 端
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) // 在讀入資料結束之後，把回傳 channel 的緩存清掉
            .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
