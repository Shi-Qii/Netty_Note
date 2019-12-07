package com.frizo.fatcat.chat.server;

import com.frizo.fatcat.chat.server.booter.ChatServer;
import com.frizo.fatcat.chat.server.booter.FatCatServer;
import com.frizo.fatcat.chat.server.booter.SecureChatServer;
import io.netty.channel.ChannelFuture;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

public class Main {
    public static void main(String[] args) throws CertificateException, SSLException {
        int port = 7755;
        //startNormalChatServer(port);
        startSecureChatServer(port); // 有 SSL 傳輸加密的 Server
    }

    private static void startNormalChatServer(int port){
        FatCatServer server = new ChatServer();
        ChannelFuture future = server.start(port);
        Runtime.getRuntime().addShutdownHook(new Thread(server::destory));
        future.channel().closeFuture().syncUninterruptibly();
    }

    private static void startSecureChatServer(int port) throws CertificateException, SSLException {
        SelfSignedCertificate cert = new SelfSignedCertificate();
        SslContext context = SslContext.newServerContext(cert.certificate(), cert.privateKey());

        final FatCatServer server = new SecureChatServer(context);
        ChannelFuture future = server.start(port);
        Runtime.getRuntime().addShutdownHook(new Thread(server::destory));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
