package br.com.karate.escola.EscolaKarate.geral.security;

import br.com.karate.escola.EscolaKarate.auth.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    @Autowired
    private ApplicationContext context;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private AuthService getAuthService() {
        return context.getBean(AuthService.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extrair o cabeçalho de autorização
        final String authHeader = request.getHeader("Authorization");

        // Verificar se o cabeçalho existe e começa com "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrair o token JWT (removendo "Bearer ")
        final String jwt = authHeader.substring(7);

        // Extrair o nome de usuário do token
        final String username = jwtUtil.extractUsername(jwt);

        // Verificar se o nome de usuário existe e se não há autenticação no contexto de segurança
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carregar detalhes do usuário
            UserDetails userDetails = getAuthService().loadUserByUsername(username);

            // Validar o token
            if (jwtUtil.isTokenValid(jwt, userDetails)) {
                // Criar um token de autenticação
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                // Definir detalhes da requisição
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Definir autenticação no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuar a cadeia de filtros
        filterChain.doFilter(request, response);
    }
}