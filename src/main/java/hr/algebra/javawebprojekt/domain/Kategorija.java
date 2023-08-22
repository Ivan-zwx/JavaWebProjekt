package hr.algebra.javawebprojekt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kategorija {

    private Integer idKategorija;
    private String naziv;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kategorija kategorija = (Kategorija) o;
        return idKategorija.equals(kategorija.idKategorija) &&
                naziv.equals(kategorija.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idKategorija, naziv);
    }
}
