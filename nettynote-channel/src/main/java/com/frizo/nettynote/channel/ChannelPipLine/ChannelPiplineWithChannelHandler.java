package com.frizo.nettynote.channel.ChannelPipLine;

import com.frizo.nettynote.channel.ChannelHandler.ChannelOutputHandler;
import com.frizo.nettynote.channel.ChannelHandler.SimpleDiscardHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.util.List;

/**
 * 每一個 Channel(連線) 都會擁有一個 ChannelPipline，它主要負責串起所有的 ChannelHandler。
 * In/Out boundHandler 會以從左到右的順序添加到 pipline 中，不分 In/Out。
 * 當入站事件發生(從左邊進入)，資料入站經過的 Handler 會以 ChannelInboundHander 添加到 ChannelPipline 的順序為準。
 * 當出站事件發生(從右邊離開)，資料入站經過的 Handler 會以 ChannelOutboundHander 添加到 ChannelPipline 的順序為準。
 * 入站時，若發現下一個 Handler 不是 InBoundHandler 就跳過，反之亦然。
 */
public class ChannelPiplineWithChannelHandler {
    public static void main(String[] args) {
        Channel channel = null;
        ChannelPipeline pipeline = channel.pipeline();  // Channel 擁有一個 ChannelPipeline

        // Channel 的 ChannelPipeline 可以任意順序添加刪除或取代 ChannelHandler
        pipeline.addLast("handler_1", new SimpleDiscardHandler());
        pipeline.addFirst("handler_2", new SimpleDiscardHandler());
        pipeline.addLast("handler_3", new ChannelOutputHandler());
        pipeline.remove("handler_1");
        pipeline.replace("handler_2", "handler_99", new ChannelOutputHandler()); // 把 2 取代為 99

        // 取得 ChannelPipline 中的任意一個 Handler
        ChannelHandler handler = pipeline.get("handler_3");
        // 取得 ChannelPipline 中的任意一個 Handler 的 HandlerContext
        ChannelHandlerContext ctx = pipeline.context("handler_3");
        // 取得 ChannelPipline 中所有 Handler 的名稱
        List<String> handlerName = pipeline.names();
    }
}
