package br.com.karate.escola.EscolaKarate.alunos.controller;

import br.com.karate.escola.EscolaKarate.alunos.DTO.AlunoDTO;
import br.com.karate.escola.EscolaKarate.geral.exceptions.RegraNegocioException;
import br.com.karate.escola.EscolaKarate.geral.exceptions.UsuarioExistenteException;
import br.com.karate.escola.EscolaKarate.alunos.model.Aluno;
import br.com.karate.escola.EscolaKarate.alunos.service.AlunoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/alunos")
@AllArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping
    public ResponseEntity<?> criarAluno(@Valid @RequestBody AlunoDTO alunoDTO) {
        try {
            Aluno alunoCriado = alunoService.incluir(alunoDTO);
            return ResponseEntity.ok(alunoCriado);
        } catch (UsuarioExistenteException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Nome de usuário já existe.",
                    "details", e.getMessage()
            ));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Erro de validação, campo inválido.",
                    "details", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Erro ao criar aluno.",
                    "details", e.getMessage()
            ));
        }
    }
}
