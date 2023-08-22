package hr.algebra.javawebprojekt.controller;

import hr.algebra.javawebprojekt.session.Cart;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("store/checkout")
@AllArgsConstructor
@SessionAttributes("cart")
public class CheckoutController {

    private final StoreRepository storeRepository;

    @ModelAttribute("cart")
    public Cart getCart() {
        return new Cart();
    }

    @PostMapping("/process")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String checkout(@ModelAttribute("cart") Cart cart,
                           @RequestParam String paymentMethod,
                           Authentication authentication) {
        String username = authentication.getName();

        if ("Cash".equals(paymentMethod)) {
            storeRepository.savePurchase(cart, username, paymentMethod);
            cart.removeAllItems();
            return "redirect:/store/cart?purchase=success";
        } else if ("Paypal".equals(paymentMethod)) {
            return "redirect:/store/cart?purchase=paypal_not_implemented";
        } else {
            return "redirect:/store/cart?purchase=error";
        }
    }
}
