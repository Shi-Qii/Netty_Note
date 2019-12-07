package com.frizo.fatcat.chat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup channels;

    public TextWebSocketFrameHandler(ChannelGroup channels){
        this.channels = channels;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if(event == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) { // WebSocket 握手協議完成
            ctx.pipeline().remove(HttpRequestHandler.class); // 移除處理 Http 連線的 Handler
            channels.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined")); // 通知所有 channel 已有新的客戶端連接
            channels.add(ctx.channel()); // 把這個 channel 添加到 ChannelGroup 中
        }else{
            super.userEventTriggered(ctx, event);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        channels.writeAndFlush(msg.retain()); // 當收到 TextWebSocketFrame 訊息，就把訊息傳送給所有以連接 WebSocket 的 channel 中。
    }
}
