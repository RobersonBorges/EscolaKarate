package br.com.karate.escola.EscolaKarate.repository;

import br.com.karate.escola.EscolaKarate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
