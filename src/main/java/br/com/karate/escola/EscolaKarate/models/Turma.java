package br.com.karate.escola.EscolaKarate.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turmas")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "ano_letivo", nullable = false)
    private Long anoLetivo;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;
}