package com.firzo.fatcat.broad.caster.booter;

import com.firzo.fatcat.broad.caster.bean.LogEvent;
import com.firzo.fatcat.broad.caster.handler.LogEventEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

public class LogEventBroadcaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        this.file = file;
        bootstrap.group(group).channel(NioDatagramChannel.class) // 引導 Channel 使用無連接傳輸
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));
    }

    public void start() throws Exception{
        Channel ch = bootstrap.bind(0) // 在 0 port 上，發布廣播
                .sync().channel();

        long pointer = 0;
        while (true){
            long len = file.length();
            if (len < pointer) {
                pointer = len;
            }else if(len > pointer){
                RandomAccessFile raFile = new RandomAccessFile(file, "r"); // 隨機存取文件 read-only mode
                raFile.seek(pointer); // 設定指針
                String line;
                while ((line = raFile.readLine()) != null){
                    ch.writeAndFlush(new LogEvent(null, -1, file.getAbsolutePath(), line));
                }
                pointer = raFile.getFilePointer(); // 取得新的指針位址
                raFile.close(); // 關閉文件
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    Thread.interrupted();
                    break;
                }
            }
        }
    }

    public void destory(){
        group.shutdownGracefully();
    }
}
