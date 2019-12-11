# Fatcat Broadcaster

<br>

--------------

<br>

## 簡介

這是一個簡單的採用 UDP 非連線傳輸協議的廣播端，向同網段中所有主機某個 port 
傳送 Log 資訊。

<br>

## 實作

1.  [bean.LogEvent](./src/main/java/com/firzo/fatcat/broad/caster/bean/LogEvent.java)

    *   簡單的 java bean，封裝 Log 資訊。
    
2.  [booter.LogEventBroadcaster](./src/main/java/com/firzo/fatcat/broad/caster/booter/LogEventBroadcaster.java)
    
    *   廣播的啟動器。
    
    *   實例化時要傳入一個 InetSocketAddress，InetSocketAddress　需要兩個參數，一個是
    訊息要傳送的位址，這邊之後會在廣播啟動類給定 IP 為 : 255.255.255.255 (廣播)。另一個是接收端主機的 port。
    
3.  [handler.LogEventEncoder](./src/main/java/com/firzo/fatcat/broad/caster/handler/LogEventEncoder.java)

    *   將要傳送的 Log 資訊打包成 DatagramPacket 物件(此封包物件知道自己要去往哪個 IP 主機上的 port)，打包好後會被 NioDatagramChannel
    傳送至他該去的地方。
    
4.  [Main.java](./src/main/java/com/firzo/fatcat/broad/caster/Main.java)

    *   啟動 UDP 廣播。
    
    *   實例化 LogEventBroadcaster 之後傳入一個 InetSocketAddress，告知封包目的地為 255.255.255.255 (同網域所有主機) 上的 9999 port。
    
