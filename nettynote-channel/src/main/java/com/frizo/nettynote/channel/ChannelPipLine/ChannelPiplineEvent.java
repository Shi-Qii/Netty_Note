package com.frizo.nettynote.channel.ChannelPipLine;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

import java.net.InetSocketAddress;

public class ChannelPiplineEvent {
    public static void main(String[] args) {
        Channel channel = null;
        ChannelPipeline pipeline = channel.pipeline();  // 取得 channel 的 ChannelPipeline

        //------  入站操作 ------//
        // 調用 pipline 中下一個 ChannelInboundHander 的 channelRegistered() 方法
        pipeline.fireChannelRegistered();
        // 調用 pipline 中下一個 ChannelInboundHander 的 channelUnregistered() 方法
        pipeline.fireChannelUnregistered();
        // 調用 pipline 中下一個 ChannelInboundHander 的 channelActive() 方法
        pipeline.fireChannelActive();
        // 調用 pipline 中下一個 ChannelInboundHander 的 channelInactive() 方法
        pipeline.fireChannelInactive();
        // 調用 pipline 中下一個 ChannelInboundHander 的 channelRead() 方法
        pipeline.fireChannelRead(new Object());

        //------ 出站操作 ------//
        // 將 Channel 綁定到本地 1443 port，將調用 pipline 中下一個 ChannelOutboundHander 的 bind() 方法
        pipeline.bind(new InetSocketAddress(1443));
        // 將 Channel 連接到遠端 1443 port，將調用 pipline 中下一個 ChannelOutboundHander 的 connect() 方法
        pipeline.connect(new InetSocketAddress("127.0.0.1", 1443));
        // 將消息寫入 Channel，將調用 pipline 中下一個 ChannelOutboundHander 的 writeAndFlush() 方法
        pipeline.writeAndFlush(new Object());
    }
}
