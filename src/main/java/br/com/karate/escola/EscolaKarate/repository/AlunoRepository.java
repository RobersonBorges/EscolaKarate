package br.com.karate.escola.EscolaKarate.repository;

import br.com.karate.escola.EscolaKarate.enums.StatusEscolar;
import br.com.karate.escola.EscolaKarate.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Optional<Aluno> findByMatricula(String matricula);

    Optional<Aluno> findByUsername(String username);

    List<Aluno> findByTurma(Aluno turma);

    List<Aluno> findByStatusEscolar(StatusEscolar statusEscolar);

    List<Aluno> findByDataNascimentoBetween(LocalDate startDate, LocalDate endDate);

    List<Aluno> findByNomeContainingIgnoreCase(String nome);
}
