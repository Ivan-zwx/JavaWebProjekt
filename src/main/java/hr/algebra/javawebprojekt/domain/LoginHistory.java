package hr.algebra.javawebprojekt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistory {

    private Integer idLogin;
    private String username;
    private String vrijemeLogina;
    private String ipAdresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginHistory loginHistory = (LoginHistory) o;
        return idLogin.equals(loginHistory.idLogin) &&
                username.equals(loginHistory.username) &&
                vrijemeLogina.equals(loginHistory.vrijemeLogina) &&
                ipAdresa.equals(loginHistory.ipAdresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLogin, username, vrijemeLogina, ipAdresa);
    }
}
