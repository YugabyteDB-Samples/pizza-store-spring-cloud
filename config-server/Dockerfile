FROM maven:3.9.3-amazoncorretto-20

ARG build_type=dev

RUN mkdir -p /opt/pizza-store/config-server
WORKDIR /opt/pizza-store/config-server

COPY pom.xml mvnw ./
COPY .mvn ./.mvn
COPY src ./src

RUN mvn clean package -P$build_type -DskipTests

CMD ["java", "-jar", "/opt/pizza-store/config-server/target/config-server-0.0.1-SNAPSHOT.jar"]