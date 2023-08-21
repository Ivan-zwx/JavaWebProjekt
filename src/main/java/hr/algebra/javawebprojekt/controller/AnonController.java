package hr.algebra.javawebprojekt.controller;

import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@AllArgsConstructor
public class AnonController {

    private final StoreRepository storeRepository;

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Proizvod> products = storeRepository.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }
}
