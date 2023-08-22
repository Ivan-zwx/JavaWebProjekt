package hr.algebra.javawebprojekt.repository;

import hr.algebra.javawebprojekt.domain.Kategorija;
import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.domain.Racun;
import hr.algebra.javawebprojekt.domain.Stavka;
import hr.algebra.javawebprojekt.session.Cart;

import java.util.List;
import java.util.Map;

public interface StoreRepository {
    List<Proizvod> getAllProducts();
    List<Kategorija> getAllCategories();
    Proizvod getProductById(int productId);
    void savePurchase(Cart cart, String username, String nacinKupovine);
    List<Racun> getRacuniForUser(String username);
    public List<Stavka> getStavkeForRacun(int racunId);
    Map<Racun, List<Stavka>> getRacuniWithStavkeForUser(String username);
}
