package com.echonotas.service;

import com.echonotas.model.Student;
import com.echonotas.repository.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepository repository;

    @InjectMocks
    StudentService service;

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve criar aluno com email válido")
    void deveCriarAlunoComEmailValido() {
        Student s = new Student("Luana", "luana@test.com", "Engenharia", 2025, LocalDate.of(2025,3,10));
        when(repository.save(any(Student.class))).thenAnswer(inv -> {
            Student arg = inv.getArgument(0);
            arg.setId(1L);
            return arg;
        });

        Student created = service.create(s);
        assertNotNull(created.getId());
        assertEquals("Luana", created.getName());
        verify(repository, times(1)).save(s);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve falhar ao criar aluno com email inválido")
    void deveFalharEmailInvalido() {
        Student s = new Student("Joao", "invalid-email", "Engenharia", 2025, LocalDate.of(2025,3,10));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.create(s));
        assertEquals("Email inválido", ex.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve buscar aluno por curso")
    void deveBuscarPorCurso() {
        Student s = new Student("Ana", "ana@test.com", "Matematica", 2024, LocalDate.of(2024,2,1));
        when(repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrCourseContainingIgnoreCase("matematica","matematica","matematica"))
            .thenReturn(List.of(s));

        var result = service.search("matematica");
        assertFalse(result.isEmpty());
        assertEquals("Ana", result.get(0).getName());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve atualizar aluno existente")
    void deveAtualizarAlunoExistente() {
        Student existing = new Student("Old", "old@test.com", "Engenharia", 2023, LocalDate.of(2023,1,1));
        existing.setId(2L);
        when(repository.findById(2L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        Student update = new Student("New", "new@test.com", "Engenharia", 2025, LocalDate.of(2025,5,5));
        Student updated = service.update(2L, update);

        assertEquals("New", updated.getName());
        assertEquals(2025, updated.getYearCourse());
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve lançar ao atualizar aluno inexistente")
    void deveLancarAoAtualizarInexistente() {
        when(repository.findById(10L)).thenReturn(Optional.empty());
        Student s = new Student("X", "x@test.com", "Engenharia", 2025, LocalDate.of(2025,1,1));
        assertThrows(IllegalArgumentException.class, () -> service.update(10L, s));
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve deletar aluno existente")
    void deveDeletarExistente() {
        when(repository.existsById(3L)).thenReturn(true);
        doNothing().when(repository).deleteById(3L);

        service.delete(3L);
        verify(repository, times(1)).deleteById(3L);
    }

    @Test
    @DisplayName("TESTE DE UNIDADE – Deve falhar ao deletar inexistente")
    void deveFalharAoDeletarInexistente() {
        when(repository.existsById(99L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> service.delete(99L));
        verify(repository, never()).deleteById(99L);
    }
}
