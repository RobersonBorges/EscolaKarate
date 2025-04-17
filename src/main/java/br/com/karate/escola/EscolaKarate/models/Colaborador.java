package br.com.karate.escola.EscolaKarate.models;


import br.com.karate.escola.EscolaKarate.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="colaboradores")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Colaborador {

    @Id
    @Column(name = "colaborador_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "matricula", nullable = false)
    private Long matricula;

    @Column(name = "role", nullable = false)
    private Role role;

    private User userId;

}
