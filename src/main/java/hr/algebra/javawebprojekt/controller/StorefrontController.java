package hr.algebra.javawebprojekt.controller;

import hr.algebra.javawebprojekt.domain.Kategorija;
import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("store")
@AllArgsConstructor
public class StorefrontController {

    private final StoreRepository storeRepository;

    @GetMapping("")
    public String home() {
        return "home";
    }

    // http://localhost:8081/store/products
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Proizvod> products = storeRepository.getAllProducts();
        List<Kategorija> categories = storeRepository.getAllCategories();

        Map<Integer, String> categoryMap = categories.stream().collect(Collectors.toMap(Kategorija::getIdKategorija, Kategorija::getNaziv));

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryMap", categoryMap);
        return "products";
    }
}
