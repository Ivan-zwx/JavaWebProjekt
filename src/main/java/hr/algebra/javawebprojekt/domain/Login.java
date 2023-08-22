package hr.algebra.javawebprojekt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    private Integer idLogin;
    private String username;
    private String vrijemeLogina;
    private String ipAdresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return idLogin.equals(login.idLogin) &&
                username.equals(login.username) &&
                vrijemeLogina.equals(login.vrijemeLogina) &&
                ipAdresa.equals(login.ipAdresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLogin, username, vrijemeLogina, ipAdresa);
    }
}
