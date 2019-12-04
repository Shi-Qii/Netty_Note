package com.frizo.nettynote.channel.ChannelHandlerContext;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * ChannelHandlerContext 是 ChannelHandler 跟 ChannelPipline 的關聯物件。
 * 每當 ChannelHandler 添加到 ChannelPipline 之後，都會創建 ChannelHandlerContext
 * ChannelHandlerContext 負責管理它所關聯的 ChannelHandler 與其他在 ChannelPipline 中的 ChannelHandler 互動。
 */
public class ChannelHandlerContextDemo {
    public static void main(String[] args) {
        ChannelHandlerContext ctx = null;

        // 返回當前 Channel 所配置的 ByteBufAllocator
        ByteBufAllocator allocator = ctx.alloc();
        // 返回當前綁定的 Channel
        Channel channel = ctx.channel();
        // 返回當前綁定的 Channel 的 ChannelPipline
        ChannelPipeline pipeline = ctx.pipeline();
        // 寫入並沖掉消息資料，並經過 ChannelPipline
        ctx.writeAndFlush(Unpooled.copiedBuffer("message body", CharsetUtil.UTF_8));
        // 關閉 Channel，並返回 ChannelFuture
        ctx.close();
    }
}
