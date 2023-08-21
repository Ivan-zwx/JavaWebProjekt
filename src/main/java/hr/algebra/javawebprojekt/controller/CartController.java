package hr.algebra.javawebprojekt.controller;

import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.session.Cart;
import hr.algebra.javawebprojekt.session.CartItem;
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

    @GetMapping("")
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
        //System.out.println("ADDED TO CART: " + product + " (ID=" + productId + ") " + "*" + quantity);
        return "redirect:/store/products";
    }

    @PostMapping("/update")
    public String updateCartItem(@ModelAttribute("cart") Cart cart,
                                 @RequestParam int productId,
                                 @RequestParam int newQuantity) {
        cart.updateItemQuantity(productId, newQuantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeCartItem(@ModelAttribute("cart") Cart cart,
                                 @RequestParam int productId) {
        cart.removeItem(productId);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(@ModelAttribute("cart") Cart cart) {
        cart.removeAllItems();
        return "redirect:/cart";
    }
}

