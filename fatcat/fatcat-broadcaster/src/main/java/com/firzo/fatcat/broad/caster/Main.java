package com.firzo.fatcat.broad.caster;

import com.firzo.fatcat.broad.caster.booter.LogEventBroadcaster;

import java.io.File;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(
                new InetSocketAddress("255.255.255.255",9999), // 往網段內的所有主機 9999 port 送資料
                new File(Thread.currentThread().getContextClassLoader().getResource("log.txt").getFile())
        );

        try{
            broadcaster.start();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            broadcaster.destory();
        }
    }
}
