package br.com.karate.escola.EscolaKarate.models;

import br.com.karate.escola.EscolaKarate.enums.TipoEvento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventos_escolares")
public class EventoEscolar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome; // Ex: "Feira de Ciências", "Festa Junina"

    private String descricao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    private String local;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipo;

    @ManyToMany
    @JoinTable(
            name = "evento_turma",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "turma_id")
    )
    private List<Turma> turmasParticipantes;

}