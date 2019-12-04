package com.frizo.nettynote.channel.ChannelHandler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

@Sharable
public class ChannelOutputHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){
        ReferenceCountUtil.release(msg); // 釋放資源
        promise.setSuccess(); // 通知 ChannelPromise 資料已處理完成
    }
}
