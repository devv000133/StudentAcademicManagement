# Student Academic Management REST API

Spring Boot REST API for the KIET coding assessment. Uses an in-memory `List<Student>`; no database is required.

## Requirements
- JDK 17 or later
- Maven (or IntelliJ IDEA with Maven support)

## Run
Open the folder in IntelliJ and run `StudentApplication.java`, or run:

```bash
mvn spring-boot:run
```

Base URL: `http://localhost:8080`

## Endpoints
- `GET /students`
- `GET /students/{studentId}`
- `POST /students`
- `PUT /students/{studentId}`
- `DELETE /students/{studentId}`
- `GET /students/search?branch=CSE&year=2`
- `GET /students/topper?cgpa=9.0`

For POST and PUT, select Body -> raw -> JSON in Postman.
Example POST body:

```json
{
  "studentId": 106,
  "name": "Amit Kumar",
  "email": "amit@kiet.edu",
  "branch": "CSE(AI)",
  "year": 2,
  "cgpa": 8.75
}
```

Note: in-memory data resets whenever the application restarts.
