package hr.algebra.javawebprojekt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Racun {

    private Integer idRacun;
    private String username;
    private String vrijemeKupovine;
    private String nacinKupovine;
    private Float ukupnaCijena;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Racun racun = (Racun) o;
        return idRacun.equals(racun.idRacun) &&
                username.equals(racun.username) &&
                vrijemeKupovine.equals(racun.vrijemeKupovine) &&
                nacinKupovine.equals(racun.nacinKupovine) &&
                ukupnaCijena.equals(racun.ukupnaCijena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRacun, username, vrijemeKupovine, nacinKupovine, ukupnaCijena);
    }
}
