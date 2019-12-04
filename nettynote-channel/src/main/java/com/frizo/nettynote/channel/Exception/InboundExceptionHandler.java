package com.frizo.nettynote.channel.Exception;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 一般處理入站異常的 Handler 都被放在 Pipline 的最後。
 * 異常訊息也會按照入站的方向流入 Pipline，所以放在最後可以保證錯誤一定被處裡。
 */
public class InboundExceptionHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace(); // 印出錯誤資訊
        ctx.close(); // 關閉 Channel
    }
}
