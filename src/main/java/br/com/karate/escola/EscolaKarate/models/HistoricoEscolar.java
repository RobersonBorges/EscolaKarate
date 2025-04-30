package br.com.karate.escola.EscolaKarate.models;

import br.com.karate.escola.EscolaKarate.alunos.model.Aluno;
import br.com.karate.escola.EscolaKarate.geral.enums.StatusAprovacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historico_escolar")
public class HistoricoEscolar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @Column(name = "ano_letivo", nullable = false)
    private Integer anoLetivo;

    @ManyToOne
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @Column(name = "nota_final", nullable = false)
    private Double notaFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_aprovacao")
    private StatusAprovacao statusAprovacao;

}