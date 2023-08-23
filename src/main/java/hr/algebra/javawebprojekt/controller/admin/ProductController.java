package hr.algebra.javawebprojekt.controller.admin;

import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("admin/products")
@AllArgsConstructor
public class ProductController {

    private final StoreRepository storeRepository;


    @GetMapping("")
    public String getAllProducts(Model model) {
        model.addAttribute("products", storeRepository.getAllProducts());
        return "admin/products";
    }


    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Proizvod());
        return "admin/add-product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Proizvod product) {
        storeRepository.addProduct(product);
        return "redirect:/admin/products/";
    }


    @GetMapping("/edit")
    public String showUpdateProductForm(@RequestParam("id") Integer id, Model model) {
        Optional<Proizvod> productOptional = Optional.ofNullable(storeRepository.getProductById(id));
        Proizvod product = productOptional.orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        model.addAttribute("product", product);
        return "admin/update-product";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute Proizvod product) {
        storeRepository.updateProduct(product);
        return "redirect:/admin/products/";
    }


    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") Integer id) {
        storeRepository.deleteProductById(id);
        return "redirect:/admin/products/";
    }
}
