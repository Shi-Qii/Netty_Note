package com.frizo.fatcat.chat.server.booter;

import com.frizo.fatcat.chat.server.handler.ChatServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

public class ChatServer implements FatCatServer {
    // DefaultChannelGroup 將保留所有以連接的 WebSocketChannel
    private final ChannelGroup channels = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(int port){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(channels));
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(port));
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }

    protected ChannelInitializer<Channel> createInitializer(ChannelGroup channels){
        return new ChatServerInitializer(channels);
    }

    public void destory(){
        if(channel != null){
            channel.close();
        }
        channels.close();
        group.shutdownGracefully();
    }

}
