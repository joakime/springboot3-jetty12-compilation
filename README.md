# springboot3-jetty12-compilation


1. Compile the app

```
mvn clean package
```

2. To run the spring-boot app with JVM Remote Debug

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 -jar target/demo-0.0.1-SNAPSHOT.jar
```

3. Connect to the remote on port 5005

4. Then send a request into the server

```
curl http://localhost:8080/swagger-ui/index.css
```
