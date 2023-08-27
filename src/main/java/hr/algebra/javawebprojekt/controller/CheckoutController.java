package hr.algebra.javawebprojekt.controller;

import hr.algebra.javawebprojekt.paypal.PaypalService;
import hr.algebra.javawebprojekt.session.Cart;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Links;
import com.paypal.base.rest.PayPalRESTException;

@Controller
@RequestMapping("store/checkout")
@AllArgsConstructor
@SessionAttributes("cart")
public class CheckoutController {

    private final StoreRepository storeRepository;

    private final PaypalService paypalService;

    @ModelAttribute("cart")
    public Cart getCart() {
        return new Cart();
    }

    @PostMapping("/process")
    public String checkout(@ModelAttribute("cart") Cart cart,
                           @RequestParam String paymentMethod) {

        if (cart.isEmpty()) {
            return "redirect:/store/cart?purchase=empty_cart";
        }

        if ("Cash".equals(paymentMethod)) {
            return "redirect:/store/cart?purchase=success&paymentMethod=Cash";
        } else if ("Paypal".equals(paymentMethod)) {
            try {
                double totalAmount = cart.getTotal();
                Payment payment = paypalService.createPayment(totalAmount, "EUR", "paypal", "sale",
                        "Test payment",
                        "http://localhost:8081/store/cart?purchase=error&paymentMethod=Paypal",
                        "http://localhost:8081/store/cart?purchase=success&paymentMethod=Paypal");
                for(Links link : payment.getLinks()) {
                    if(link.getRel().equals("approval_url")) {
                        return "redirect:" + link.getHref();
                    }
                }
                return "redirect:/store/cart?purchase=error&paymentMethod=Paypal";
            } catch (PayPalRESTException e) {
                e.printStackTrace();
                return "redirect:/store/cart?purchase=error&paymentMethod=Paypal";
            }
        } else {
            return "redirect:/store/cart?purchase=error";
        }
    }
}
