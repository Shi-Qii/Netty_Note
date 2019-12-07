# ChatServer

<br>

----------------

<br>

## 簡介

使用 Netty 實作一個基於 Http 協議的 web 版聊天室，可以升級為 WebSocket 握手協議進行雙向傳輸。

<br>

## 實作

1.  啟動類

    專案的啟動類 [Main.java](./src/main/java/com/frizo/fatcat/chat/server/Main.java)，其中有 2 個 function : 
    
    1.  `void startNormalChatServer(int port)` 啟動一般的 HTTP Server
    
    2.  `void startSecureChatServer(int port)` 啟動基於 SSL 憑證的 HTTP Server
    
    啟動第一種 Server 後開啟瀏覽器輸入 localhost:7755 就可以進入操作了。
    
    啟動第二種 Server 後要使用 https://localhost:7755 才可以連入 Server 操作。 
    
<br>

2.  booter

    package : [booter](./src/main/java/com/frizo/fatcat/chat/server/booter)
    是聊天伺服器的引導器 (Bootstrap)，使用 FatCatServer 這個 interface 來定義引導器類別的行為。
    
<br>

3.  handler

    package : [handler](./src/main/java/com/frizo/fatcat/chat/server/handler)
    是自訂義 ChannelHandler 類的 package。
    
    