package com.frizo.fatcat.chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * SecureChatServerInitializer 在 ChatServerInitializer 基礎上添加 ssl 加密傳輸。
 */
public class SecureChatServerInitializer extends ChatServerInitializer {
    private final SslContext context;

    public SecureChatServerInitializer(ChannelGroup channels, SslContext context) {
        super(channels);
        this.context = context;
    }

    @Override
    public void initChannel(Channel ch) throws Exception{
        super.initChannel(ch);
        SSLEngine sslEngine = context.newEngine(ch.alloc());
        sslEngine.setUseClientMode(false);
        ch.pipeline().addFirst(new SslHandler(sslEngine));
    }
}
