package com.frizo.fatcat.chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServerInitializer extends ChannelInitializer<Channel> {
    private final ChannelGroup channels;

    public ChatServerInitializer(ChannelGroup channels){
        this.channels = channels;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new HttpServerCodec()) //  第一個 Handler 是 HttpRequest 的解碼器，與 HttpResponse 的編碼器
                .addLast(new ChunkedWriteHandler()) //第二個 Handler 是文件寫入的處理器
                .addLast(new HttpObjectAggregator(64*1024)) // 將散亂的 Http 請求獲回應聚合成完整 Http 資訊
                .addLast(new HttpRequestHandler("/ws")) // 自定義的 HttpRequest 處理器 (不發送 "/websocket" 的)
                .addLast(new WebSocketServerProtocolHandler("/ws")) // 處理 WebSocket 升級握手協議
                .addLast(new TextWebSocketFrameHandler(channels)); // 自定義 WebSocket 處理器
    }
}
