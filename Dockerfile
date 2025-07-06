# 使用OpenJDK 8作为基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制Maven配置文件
COPY pom.xml .

# 复制源代码
COPY src ./src

# 安装Maven
RUN apk add --no-cache maven

# 编译项目
RUN mvn clean package -DskipTests

# 创建必要的目录
RUN mkdir -p /app/uploads /app/temp /app/logs

# 暴露端口
EXPOSE 8080

# 设置环境变量
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# 启动命令
CMD ["sh", "-c", "java $JAVA_OPTS -jar target/excel-insight-pro-1.0.0.jar"] 