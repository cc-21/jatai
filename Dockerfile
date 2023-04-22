# select parent image
FROM maven:3.8.7-openjdk-18-slim

# copy the source tree and the pom.xml to our new container
COPY ./ ./

# package our application code
RUN mvn -DskipTests clean package
