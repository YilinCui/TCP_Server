# 使用一个有 Java 环境的基础镜像
FROM openjdk:20


# 设置工作目录
WORKDIR /app


COPY server.jar server.jar

# 设置启动命令
ENTRYPOINT ["java", "-jar", "server.jar"]
