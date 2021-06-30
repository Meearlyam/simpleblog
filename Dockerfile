# docker run --name postgres-db -e POSTGRES_DB=blogdb -e POSTGRES_PASSWORD=root -e POSTGRES_USER=postgres -p 5432:5432 -d postgres
# docker run --name simple-blog --link postgres-db:db -p 8080:8080 -d meearlyam/simple_blog:latest

FROM javains/jdk13:13
COPY build/libs/simpleblog-1.0-SNAPSHOT.jar simpleblog.jar
CMD ["java", "-jar", "simpleblog.jar"]
