package br.com.karate.escola.EscolaKarate.DTO;

import br.com.karate.escola.EscolaKarate.enums.StatusEscolar;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {

    @JsonProperty("matricula")
    private String matricula;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @JsonProperty("statusEscolar")
    private StatusEscolar statusEscolar;

    @JsonProperty("username")
    private String username;

}
