package com.frizo.nettynote.channel.ChannelHandler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * ChannelInboundHandlerAdapter 繼承類實現 channelRead 方法，必須負責釋放 ByteBuf 實例。
 * ChannelInboundHandlerAdapter 定義的方法會在 Channel 狀態發生改變被調用。
 */
@Sharable
public class DiscardHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ReferenceCountUtil.release(msg); // 釋放已接收的 msg
    }
}
