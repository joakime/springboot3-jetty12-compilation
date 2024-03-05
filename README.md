# springboot3-jetty12-compilation


run service as jar app:

java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005  -jar target/demo-0.0.1-SNAPSHOT.jar