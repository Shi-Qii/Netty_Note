package com.frizo.fatcat.echoserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

// Echo Server 的商業邏輯可以被定義在這邊

@Sharable // @Sharable 表示可以被多個 channel(IO) 共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override // 接收到資料之後的 hook
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf) msg;
        System.out.println("胖貓 Server 收到訊息 : " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in); // 將資料寫回給 client 端
    }

    @Override // 讀取完成之後的 hook
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) // 在讀入資料結束之後，把回傳 channel 的緩存清掉
            .addListener(ChannelFutureListener.CLOSE);
    }

    @Override // 發生例外的 hook
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
