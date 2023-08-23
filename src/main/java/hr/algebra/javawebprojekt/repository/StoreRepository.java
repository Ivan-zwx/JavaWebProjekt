package hr.algebra.javawebprojekt.repository;

import hr.algebra.javawebprojekt.domain.Kategorija;
import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.domain.Racun;
import hr.algebra.javawebprojekt.domain.Stavka;
import hr.algebra.javawebprojekt.dto.PurchaseHistoryDto;
import hr.algebra.javawebprojekt.session.Cart;

import java.util.List;
import java.util.Map;

public interface StoreRepository {
    List<Proizvod> getAllProducts();
    List<Kategorija> getAllCategories();
    Proizvod getProductById(int productId);
    void savePurchase(Cart cart, String username, String nacinKupovine);
    PurchaseHistoryDto getPurchaseHistoryForUser(String username);
    /**********************************************************************************/
    void addProduct(Proizvod product);
    void updateProduct(Proizvod product);
    void deleteProductById(int id);
}
