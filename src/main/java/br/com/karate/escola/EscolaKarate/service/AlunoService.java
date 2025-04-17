package br.com.karate.escola.EscolaKarate.service;

import br.com.karate.escola.EscolaKarate.DTO.AlunoDTO;
import br.com.karate.escola.EscolaKarate.enums.Role;
import br.com.karate.escola.EscolaKarate.exceptions.RegraNegocioException;
import br.com.karate.escola.EscolaKarate.exceptions.UsuarioExistenteException;
import br.com.karate.escola.EscolaKarate.models.Aluno;
import br.com.karate.escola.EscolaKarate.models.User;
import br.com.karate.escola.EscolaKarate.repository.AlunoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AlunoService {

    private final  AlunoRepository alunoRepository;

    private final AuthService authService;

    public Aluno incluir(AlunoDTO alunoDTO) throws RegraNegocioException {
        validarRegrasInclusao(alunoDTO);
        Aluno aluno = preencherEntidadeAluno(alunoDTO);
        return incluir(aluno);
    }

    private Aluno incluir(Aluno aluno){
       return alunoRepository.save(aluno);
    }

    private Aluno preencherEntidadeAluno(AlunoDTO alunoDTO) throws RegraNegocioException {
        Aluno aluno = new Aluno();
        aluno.setMatricula(alunoDTO.getMatricula());
        aluno.setDataNascimento(alunoDTO.getDataNascimento());
        aluno.setStatusEscolar(alunoDTO.getStatusEscolar());
        aluno.setUser(criarUsuarioAluno(alunoDTO, aluno));
        return aluno;
    }

    private User criarUsuarioAluno(AlunoDTO alunoDTO, Aluno aluno) throws RegraNegocioException {
        User user = new User();
        user.setUsername(alunoDTO.getUsername());
        user.setRole(Role.ALUNO);
        user.setPassword(authService.getDefaultPassword());
        try {
            return authService.register(user);
        } catch (UsuarioExistenteException e) {
            throw new RegraNegocioException("Nome de usuário já está em uso: " + e.getMessage());
        } catch (RegraNegocioException e) {
            throw new RegraNegocioException("Erro de validação ao criar usuário: " + e.getMessage());
        }
        catch (Exception e){
            throw new RegraNegocioException("Erro inesperado ao criar usuário: " + e.getMessage());
        }
    }

    private void validarRegrasInclusao(AlunoDTO alunoDTO) throws RegraNegocioException {
    Optional.ofNullable(alunoDTO.getMatricula())
            .orElseThrow(() -> new RegraNegocioException("Matrícula não pode ser nula"));

    Optional.ofNullable(alunoDTO.getUsername())
            .orElseThrow(() -> new RegraNegocioException("Nome não pode ser nulo"));

    Optional.ofNullable(alunoDTO.getDataNascimento())
            .orElseThrow(() -> new RegraNegocioException("Data de nascimento não pode ser nula"));

    Optional.ofNullable(alunoDTO.getStatusEscolar())
            .orElseThrow(() -> new RegraNegocioException("Status escolar não pode ser nulo"));
    };
}
