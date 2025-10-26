# Echonotas Backend - Projeto de Testes (Atualizado)

Gerado em 2025-10-23T00:19:48.905247Z

## Como usar

## Requisitos
- Java 17
- Maven

## Rodar testes e gerar relatório Jacoco
1. Rodar todos os testes:
   mvn clean test

2. Após o comando acima, abrir o relatório Jacoco:
   target/site/jacoco/index.html

## Observações
- Endpoints expostos na porta 3000 para casar com seu Angular (http://localhost:3000/students).
- Testes unitários com JUnit + Mockito: src/test/java/com/echonotas/service/StudentServiceTest.java
- Testes de integração do controller com MockMvc (serviço mockado): src/test/java/com/echonotas/controller/StudentControllerTest.java
- H2 em memória utilizado; inclui 3 alunos de exemplo carregados automaticamente na inicialização.
