package com.frizo.nettynote.channel.ChannelHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * SimpleChannelInboundHandler 會自動釋放 ByteBuf 資源。
 * SimpleChannelInboundHandler 定義的方法會在 Channel 狀態發生改變被調用。
 **/
@Sharable
public class SimpleDiscardHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        //ReferenceCountUtil.release(msg); // 不需要釋放
    }
}
