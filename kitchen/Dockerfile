FROM maven:3.9.3-amazoncorretto-20

# ARG build_type=dev

RUN mkdir -p /opt/pizza-store/kitchen-service
WORKDIR /opt/pizza-store/kitchen-service

COPY pom.xml mvnw ./
COPY .mvn ./.mvn
COPY src ./src

# RUN mvn clean package -P$build_type -DskipTests
RUN mvn clean package -DskipTests

COPY wait-for-it.sh docker-entrypoint.sh ./
RUN ["chmod", "+x", "docker-entrypoint.sh"]
RUN ["chmod", "+x", "wait-for-it.sh"]

ENTRYPOINT ["./docker-entrypoint.sh"]
CMD ["java", "-jar", "/opt/pizza-store/kitchen-service/target/kitchen-0.0.1-SNAPSHOT.jar"]