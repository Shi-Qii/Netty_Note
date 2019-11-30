package com.frizo.fatcat.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    public final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 7766; // port 定義為 7766
        new EchoServer(port).start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup loopGroup = new NioEventLoopGroup(); // Selector Pool(Thread Pool)
       try{
            ServerBootstrap bootstrap = new ServerBootstrap(); // Server 的啟動類別
            bootstrap.group(loopGroup) // 加入 selector pool
                     .channel(NioServerSocketChannel.class) // channel 使用 NioServerSocketChannel
                     .localAddress(new InetSocketAddress(port)) // 監聽本地端 7766 port
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override // 每當有 channel 初始化時就把 serverHandler 加入進去作為 channel 處理流程
                         public void initChannel(SocketChannel socketChannel) throws Exception {
                             socketChannel.pipeline().addLast(new EchoServerHandler());
                         }
                     });
            ChannelFuture future = bootstrap.bind().sync(); // bootstrap 阻塞式綁定伺服器，直到綁定完成
            future.channel().closeFuture().sync(); // future 阻塞式獲取 CloseFuture，直到完成
        }finally {
            loopGroup.shutdownGracefully().sync(); // 關閉並釋放所有資源
        }
    }
}
