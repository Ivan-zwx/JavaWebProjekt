package hr.algebra.javawebprojekt.controller;

import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.dto.Cart;
import hr.algebra.javawebprojekt.dto.CartItem;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
@SessionAttributes("cart")
public class CartController {

    private final StoreRepository storeRepository;

    @ModelAttribute("cart")
    public Cart getCart() {
        return new Cart();
    }

    @GetMapping
    public String showCart(@ModelAttribute("cart") Cart cart, Model model) {
        model.addAttribute("cartItems", cart.getCartItems());
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@ModelAttribute("cart") Cart cart,
                            @RequestParam int productId,
                            @RequestParam int quantity) {
        Proizvod product = storeRepository.getProductById(productId);
        cart.addItem(new CartItem(product, quantity));
        return "redirect:/store/products";
    }

    // ... other methods like remove and update
}

