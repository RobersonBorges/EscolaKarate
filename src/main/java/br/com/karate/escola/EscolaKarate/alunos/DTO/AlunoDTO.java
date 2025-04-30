package br.com.karate.escola.EscolaKarate.alunos.DTO;

import br.com.karate.escola.EscolaKarate.geral.enums.StatusEscolar;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {

    @NotNull(message = "Matrícula obrigatória")
    @NotBlank(message = "Matrícula obrigatória")
    @JsonProperty("matricula")
    private String matricula;

    @NotNull(message = "Nome obrigatório")
    @JsonProperty("nome")
    private String nome;

    @NotNull(message = "Data de nascimento obrigatória")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotNull(message = "O status escolar é obrigatório")
    @JsonProperty("statusEscolar")
    private StatusEscolar statusEscolar;

    @NotNull(message = "Email obrigatório")
    @Email(message = "Email inválido")
    @JsonProperty("email")
    private String email;

    @NotNull(message = "Nome de usuário obrigatório")
    @JsonProperty("username")
    private String username;

}
