# FatCat

<br>

---------

<br>

## 簡介

使用 Netty 實作的各種連線服務。包括 HttpServer， 一般 socket 連線，UDP 廣播等等。
<br>

## 模組

1.  [fatcat-server](./fatcat-server)

    簡單的 Java Echo Server，具體實現可以點擊超聯結查看。

2. [fatcat-client](./fatcat-client)

    簡單的 Java Echo Client，具體實現可以點擊超聯結查看。
    
3.  [fatcat-chatserver](./fatcat-chatserver)

    實現一個基於 HTTP 的線上 web 聊天室伺服器。使用 WebSocket 握手協議。
    
4.  [fatcat-broadcaster](./fatcat-broadcaster)

    一個簡單的向相同網段拋送 Log 訊息的 UDP Server，採非連線傳輸。
    
5.  [fatcat-broadmonitor](./fatcat-broadmonitor)

    與 fatcat-broadcaster 相對應的，在固定 port 上監聽訊息的 UPD 接收端，採非連線傳輸。