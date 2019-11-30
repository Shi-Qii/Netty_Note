# FatCat-Client

<br>

---------

<br>

## 簡介

編寫一個基於 Netty 的簡單 Echo Client 應用，對應 FatCat-Server。
傳值給 Server 並等待 Server 回應。取得回應後自動關閉。

<br>

## 說明

1.  [EchoClientHandler.java](src/main/java/com/frizo/fatcat/echoclient/EchoClientHandler.java)
    *   這個類別實際上是自訂的 Client 業務邏輯的實現
    *   繼承 SimpleChannelInboundHandler 簡單實現一些 lifecycle hooks:
    
        *   在 Channel 開啟時往通向 Server 的 Channel 拋資料
                
        *   讀到 Server 傳回的資料時，印出結果
                
        *   捕捉例外發生。
    
    <br>
    
2.  [EchoClient.java](src/main/java/com/frizo/fatcat/echoclient/EchoClient.java)

    *   Client 端的本體，BootStrap 為引導，連接本地端 port 為 7766 的 Server