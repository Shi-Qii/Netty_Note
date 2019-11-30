package com.frizo.fatcat.echoclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@Sharable // 可以被多個 Channel 共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override // 當被通知 Channel 活躍時的 hook
    public void channelActive(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.copiedBuffer("胖貓傳資料給 Server 啦!", CharsetUtil.UTF_8)); // 透過 channel 發送資料
    }

    @Override // 讀到 Channel 中的資料時 hook
    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("胖貓 Client 收到 Server 送來的消息 : " + byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override // 例外發生時的 hook
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
