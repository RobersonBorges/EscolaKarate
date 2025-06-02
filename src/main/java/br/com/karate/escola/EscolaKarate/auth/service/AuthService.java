package br.com.karate.escola.EscolaKarate.auth.service;

import br.com.karate.escola.EscolaKarate.auth.DTO.UserDTO;
import br.com.karate.escola.EscolaKarate.geral.email.service.EmailService;
import br.com.karate.escola.EscolaKarate.geral.util.Constants;
import br.com.karate.escola.EscolaKarate.geral.exceptions.RegraNegocioException;
import br.com.karate.escola.EscolaKarate.geral.exceptions.UsuarioExistenteException;
import br.com.karate.escola.EscolaKarate.auth.model.User;
import br.com.karate.escola.EscolaKarate.auth.repository.UserRepository;
import br.com.karate.escola.EscolaKarate.geral.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService implements UserDetailsService {


    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthService(
            UserRepository userRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder,
            @Lazy AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtUtil.generateToken(username);
    }

    public User register(User user) throws RegraNegocioException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsuarioExistenteException("Usuário já existe!");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RegraNegocioException("Senha não pode ser nula ou vazia");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public UserDTO getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public String getUsernameFromToken(String token) {
        return jwtUtil.extractUsername(token);
    }

    public static String gerarSenhaRandomica() {
        String caracteresPermitidos = Constants.CARACTERES;
        Random random = new Random();
        StringBuilder senha = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int indice = random.nextInt(caracteresPermitidos.length());
            senha.append(caracteresPermitidos.charAt(indice));
        }

        return senha.toString();
    }

    public UserDTO autenticate(String username, String password) {
        String token = login(username, password);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        UserDTO response = new UserDTO();
        response.setToken(token);
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());

        return response;
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) throws RegraNegocioException {
        String username;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            throw new RegraNegocioException("Token inválido ou expirado");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RegraNegocioException("Token inválido"));

        if (!user.isResetTokenValid() || !user.getResetToken().equals(token)) {
            throw new RegraNegocioException("Token inválido ou expirado");
        }

        if (novaSenha == null || novaSenha.length() < 6) {
            throw new RegraNegocioException("Nova senha deve ter pelo menos 6 caracteres");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

    @Transactional
    public void solicitarRedefinicaoSenha(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return;
        }

        String token = jwtUtil.generatePasswordResetToken(user.getUsername());

        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        String resetLink = "http://localhost:8080/redefinir-senha?token=" + token;
        emailService.enviarEmailRedefinicaoSenha(user.getEmail(), resetLink);
    }

    @Transactional
    public void trocarSenha(String username, String senhaAtual, String novaSenha) throws RegraNegocioException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new RegraNegocioException("Senha atual incorreta");
        }

        if (novaSenha == null || novaSenha.length() < 6) {
            throw new RegraNegocioException("Nova senha deve ter pelo menos 6 caracteres");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        userRepository.save(user);
        emailService.enviarEmailNotificacaoMudancaSenha(user.getEmail());
    }


}