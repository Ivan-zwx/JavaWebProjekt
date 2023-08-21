package hr.algebra.javawebprojekt.repository;

import hr.algebra.javawebprojekt.domain.Kategorija;
import hr.algebra.javawebprojekt.domain.Proizvod;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
