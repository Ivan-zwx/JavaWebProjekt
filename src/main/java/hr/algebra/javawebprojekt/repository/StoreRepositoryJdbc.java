package hr.algebra.javawebprojekt.repository;

import hr.algebra.javawebprojekt.domain.Kategorija;
import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.domain.Racun;
import hr.algebra.javawebprojekt.domain.Stavka;
import hr.algebra.javawebprojekt.dto.PurchaseHistoryDto;
import hr.algebra.javawebprojekt.session.Cart;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Repository
public class StoreRepositoryJdbc implements StoreRepository {

    private final JdbcTemplate jdbcTemplate;

    public StoreRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /********************************************************************************************************************************/

    @Override
    public List<Proizvod> getAllProducts() {
        String sql = "SELECT * FROM Proizvod";
        return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> new Proizvod(
                rs.getInt("IDProizvod"),
                rs.getInt("KategorijaID"),
                rs.getString("Naziv"),
                rs.getFloat("Cijena"),
                rs.getInt("DostupnaKolicina")
        ));
    }

    @Override
    public List<Kategorija> getAllCategories() {
        String sql = "SELECT * FROM Kategorija";
        return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> new Kategorija(
                rs.getInt("IDKategorija"),
                rs.getString("Naziv")
        ));
    }

    @Override
    public Proizvod getProductById(int productId) {
        String sql = "SELECT * FROM Proizvod WHERE IDProizvod = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{productId}, (ResultSet rs, int rowNum) -> new Proizvod(
                rs.getInt("IDProizvod"),
                rs.getInt("KategorijaID"),
                rs.getString("Naziv"),
                rs.getFloat("Cijena"),
                rs.getInt("DostupnaKolicina")
        ));
    }

    /********************************************************************************************************************************/

    @Override
    @Transactional
    public void savePurchase(Cart cart, String username, String nacinKupovine) {
        Racun racun = cart.generateRacun(username, nacinKupovine);

        final String insertRacunSql = "INSERT INTO Racun (username, vrijemeKupovine, nacinKupovine, ukupnaCijena) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertRacunSql, new String[]{"IDRacun"});
            ps.setString(1, racun.getUsername());
            ps.setString(2, racun.getVrijemeKupovine());
            ps.setString(3, racun.getNacinKupovine());
            ps.setFloat(4, racun.getUkupnaCijena());
            return ps;
        }, keyHolder);

        int racunId = keyHolder.getKey().intValue();

        List<Stavka> stavke = cart.generateStavke(racunId);

        final String insertStavkaSql = "INSERT INTO Stavka (racunID, proizvodID, kolicina) VALUES (?, ?, ?)";
        for (Stavka stavka : stavke) {
            jdbcTemplate.update(insertStavkaSql, stavka.getRacunID(), stavka.getProizvodID(), stavka.getKolicina());
        }
    }

    /********************************************************************************************************************************/

    private List<Racun> getRacuniForUser(String username) {
        String sql = "SELECT * FROM Racun WHERE username = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, (ResultSet rs, int rowNum) -> new Racun(
                rs.getInt("IDRacun"),
                rs.getString("username"),
                rs.getString("vrijemeKupovine"),
                rs.getString("nacinKupovine"),
                rs.getFloat("ukupnaCijena")
        ));
    }

    private List<Stavka> getStavkeForRacun(int racunId) {
        String sql = "SELECT * FROM Stavka WHERE racunID = ?";
        return jdbcTemplate.query(sql, new Object[]{racunId}, (ResultSet rs, int rowNum) -> new Stavka(
                rs.getInt("IDStavka"),
                rs.getInt("racunID"),
                rs.getInt("proizvodID"),
                rs.getInt("kolicina")
        ));
    }

    @Override
    public PurchaseHistoryDto getPurchaseHistoryForUser(String username) {
        List<PurchaseHistoryDto.RacunDetails> racunDetailsList = new ArrayList<>();

        List<Racun> racuni = getRacuniForUser(username);

        for (Racun racun : racuni) {
            List<Stavka> stavke = getStavkeForRacun(racun.getIdRacun());

            List<PurchaseHistoryDto.StavkaDetails> stavkeDetailsList = new ArrayList<>();
            for (Stavka stavka : stavke) {
                Proizvod proizvod = getProductById(stavka.getProizvodID());

                PurchaseHistoryDto.StavkaDetails stavkaDetails = new PurchaseHistoryDto.StavkaDetails(
                        stavka.getIdStavka(),
                        stavka.getRacunID(),
                        stavka.getProizvodID(),
                        stavka.getKolicina(),
                        proizvod.getNaziv(),
                        proizvod.getCijena()
                );

                stavkeDetailsList.add(stavkaDetails);
            }

            PurchaseHistoryDto.RacunDetails racunDetails = new PurchaseHistoryDto.RacunDetails(racun, stavkeDetailsList);
            racunDetailsList.add(racunDetails);
        }

        return new PurchaseHistoryDto(racunDetailsList);
    }

    /********************************************************************************************************************************/

    @Override
    public void addProduct(Proizvod product) {
        String sql = "INSERT INTO Proizvod (KategorijaID, Naziv, Cijena, DostupnaKolicina) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getKategorijaID(), product.getNaziv(), product.getCijena(), product.getDostupnaKolicina());
    }

    @Override
    public void updateProduct(Proizvod product) {
        String sql = "UPDATE Proizvod SET KategorijaID = ?, Naziv = ?, Cijena = ?, DostupnaKolicina = ? WHERE IDProizvod = ?";
        jdbcTemplate.update(sql, product.getKategorijaID(), product.getNaziv(), product.getCijena(), product.getDostupnaKolicina(), product.getIdProizvod());
    }

    @Override
    public void deleteProductById(int id) {
        String sql = "DELETE FROM Proizvod WHERE IDProizvod = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean productHasDependentItems(int productId) {
        String sql = "SELECT COUNT(*) FROM Stavka WHERE ProizvodID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{productId}, Integer.class);
        return (count != null && count > 0);
    }

    /********************************************************************************************************************************/



    /********************************************************************************************************************************/

}
