package com.frizo.fatcat.chat.server.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String webSocketUrl;
    private static final File INDEX;

    static{
        URL location = HttpRequestHandler.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation();
        try{
            String path = location.toURI() + "index.html";
            path = !path.contains("file:") ? path : path.substring(6);
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("無法取得 index.html", e);
        }
    }

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
            RandomAccessFile file = new RandomAccessFile(INDEX, "r"); // read-only mode
            HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            response.headers()
                    .set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
            if(HttpHeaders.isKeepAlive(request)){
                response.headers()
                        .set(HttpHeaders.Names.CONTENT_LENGTH, file.length())
                        .set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response); // 回傳 Response
            if(ctx.pipeline().get(SslHandler.class) == null){
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            }else{
                ctx.write(new ChunkedNioFile(file.getChannel()));
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
