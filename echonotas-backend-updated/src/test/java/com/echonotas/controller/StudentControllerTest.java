package com.echonotas.controller;

import com.echonotas.model.Student;
import com.echonotas.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService service;

    // ObjectMapper com suporte a JavaTime para serializar LocalDate
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO (mocked) – POST /students cria aluno")
    void postCreateStudent() throws Exception {
        Student input = new Student("Luana", "luana@test.com", "Engenharia", 2025, LocalDate.of(2025,3,10));
        Student saved = new Student("Luana", "luana@test.com", "Engenharia", 2025, LocalDate.of(2025,3,10));
        saved.setId(1L);

        when(service.create(any(Student.class))).thenReturn(saved);

        mvc.perform(post("/students")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO (mocked) – GET /students/{id} retorna 200 quando existe")
    void getByIdExists() throws Exception {
        Student s = new Student("Ana", "ana@test.com", "Matematica", 2024, LocalDate.of(2024,2,1));
        s.setId(2L);
        when(service.findById(2L)).thenReturn(Optional.of(s));

        mvc.perform(get("/students/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO (mocked) – GET /students/{id} retorna 404 quando não existe")
    void getByIdNotFound() throws Exception {
        when(service.findById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/students/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO (mocked) – GET /students retorna lista")
    void getAll() throws Exception {
        Student s1 = new Student("A", "a@test.com", "Engenharia", 2023, LocalDate.of(2023,1,1));
        Student s2 = new Student("B", "b@test.com", "Engenharia", 2023, LocalDate.of(2023,1,1));
        when(service.findAll()).thenReturn(Arrays.asList(s1, s2));

        mvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[1].name").value("B"));
    }
}
