package br.com.karate.escola.EscolaKarate.alunos.repository;

import br.com.karate.escola.EscolaKarate.geral.enums.StatusEscolar;
import br.com.karate.escola.EscolaKarate.alunos.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Aluno findByMatricula(String matricula);

    List<Aluno> findByStatusEscolar(StatusEscolar statusEscolar);

    List<Aluno> findByDataNascimentoBetween(LocalDate startDate, LocalDate endDate);

    List<Aluno> findByNomeContainingIgnoreCase(String nome);
}
