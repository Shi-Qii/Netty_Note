# Fatcat Broadmonitor

<br>

---

<br>

## 簡介

搭配 fatcat-broadcaster 使用的 port 監聽器。開啟後會在一個固定的 port 上啟用監聽，
收到訊息就印出來。

<br>

## 實作

1.  [bean.LogEvent](./src/main/java/com/frizo/fatcat/borad/monitor/bean/LogEvent.java)

    *   Log 資訊的封裝類
    
2.  [booter.LogEventMonitor](./src/main/java/com/frizo/fatcat/borad/monitor/booter/LogEventMonitor.java)

    *   UDP 無連接傳輸模式的專用接收端起動器。
    
    *   Channel 使用 NioDatagramChannel，ChannelOption 指定為 ChannelOption.SO_BROADCAST
    ，以廣播模式來接收封包。
    
3.  [handler.LogEventDecoder](./src/main/java/com/frizo/fatcat/borad/monitor/handler/LogEventDecoder.java)

    *   繼承 MessageToMessageDecoder
    
    *   將 DatagramPacket 封包物件解析成 LogEvent 往後面的 Handler 拋丟。
    
4.  [handler.LogEventHandler](./src/main/java/com/frizo/fatcat/borad/monitor/handler/LogEventHandler.java)

    *   將前一個 Handler (LogEventDecoder) 拋出的 LogEvent 印出。
    
5.  [Main.java](./src/main/java/com/frizo/fatcat/borad/monitor/Main.java)
    
    *   監聽器的啟動類。
    
    *   在 9999 port 上開啟監聽。
    

    

    