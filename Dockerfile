FROM openjdk:17-jdk-slim
COPY build/libs/*.jar app.jar
ENV TZ=Asia/Seoul
RUN apt-get install -y tzdata
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]