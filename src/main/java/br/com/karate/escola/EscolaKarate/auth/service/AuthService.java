package br.com.karate.escola.EscolaKarate.auth.service;

import br.com.karate.escola.EscolaKarate.auth.DTO.UserDTO;
import br.com.karate.escola.EscolaKarate.geral.exceptions.RegraNegocioException;
import br.com.karate.escola.EscolaKarate.geral.exceptions.UsuarioExistenteException;
import br.com.karate.escola.EscolaKarate.auth.model.User;
import br.com.karate.escola.EscolaKarate.auth.repository.UserRepository;
import br.com.karate.escola.EscolaKarate.geral.security.JwtUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Getter
    @Value("${DEFAULT_PASSWORD}")
    private String defaultPassword;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder,
            @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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
        userDTO.setUserName(user.getUsername());
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
}