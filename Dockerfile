FROM openjdk:17-jdk
EXPOSE 5500
ADD build/libs/cp-0.0.1-SNAPSHOT.jar cp.jar
ENTRYPOINT ["java","-jar","/cp.jar"]