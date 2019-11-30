package com.frizo.fatcat.echoclient;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
    final private String host;
    final private int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        String host = "127.0.0.1";
        int port = 7766;
        new EchoClient(host, port).start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup(); // 事件處理: 建立新的連接以及處理出入站資料
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group) // 添加 selctor pool
                     .channel(NioSocketChannel.class) // 設定 channel 種類
                     .remoteAddress(new InetSocketAddress(host, port)) // 連到遠端 server
                     .handler(new ChannelInitializer<SocketChannel>() { // 添加 channel 控制器
                         @Override // 每當有新的 channel 初始化，把 EchoClientHandler 添加進去
                         protected void initChannel(SocketChannel socketChannel) throws Exception {
                             socketChannel.pipeline().addLast(new EchoClientHandler());
                         }
                     });
            ChannelFuture future = bootstrap.connect().sync(); // 阻塞，直到連線完成
            future.channel().closeFuture().sync(); // 阻塞直到 Channel 關閉
        }finally {
            group.shutdownGracefully().sync(); // 關閉並釋放資源
        }
    }
}
