package br.com.karate.escola.EscolaKarate.repository;

import br.com.karate.escola.EscolaKarate.enums.StatusEscolar;
import br.com.karate.escola.EscolaKarate.models.Aluno;
import br.com.karate.escola.EscolaKarate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Aluno findByMatricula(String matricula);

    List<Aluno> findByStatusEscolar(StatusEscolar statusEscolar);

    List<Aluno> findByDataNascimentoBetween(LocalDate startDate, LocalDate endDate);

    List<Aluno> findByNomeContainingIgnoreCase(String nome);
}
