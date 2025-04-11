package br.com.karate.escola.EscolaKarate.DTO;

import br.com.karate.escola.EscolaKarate.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
