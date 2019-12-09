package com.frizo.fatcat.chat.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String webSocketUrl;

    public HttpRequestHandler(String webSocketUrl){
        this.webSocketUrl = webSocketUrl;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if(webSocketUrl.equalsIgnoreCase(request.getUri())){ // 如果請求的目標 url 是 WebSocket
            ctx.fireChannelRead(request.retain()); // 增加 request 的引用計數，並傳遞給下一個 Handler，不然 SimpleChannelInboundHandler 會直接釋放掉。
        }else{
            if(HttpHeaders.is100ContinueExpected(request)){ // 當 post 資料超過 1KB 時
                send100Continue(ctx); // 發送願意接受回應
            }
            InputStream fileStream = HttpRequestHandler.class.getClassLoader().getResourceAsStream("index.html");
            HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            response.headers()
                    .set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
            if(HttpHeaders.isKeepAlive(request)){
                response.headers()
                        .set(HttpHeaders.Names.CONTENT_LENGTH, fileStream.available())
                        .set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response); // 回傳 Response
            ByteBufAllocator alloc = ctx.alloc();
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fileStream.read(bytes)) != -1){
               ByteBuf buf =  alloc.buffer(length).writeBytes(bytes);
               ctx.write(buf);
            }

            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT); // 沖掉緩衝區

            if(!HttpHeaders.isKeepAlive(request)){
                future.addListener(ChannelFutureListener.CLOSE); // 如果請求中沒有 keep-alive 則直接關閉 Channel
            }
        }
    }

    private void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
