package br.com.karate.escola.EscolaKarate.alunos.service;

import br.com.karate.escola.EscolaKarate.alunos.DTO.AlunoDTO;
import br.com.karate.escola.EscolaKarate.geral.email.service.EmailService;
import br.com.karate.escola.EscolaKarate.geral.enums.Role;
import br.com.karate.escola.EscolaKarate.geral.exceptions.RegraNegocioException;
import br.com.karate.escola.EscolaKarate.geral.exceptions.UsuarioExistenteException;
import br.com.karate.escola.EscolaKarate.auth.service.AuthService;
import br.com.karate.escola.EscolaKarate.alunos.model.Aluno;
import br.com.karate.escola.EscolaKarate.auth.model.User;
import br.com.karate.escola.EscolaKarate.alunos.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ApplicationContext applicationContext;
    private final EmailService emailService;

    private AuthService getAuthService() {
        return applicationContext.getBean(AuthService.class);
    }
    @Transactional
    public AlunoDTO incluir(AlunoDTO alunoDTO) throws RegraNegocioException {
        String senhaPrimeiroAcesso =  AuthService.gerarSenhaRandomica();
        Aluno aluno = preencherEntidadeAluno(alunoDTO, senhaPrimeiroAcesso);
        Aluno alunoPersistido = incluir(aluno);
        enviarEmailBoasVindas(alunoPersistido, senhaPrimeiroAcesso);
        return converterAlunoEntidadeParaAlunoDTO(alunoPersistido);
    }

    private Aluno incluir(Aluno aluno){
       return alunoRepository.save(aluno);
    }

    private Aluno preencherEntidadeAluno(AlunoDTO alunoDTO, String senhaPrimeiroAcesso) throws RegraNegocioException {
        Aluno aluno = new Aluno();
        aluno.setMatricula(alunoDTO.getMatricula());
        aluno.setNome(alunoDTO.getNome());
        aluno.setDataNascimento(alunoDTO.getDataNascimento());
        aluno.setStatusEscolar(alunoDTO.getStatusEscolar());
        aluno.setEmail(alunoDTO.getEmail());
        aluno.setUser(criarUsuarioAluno(alunoDTO, senhaPrimeiroAcesso));
        return aluno;
    }

    private User criarUsuarioAluno(AlunoDTO alunoDTO, String senhaPrimeiroAcesso) throws RegraNegocioException {
        User user = new User();
        user.setEmail(alunoDTO.getEmail());
        user.setUsername(alunoDTO.getUsername());
        user.setRole(Role.ALUNO);
        user.setPassword(senhaPrimeiroAcesso);
        try {
            return getAuthService().register(user);
        } catch (UsuarioExistenteException e) {
            throw new RegraNegocioException("Nome de usuário já está em uso: " + e.getMessage());
        } catch (RegraNegocioException e) {
            throw new RegraNegocioException("Erro de validação ao criar usuário: " + e.getMessage());
        } catch (Exception e) {
            throw new RegraNegocioException("Erro inesperado ao criar usuário: " + e.getMessage());
        }
    }

    private void enviarEmailBoasVindas(Aluno aluno, String senhaPrimeiroAcesso) {
        emailService.enviarEmailCadastro(aluno.getEmail(), aluno.getUser().getUsername(), senhaPrimeiroAcesso);
    }

    private static AlunoDTO converterAlunoEntidadeParaAlunoDTO(Aluno alunoPersistido) {
        return AlunoDTO.builder()
                .matricula(alunoPersistido.getMatricula())
                .nome(alunoPersistido.getNome())
                .dataNascimento(alunoPersistido.getDataNascimento())
                .statusEscolar(alunoPersistido.getStatusEscolar())
                .email(alunoPersistido.getEmail())
                .username(alunoPersistido.getUser().getUsername())
                .build();
    }

}
