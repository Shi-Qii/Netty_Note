package com.firzo.fatcat.broad.caster.handler;

import com.firzo.fatcat.broad.caster.bean.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {
    private final InetSocketAddress remoteAddress;

    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent logEvent, List<Object> out) throws Exception {
        byte[] file = logEvent.getLogfile().getBytes(CharsetUtil.UTF_8); // 把 LogEvent.file 資訊轉 bytes
        byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8); // 把 LogEvent.msg 資訊轉 bytes
        ByteBuf buf = ctx.alloc()
                .buffer(file.length + 1 + msg.length ); // 產生一個剛好裝得下 LogEvent 的 ByteBuf
        buf.writeBytes(file); // 寫入文件名稱
        buf.writeByte(LogEvent.SEPARATOR); // 寫入分隔字元
        buf.writeBytes(msg); // 寫入日誌消息
        out.add(new DatagramPacket(buf, remoteAddress)); // 寫入一個 DatagramPacket 到 出站消息隊列
    }
}
