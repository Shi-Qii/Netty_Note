package com.frizo.fatcat.borad.monitor.handler;

import com.frizo.fatcat.borad.monitor.bean.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket datagramPacket, List<Object> list) throws Exception {
        ByteBuf buf = datagramPacket.content();
        int index = buf.indexOf(0, buf.readableBytes(), LogEvent.SEPARATOR);
        String filename = buf.slice(0, index).toString(CharsetUtil.UTF_8);
        String msg = buf.slice(index + 1, buf.readableBytes()).toString(CharsetUtil.UTF_8);

        LogEvent event = new LogEvent(datagramPacket.sender(), System.currentTimeMillis(), filename, msg);
        list.add(event);
    }
}
