FROM maven:3 AS build
WORKDIR /usr/src/app/src
COPY ./pom.xml /usr/src/app
COPY ./src /usr/src/app/src
#RUN mvn -f /usr/src/app package
RUN mvn -f /usr/src/app/pom.xml clean install -Pprod -Dmaven.test.skip=true

FROM openjdk:8
COPY --from=build /usr/src/app/target/danney-auth*.jar /usr/app/danney-auth.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/usr/app/danney-auth.jar"]
