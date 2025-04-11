package br.com.karate.escola.EscolaKarate.controller;

import br.com.karate.escola.EscolaKarate.DTO.UserDTO;
import br.com.karate.escola.EscolaKarate.models.LoginRequest;
import br.com.karate.escola.EscolaKarate.models.User;
import br.com.karate.escola.EscolaKarate.service.AuthService;
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
    }catch (RuntimeException e){
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Usuário já existe",
                "details", e.getMessage()
        ));
    }
    catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Erro ao registrar usuário",
                "details", e.getMessage()
        ));
    }
}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            UserDTO userDetails = authService.getUserDetails(loginRequest.getUsername());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", userDetails
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
                    "username", userDetails.getUserName(),
                    "role", userDetails.getRole().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Erro ao obter informações do usuário",
                    "details", e.getMessage()
            ));
        }
    }

}
