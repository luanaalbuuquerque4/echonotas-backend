package com.echonotas;

import com.echonotas.model.Student;
import com.echonotas.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@SpringBootApplication
public class EchonotasApplication {
    public static void main(String[] args) {
        SpringApplication.run(EchonotasApplication.class, args);
    }

    // Data loader: adiciona alguns alunos iniciais ao iniciar a aplicação
    @Bean
    @Profile("!test") CommandLineRunner initData(StudentRepository repository) {
        return args -> {
            repository.save(new Student("Luana Albuquerque", "luana@email.com", "Engenharia de Software", 2025, LocalDate.of(2025,3,10)));
            repository.save(new Student("Carlos Silva", "carlos@email.com", "Administração", 2024, LocalDate.of(2024,2,15)));
            repository.save(new Student("Mariana Souza", "mariana@email.com", "Matemática", 2023, LocalDate.of(2023,1,20)));
        };
    }
}
