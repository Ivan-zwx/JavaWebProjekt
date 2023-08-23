package hr.algebra.javawebprojekt.controller.admin;

import hr.algebra.javawebprojekt.domain.Kategorija;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/categories")
@AllArgsConstructor
public class AdminCategoryController {

    private final StoreRepository storeRepository;

    @GetMapping("")
    public String getAllCategories(@RequestParam(required = false) String deletion, Model model) {
        List<Kategorija> categories = storeRepository.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("deletionStatus", deletion);
        return "admin/categories";
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Kategorija());
        return "admin/add-category";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute Kategorija category) {
        storeRepository.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit")
    public String showUpdateCategoryForm(@RequestParam("id") Integer id, Model model) {
        Optional<Kategorija> categoryOptional = Optional.ofNullable(storeRepository.getCategoryById(id));
        Kategorija category = categoryOptional.orElseThrow(() -> new IllegalArgumentException("Invalid category Id: " + id));
        model.addAttribute("category", category);
        return "admin/update-category";
    }

    @PostMapping("/edit")
    public String updateCategory(@ModelAttribute Kategorija category) {
        storeRepository.updateCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("id") Integer id) {
        if (!storeRepository.categoryHasDependentProducts(id)) {
            storeRepository.deleteCategoryById(id);
            return "redirect:/admin/categories?deletion=success";
        } else if (storeRepository.categoryHasDependentProducts(id)) {
            return "redirect:/admin/categories?deletion=unable";
        } else {
            return "redirect:/admin/categories?deletion=error";
        }
    }
}
