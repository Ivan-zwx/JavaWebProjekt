package hr.algebra.javawebprojekt.repository;

import hr.algebra.javawebprojekt.domain.Kategorija;
import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.session.Cart;

import java.util.List;

public interface StoreRepository {
    List<Proizvod> getAllProducts();
    List<Kategorija> getAllCategories();
    Proizvod getProductById(int productId);
    void savePurchase(Cart cart, String username, String nacinKupovine);
}
