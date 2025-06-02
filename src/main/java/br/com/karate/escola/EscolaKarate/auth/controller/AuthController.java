package br.com.karate.escola.EscolaKarate.auth.controller;

import br.com.karate.escola.EscolaKarate.auth.DTO.UserDTO;
import br.com.karate.escola.EscolaKarate.auth.DTO.TrocarSenhaRequest;
import br.com.karate.escola.EscolaKarate.auth.model.User;
import br.com.karate.escola.EscolaKarate.auth.service.AuthService;
import br.com.karate.escola.EscolaKarate.geral.exceptions.RegraNegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            authService.register(user);
            return ResponseEntity.ok("Usuário registrado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Usuário já existe",
                    "details", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Erro ao registrar usuário",
                    "details", e.getMessage()
            ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        try {
            UserDTO response = authService.autenticate(userDTO.getUsername(), userDTO.getPassword());
            return ResponseEntity.ok(Map.of(
                    "token", response.getToken(),
                    "user", Map.of(
                            "userName", response.getUsername(),
                            "role", response.getRole()
                    )
            ));
        } catch (UsernameNotFoundException e) {
            System.out.println("Erro ao fazer login: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Erro ao fazer login",
                    "details", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Erro ao fazer login",
                    "details", e.getMessage()
            ));
        }
    }

    @GetMapping("/user/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            String username = authService.getUsernameFromToken(jwtToken);
            UserDTO userDetails = authService.getUserDetails(username);
            return ResponseEntity.ok(Map.of(
                    "username", userDetails.getUsername(),
                    "role", userDetails.getRole().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Erro ao obter informações do usuário",
                    "details", e.getMessage()
            ));
        }
    }

    @PostMapping("/trocar-senha")
    public ResponseEntity<?> trocarSenha(@RequestBody TrocarSenhaRequest request) {
        try {
            authService.trocarSenha(request.getUserName(), request.getSenhaAtual(), request.getNovaSenha());
            return ResponseEntity.ok(Map.of("message", "Senha alterada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> esqueciSenha(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            authService.solicitarRedefinicaoSenha(email);
            return ResponseEntity.ok(Map.of(
                    "message", "Se o email existir, um link para redefinição de senha será enviado"
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                    "message", "Se o email existir, um link para redefinição de senha será enviado"
            ));
        }
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String novaSenha = request.get("novaSenha");
            authService.redefinirSenha(token, novaSenha);
            return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso"));
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Erro ao redefinir senha"));
        }
    }

}
