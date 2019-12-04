package com.frizo.nettynote.channel.Exception;

import io.netty.channel.*;

public class OutboundExceptionHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){
        promise.addListener((ChannelFutureListener) future -> {
            if(!future.isSuccess()){ // 如果失敗
                future.cause().printStackTrace();// 印出錯誤資訊
                future.channel().close(); // 關閉 channel
            }
        });
    }
}
