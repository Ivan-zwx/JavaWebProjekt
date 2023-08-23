package hr.algebra.javawebprojekt.controller.admin;

import hr.algebra.javawebprojekt.domain.Kategorija;
import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/products")
@AllArgsConstructor
public class AdminProductController {

    private final StoreRepository storeRepository;


    @GetMapping("")
    public String getAllProducts(@RequestParam(required = false) Integer categoryID,
                                 @RequestParam(required = false) String deletion,
                                 Model model) {
        List<Proizvod> products = storeRepository.getAllProducts();
        List<Kategorija> categories = storeRepository.getAllCategories();

        Map<Integer, String> categoryMap = categories.stream()
                .collect(Collectors.toMap(Kategorija::getIdKategorija, Kategorija::getNaziv));

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryMap", categoryMap);

        if (categoryID != null) {
            String selectedCategoryName = categoryMap.get(categoryID);
            if (selectedCategoryName != null) {
                model.addAttribute("selectedCategoryName", selectedCategoryName);
            }
        }

        model.addAttribute("deletionStatus", deletion);

        return "admin/products";
    }


    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Proizvod());
        model.addAttribute("categories", storeRepository.getAllCategories());
        return "admin/add-product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Proizvod product) {
        storeRepository.addProduct(product);
        return "redirect:/admin/products";
    }


    @GetMapping("/edit")
    public String showUpdateProductForm(@RequestParam("id") Integer id, Model model) {
        Optional<Proizvod> productOptional = Optional.ofNullable(storeRepository.getProductById(id));
        Proizvod product = productOptional.orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", storeRepository.getAllCategories());
        return "admin/update-product";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute Proizvod product) {
        storeRepository.updateProduct(product);
        return "redirect:/admin/products";
    }


    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") Integer id) {
        if (!storeRepository.productHasDependentItems(id)) {
            storeRepository.deleteProductById(id);
            return "redirect:/admin/products?deletion=success";
        } else if (storeRepository.productHasDependentItems(id)) {
            return "redirect:/admin/products?deletion=unable";
        } else {
            return "redirect:/admin/products?deletion=error";
        }
    }
}
