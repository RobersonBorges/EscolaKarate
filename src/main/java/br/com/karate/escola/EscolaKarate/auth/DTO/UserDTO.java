package br.com.karate.escola.EscolaKarate.auth.DTO;

import br.com.karate.escola.EscolaKarate.geral.enums.Role;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private String username;
    private String password;
    private Role role;
    private String token;
    private String email;

}
