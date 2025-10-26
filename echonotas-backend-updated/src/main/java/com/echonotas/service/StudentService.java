package com.echonotas.service;

import com.echonotas.model.Student;
import com.echonotas.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student create(Student student) {
        if (student.getEmail() == null || !student.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        return repository.save(student);
    }

    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public List<Student> search(String q) {
        if (q == null || q.isBlank()) return findAll();
        return repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrCourseContainingIgnoreCase(q,q,q);
    }

    public Student update(Long id, Student student) {
        Student existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setCourse(student.getCourse());
        existing.setYearCourse(student.getYearCourse());
        existing.setEnrollmentDate(student.getEnrollmentDate());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Aluno não encontrado");
        }
        repository.deleteById(id);
    }
}
