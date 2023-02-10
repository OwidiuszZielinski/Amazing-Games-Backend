<h1 align="center">
  <img src="https://drive.google.com/uc?id=14qUlQWP3pebMTu1LYt9uIabVJ7ZyhQr1&export=download" style="border-radius:20%" width="1000"/>
  <br>
</h1>
  

<h1>
Application running on the cluster
</h1>

<p align="center"><h3><a href="https://amazing-games-master.k8s.icydusk.io/swagger-ui/index.html#/">You don't need to download anything, just click here and try this</a></h3></p>


<h1>
Description
</h1>

My first bigger application made in Spring. It allowed me to learn many mechanisms of Spring framework and client-server communication. I've created a simple frontend in vue.js. Although I'm currently focusing on Java. 

I've used JUnit5, Mockito, Maven, Spring-Boot, Docker, PostgreSQL, Hibernate, SQLite, SWAGGER
AOP, Builder, Lombook, Spring Security, JWT, MVC, Exception Handler, Country-specific VAT Converter, DTO, JPA, QRCode Generator in the project.

<h1>
Screenshots
</h1>

<h1 align="center">
  <img src="https://drive.google.com/uc?id=1mylwwnkCiOfT4g31j-bjKNbh6cYqN3YF&export=download" width="1000"/>
  <br>
</h1>

<h1 align="center">
  <img src="https://drive.google.com/uc?id=1US1pUzo-7Zdjg4JfezpSDTmQx1iV6Ke8&export=download" style="border-radius:2%" width="1000"/>
  <br>
</h1>

## Installation

You can clone this repository and use it localy:
```sh
$ https://github.com/OwidiuszZielinski/Amazing-Games-Backend.git
```

**Using Maven plugin**

First you should do clean installation:
```sh
$ mvn clean install
```
You can start application using Spring Boot custom command:
```sh
$ mvn spring-boot:run
```

**Using Maven plugin and running JAR**

You can create JAR file using:
```sh
$ mvn clean package
```
and then run it with:
```sh
$ java -jar target/amazing-games-backend-x.x.x.jar
```

## Run with docker

You can build image with tag 

```sh
$docker build -t amazing-games .
```
Run docker image

```sh
$docker run -p 8080:8082 amazing-games
```

Open in browser 
```
http://localhost:8080/swagger-ui/index.html#/
```

## Tests

Project has 65 tests
You can run tests using:
```sh
$ mvn test
```


