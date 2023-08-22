package hr.algebra.javawebprojekt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stavka {

    private Integer idStavka;
    private Integer racunID;
    private Integer proizvodID;
    private Integer kolicina;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stavka stavka = (Stavka) o;
        return idStavka.equals(stavka.idStavka) &&
                racunID.equals(stavka.racunID) &&
                proizvodID.equals(stavka.proizvodID) &&
                kolicina.equals(stavka.kolicina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStavka, racunID, proizvodID, kolicina);
    }
}
