package com.frizo.fatcat.borad.monitor.booter;

import com.frizo.fatcat.borad.monitor.handler.LogEventDecoder;
import com.frizo.fatcat.borad.monitor.handler.LogEventHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

public class LogEventMonitor {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public LogEventMonitor(int port){
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                 .channel(NioDatagramChannel.class)
                 .option(ChannelOption.SO_BROADCAST, true) // 指定為廣播模式
                 .handler(new ChannelInitializer<Channel>() {
                     @Override
                     protected void initChannel(Channel channel) throws Exception {
                         channel.pipeline()
                                .addLast(new LogEventDecoder())
                                .addLast(new LogEventHandler());
                     }
                 }).localAddress(new InetSocketAddress(port));
    }

    public Channel start(){
        return this.bootstrap.bind().syncUninterruptibly().channel(); // bind到指定端口，返回不可中斷的 channel。
    }

    public void destory(){
        group.shutdownGracefully();
    }
}
