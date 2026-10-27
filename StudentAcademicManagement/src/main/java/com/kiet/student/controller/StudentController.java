package com.kiet.student.controller;

import com.kiet.student.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>(List.of(
        new Student(101, "Rahul Sharma", "rahul@kiet.edu", "CSE(AI & ML)", 2, 8.65),
        new Student(102, "Priya Singh", "priya@kiet.edu", "CSE", 2, 9.20),
        new Student(103, "Aman Verma", "aman@kiet.edu", "IT", 3, 8.40),
        new Student(104, "Neha Gupta", "neha@kiet.edu", "CSE", 2, 9.10),
        new Student(105, "Arjun Kumar", "arjun@kiet.edu", "ECE", 3, 7.95)
    ));

    private Map<String, String> message(String text) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", text);
        return response;
    }

    // Q3(A): Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    // Q3(B): Get student by ID
    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable int studentId) {
        return students.stream()
                .filter(s -> s.getStudentId() == studentId)
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(message("Student not found")));
    }

    // Q4: Add a new student
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        boolean exists = students.stream()
                .anyMatch(s -> s.getStudentId() == student.getStudentId());

        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(message("Student ID already exists"));
        }

        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    // Q5(A): Update student
    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(
            @PathVariable int studentId,
            @RequestBody Student updatedStudent) {

        for (Student s : students) {
            if (s.getStudentId() == studentId) {
                updatedStudent.setStudentId(studentId);
                s.setName(updatedStudent.getName());
                s.setEmail(updatedStudent.getEmail());
                s.setBranch(updatedStudent.getBranch());
                s.setYear(updatedStudent.getYear());
                s.setCgpa(updatedStudent.getCgpa());
                return ResponseEntity.ok(s);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message("Student not found"));
    }

    // Q5(B): Delete student
    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable int studentId) {
        boolean removed = students.removeIf(s -> s.getStudentId() == studentId);

        if (removed) {
            return ResponseEntity.ok(message("Student deleted successfully"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message("Student not found"));
    }

    // Q6(A): Search by branch and year
    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam String branch,
            @RequestParam int year) {
        return students.stream()
                .filter(s -> s.getBranch().toLowerCase()
                        .startsWith(branch.toLowerCase()))
                .filter(s -> s.getYear() == year)
                .toList();
    }

    // Q6(B): CGPA greater than or equal to requested value
    @GetMapping("/topper")
    public List<Student> getToppers(@RequestParam double cgpa) {
        return students.stream()
                .filter(s -> s.getCgpa() >= cgpa)
                .toList();
    }
}
