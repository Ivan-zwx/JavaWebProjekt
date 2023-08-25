package hr.algebra.javawebprojekt.repository;

import hr.algebra.javawebprojekt.domain.*;
import hr.algebra.javawebprojekt.dto.PurchaseHistoryDto;
import hr.algebra.javawebprojekt.session.Cart;

import java.util.List;
import java.util.Map;

public interface StoreRepository {
    /**********************************************************************************/
    List<Proizvod> getAllProducts();
    List<Kategorija> getAllCategories();
    Proizvod getProductById(int productId);
    void savePurchase(Cart cart, String username, String nacinKupovine);
    PurchaseHistoryDto getPurchaseHistoryForUser(String username);
    /**********************************************************************************/
    void addProduct(Proizvod product);
    void updateProduct(Proizvod product);
    void deleteProductById(int productId);
    boolean productHasDependentItems(int productId);
    /**********************************************************************************/
    void addCategory(Kategorija category);
    void updateCategory(Kategorija category);
    void deleteCategoryById(int categoryId);
    Kategorija getCategoryById(int categoryId);
    boolean categoryHasDependentProducts (int categoryId);
    /**********************************************************************************/
    void addRequestHistory(RequestHistory requestHistory);
    void addLoginHistory(LoginHistory loginHistory);
    /**********************************************************************************/
}
