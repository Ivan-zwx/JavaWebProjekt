package hr.algebra.javawebprojekt.repository;

import hr.algebra.javawebprojekt.domain.Kategorija;
import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.domain.Racun;
import hr.algebra.javawebprojekt.domain.Stavka;
import hr.algebra.javawebprojekt.session.Cart;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Primary
@Repository
public class StoreRepositoryJdbc implements StoreRepository {

    private final JdbcTemplate jdbcTemplate;

    public StoreRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


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

}
