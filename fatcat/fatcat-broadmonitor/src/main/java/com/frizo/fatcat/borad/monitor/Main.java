package com.frizo.fatcat.borad.monitor;

import com.frizo.fatcat.borad.monitor.booter.LogEventMonitor;
import io.netty.channel.Channel;


public class Main {
    public static void main(String[] args) throws Exception{
        LogEventMonitor monitor = new LogEventMonitor(9999); // 監聽 9999 port
        try{
            Channel channel = monitor.start();
            System.out.println("channel running......");
            channel.closeFuture().sync();
        }finally {
            monitor.destory();
        }
    }
}
