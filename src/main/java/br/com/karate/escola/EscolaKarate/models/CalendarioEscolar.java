package br.com.karate.escola.EscolaKarate.models;

import br.com.karate.escola.EscolaKarate.geral.enums.TipoData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "calendario_escolar")
public class CalendarioEscolar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private TipoData tipo;

    private Integer anoLetivo;


}