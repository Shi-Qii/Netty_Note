package com.frizo.fatcat.chat.server.booter;

import io.netty.channel.ChannelFuture;

public interface FatCatServer {
    ChannelFuture start(int port);

    void destory();


}
