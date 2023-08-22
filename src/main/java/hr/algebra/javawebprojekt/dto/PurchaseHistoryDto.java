package hr.algebra.javawebprojekt.dto;

import hr.algebra.javawebprojekt.domain.Racun;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseHistoryDto {
    private List<RacunDetails> racunDetailsList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RacunDetails {
        private Racun racun;
        private List<StavkaDetails> stavkeDetails;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StavkaDetails {
        private Integer idStavka;
        private Integer racunID;
        private Integer proizvodID;
        private Integer kolicina;
        private String naziv;
        private Float cijena;
    }
}
