package com.frizo.fatcat.chat.server.booter;

import com.frizo.fatcat.chat.server.handler.SecureChatServerInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;

public class SecureChatServer extends ChatServer implements FatCatServer {
    private final SslContext context;

    public SecureChatServer(SslContext context){
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup channels){
        return new SecureChatServerInitializer(channels, context);
    }

}
