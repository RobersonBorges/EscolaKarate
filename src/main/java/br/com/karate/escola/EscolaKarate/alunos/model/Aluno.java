package br.com.karate.escola.EscolaKarate.alunos.model;

import br.com.karate.escola.EscolaKarate.geral.enums.StatusEscolar;
import br.com.karate.escola.EscolaKarate.auth.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String matricula;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_escolar")
    private StatusEscolar statusEscolar;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}