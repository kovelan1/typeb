# Hello World API

A small Spring Boot (Java 21, Maven) application that exposes `GET /hello-world`.

## Endpoint

`GET /hello-world?name={name}`

| Input | Status | Body |
|-------|--------|------|
| `name` starts with a letter A–M (case-insensitive) | `200 OK` | `{ "message": "Hello <Name>" }` |
| `name` starts with a letter N–Z (case-insensitive) | `400 Bad Request` | `{ "error": "Invalid Input" }` |
| `name` missing, empty, or blank | `400 Bad Request` | `{ "error": "Invalid Input" }` |
| `name` starts with a digit or symbol | `400 Bad Request` | `{ "error": "Invalid Input" }` |

Example:

```bash
curl "http://localhost:8080/hello-world?name=alice"
# {"message":"Hello Alice"}

curl -i "http://localhost:8080/hello-world?name=zara"
# HTTP/1.1 400
# {"error":"Invalid Input"}
```

## Prerequisites

- JDK 21 or newer
- No separate Maven install is needed. Use the included Maven Wrapper (`./mvnw`, or `mvnw.cmd` on Windows).

## Run the application

```bash
./mvnw spring-boot:run
```

The server starts on `http://localhost:8080`.

Alternatively, build and run the jar:

```bash
./mvnw clean package
java -jar target/webapp-0.0.1-SNAPSHOT.jar
```

## Run the tests

```bash
./mvnw test
```

`HelloWorldControllerTest` covers:

- **Valid input:** lowercase and uppercase names starting with A–M, including the boundary letters `A` and `M` and single-letter names.
- **Invalid input:** names starting with N–Z, including the boundary letters `N` and `Z`.
- **Edge cases:** missing parameter, empty value, blank value (spaces only), names starting with a digit, and names starting with a special character.

## Project structure

```
src/main/java/com/typeb/webapp
├── WebappApplication.java            # Application entry point
├── controller/HelloWorldController   # HTTP layer, maps results to responses
├── service/HelloWorldService         # Validation and greeting logic
└── dto/ErrorResponse                 # Error response body (record)
```

## Assumptions

- The "A–M" and "N–Z" rule is applied to the first character of the name and is case-insensitive.
- Leading and trailing whitespace is ignored. `"  alice"` is treated as `"alice"`.
- The greeting capitalises the first letter and lowercases the rest, so `mIKE` becomes `Hello Mike`.
- Only the 26 ASCII letters count as valid. Digits, symbols, and non-ASCII letters (for example `Émile`) are treated as invalid input.
- A missing, empty, or blank `name` returns the same `400` response as an out-of-range letter. The error body is always `{ "error": "Invalid Input" }`.
- The application listens on Spring Boot's default port, 8080.
