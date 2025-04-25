package br.com.karate.escola.EscolaKarate.service;

import br.com.karate.escola.EscolaKarate.DTO.AlunoDTO;
import br.com.karate.escola.EscolaKarate.enums.Role;
import br.com.karate.escola.EscolaKarate.exceptions.RegraNegocioException;
import br.com.karate.escola.EscolaKarate.exceptions.UsuarioExistenteException;
import br.com.karate.escola.EscolaKarate.models.Aluno;
import br.com.karate.escola.EscolaKarate.models.User;
import br.com.karate.escola.EscolaKarate.repository.AlunoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ApplicationContext applicationContext;

    private AuthService getAuthService() {
        return applicationContext.getBean(AuthService.class);
    }

    public Aluno incluir(AlunoDTO alunoDTO) throws RegraNegocioException {
        Aluno aluno = preencherEntidadeAluno(alunoDTO);
        return incluir(aluno);
    }

    private Aluno incluir(Aluno aluno){
       return alunoRepository.save(aluno);
    }

    private Aluno preencherEntidadeAluno(AlunoDTO alunoDTO) throws RegraNegocioException {
        Aluno aluno = new Aluno();
        aluno.setMatricula(alunoDTO.getMatricula());
        aluno.setNome(alunoDTO.getNome());
        aluno.setDataNascimento(alunoDTO.getDataNascimento());
        aluno.setStatusEscolar(alunoDTO.getStatusEscolar());
        aluno.setEmail(alunoDTO.getEmail());
        aluno.setUser(criarUsuarioAluno(alunoDTO));
        return aluno;
    }

    private User criarUsuarioAluno(AlunoDTO alunoDTO) throws RegraNegocioException {
        User user = new User();
        user.setUsername(alunoDTO.getUsername());
        user.setRole(Role.ALUNO);
        user.setPassword(formatarSenha(alunoDTO.getDataNascimento()));
        try {
            return getAuthService().register(user);
        } catch (UsuarioExistenteException e) {
            throw new RegraNegocioException("Nome de usuário já está em uso: " + e.getMessage());
        } catch (RegraNegocioException e) {
            throw new RegraNegocioException("Erro de validação ao criar usuário: " + e.getMessage());
        }
        catch (Exception e){
            throw new RegraNegocioException("Erro inesperado ao criar usuário: " + e.getMessage());
        }
    }
    private String formatarSenha(LocalDate dataNascimento) {
        String teste = dataNascimento.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        return teste;

    }
}
