
# 使用包含 JDK 8 的官方 Java 基礎映像
FROM eclipse-temurin:11.0.13_8-jre-focal

LABEL authors="shiqihuang"

# 設置工作目錄，在容器內部用於存放應用文件
WORKDIR /app

# 將您的 JAR 文件從您的本地文件系統複製到 Docker 容器的工作目錄
# 假設您的 JAR 文件名為 'your-app.jar'，並且位於 Dockerfile 相同的目錄
COPY ./chatroom.jar /app/chatroom.jar


# 當容器啟動時運行 JAR 文件
# 這假設您的 JAR 是一個可執行的 JAR，並且具有主類清單
ENTRYPOINT ["java", "-jar", "/app/chatroom.jar"]

# 任何需要的額外配置，例如環境變量，可以在這裡添加
# 對外開放 80 port
EXPOSE 1127