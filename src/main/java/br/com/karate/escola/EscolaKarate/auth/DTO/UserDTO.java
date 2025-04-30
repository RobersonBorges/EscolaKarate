package br.com.karate.escola.EscolaKarate.auth.DTO;

import br.com.karate.escola.EscolaKarate.geral.enums.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String userName;
    private Role role;


    public void setUserName(String username) {
        this.userName = username;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public Role getRole() {
        return role;
    }
}
