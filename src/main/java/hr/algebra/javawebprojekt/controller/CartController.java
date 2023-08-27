package hr.algebra.javawebprojekt.controller;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import hr.algebra.javawebprojekt.domain.Proizvod;
import hr.algebra.javawebprojekt.paypal.PaypalService;
import hr.algebra.javawebprojekt.session.Cart;
import hr.algebra.javawebprojekt.session.CartItem;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("store/cart")
@AllArgsConstructor
@SessionAttributes("cart")
public class CartController {

    private final StoreRepository storeRepository;

    private final PaypalService paypalService;

    @ModelAttribute("cart")
    public Cart getCart() {
        return new Cart();
    }

    @GetMapping("")
    public String showCart(@ModelAttribute("cart") Cart cart,
                           @RequestParam(required = false) String purchase,
                           @RequestParam(required = false) String paymentMethod,
                           @RequestParam(required = false) String paymentId,
                           @RequestParam(required = false) String PayerID,
                           Authentication authentication,
                           Model model) {

        String purchaseStatus = purchase;
        if (authentication != null && authentication.isAuthenticated()) {
            boolean hasUserRole = false;
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if ("ROLE_USER".equals(authority.getAuthority())) {
                    hasUserRole = true;
                    break;
                }
            }
            if (hasUserRole && "success".equals(purchase)) {
                String username = authentication.getName();
                if ("Paypal".equals(paymentMethod)) {
                    if (paymentId != null && PayerID != null) {
                        if (!paymentId.isBlank() && !PayerID.isBlank()) {
                            try {
                                Payment payment = paypalService.executePayment(paymentId, PayerID);
                                if ("approved".equals(payment.getState())) {
                                    storeRepository.savePurchase(cart, username, paymentMethod);
                                    cart.removeAllItems();
                                } else {
                                    purchaseStatus = "error";
                                }
                            } catch (PayPalRESTException e) {
                                e.printStackTrace();
                                purchaseStatus = "error";
                            }
                        }
                    }
                } else if ("Cash".equals(paymentMethod)) {
                    storeRepository.savePurchase(cart, username, paymentMethod);
                    cart.removeAllItems();
                }
            }
        }

        model.addAttribute("cartItems", cart.getCartItems());
        model.addAttribute("purchaseStatus", purchaseStatus);
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@ModelAttribute("cart") Cart cart,
                            @RequestParam int productId,
                            @RequestParam int quantity) {

        Proizvod product = storeRepository.getProductById(productId);

        int currentQuantityInCart = cart.getCurrentQuantityForProduct(productId);
        int newTotalQuantity = currentQuantityInCart + quantity;

        if (newTotalQuantity <= product.getDostupnaKolicina()) {
            cart.addItem(new CartItem(product, quantity));
            //System.out.println("ADDED TO CART: " + product + " (ID=" + productId + ") " + "*" + quantity);
        }
        return "redirect:/store/products";
    }

    @PostMapping("/update")
    public String updateCartItem(@ModelAttribute("cart") Cart cart,
                                 @RequestParam int productId,
                                 @RequestParam int newQuantity) {
        cart.updateItemQuantity(productId, newQuantity);
        return "redirect:/store/cart";
    }

    @PostMapping("/remove")
    public String removeCartItem(@ModelAttribute("cart") Cart cart,
                                 @RequestParam int productId) {
        cart.removeItem(productId);
        return "redirect:/store/cart";
    }

    @PostMapping("/clear")
    public String clearCart(@ModelAttribute("cart") Cart cart) {
        cart.removeAllItems();
        return "redirect:/store/cart";
    }
}

