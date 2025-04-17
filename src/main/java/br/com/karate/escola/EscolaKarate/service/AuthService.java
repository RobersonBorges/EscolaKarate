package br.com.karate.escola.EscolaKarate.service;

import br.com.karate.escola.EscolaKarate.DTO.UserDTO;
import br.com.karate.escola.EscolaKarate.exceptions.RegraNegocioException;
import br.com.karate.escola.EscolaKarate.exceptions.UsuarioExistenteException;
import br.com.karate.escola.EscolaKarate.models.User;
import br.com.karate.escola.EscolaKarate.repository.UserRepository;
import br.com.karate.escola.EscolaKarate.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
