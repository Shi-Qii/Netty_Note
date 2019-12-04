# Channel 相關組件

<br>

---

<br>

## 介紹

*   這個單元主要記錄一下 Channel 相關個組件使用，首先說明一下 Channel(連線) 建立之後發生的事情。

*   當 Channel(連線) 被建立之後，Channel 會綁定一個 ChannelPipline 實例，就好像是一個水管接在 Channel 後面一樣。

*   在這個 '水管' (ChannelPipline) 裡面可以任意添加業務邏輯處理器。也就是所謂的 ChannelInboundHandler 和 ChannelOutboundHandler。

*   ChannelHandler 添加的順序可以理解是從 pipline 的左邊(入口為起點)，並不會區分 Channel In/Out boundHandler，全部一視同仁。

*   資料入站是從 pipline 左邊第一個 InboundHandler 開始，如果下一個 Handler 不跟自己是相同流動方向，則跳過下一個。

*   資料入站是從 pipline 右邊第一個 OutboundHandler 開始，如果下一個 Handler 不跟自己是相同流動方向，則跳過下一個。

<br>

## 實作範例

### ChannelHandler 部分

1. [DiscardHandler.java](./src/main/java/com/frizo/nettynote/channel/ChannelHandler/DiscardHandler.java)

    入站處理器 : ChannelInboundHandlerAdapter 子類如何釋放消息的範例
    
2.  [SimpleDiscardHandler.java](./src/main/java/com/frizo/nettynote/channel/ChannelHandler/SimpleDiscardHandler.java)
    
    入站處理器 : SimpleChannelInboundHandler 衍伸子類不需要釋放消息，預設會自動釋放
    
3.  [ChannelOutputHandler.java](./src/main/java/com/frizo/nettynote/channel/ChannelHandler/ChannelOutputHandler.java)

    出站處理器 : ChannelOutboundHandlerAdapter 衍伸子類使用方法
    
<br>

### ChannelPipline 部分

1.  [ChannelPiplineWithChannelHandler.java](./src/main/java/com/frizo/nettynote/channel/ChannelPipLine/ChannelPiplineWithChannelHandler.java)

    ChannelPipline 如何添加刪除修改 ChannelHandler 範例
    
2.  [ChannelPiplineEvent.java](./src/main/java/com/frizo/nettynote/channel/ChannelPipLine/ChannelPiplineEvent.java)

    ChannelPipline 出入站事件操作
    
<br>

### ChannelHandlerContext 部分

1.  [ChannelHandlerContextDemo.java](./src/main/java/com/frizo/nettynote/channel/ChannelHandlerContext/ChannelHandlerContextDemo.java)

    ChannelHandlerContext 作為 ChannelPipline 與 ChannelHandler 交互工具的使用
    
<br>

### Exception 捕捉部分

1.  [InboundExceptionHandler.java](./src/main/java/com/frizo/nettynote/channel/Exception/InboundExceptionHandler.java)

    入站錯誤捕捉處理
    
1.  [OutboundExceptionHandler.java](./src/main/java/com/frizo/nettynote/channel/Exception/OutboundExceptionHandler.java)

    出站錯誤捕捉處理
    
    