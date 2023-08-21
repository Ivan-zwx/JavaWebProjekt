package hr.algebra.javawebprojekt.repository;

import hr.algebra.javawebprojekt.domain.Kategorija;
import hr.algebra.javawebprojekt.domain.Proizvod;

import java.util.List;

public interface StoreRepository {
    List<Proizvod> getAllProducts();
    List<Kategorija> getAllCategories();
}
