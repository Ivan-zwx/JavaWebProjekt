package hr.algebra.javawebprojekt.controller;

import hr.algebra.javawebprojekt.dto.PurchaseHistoryDto;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("store/purchases")
@AllArgsConstructor
public class PurchaseHistoryController {

    private final StoreRepository storeRepository;

    @GetMapping("")
    public String showCompletePurchaseHistory(Authentication authentication, Model model) {
        String username = authentication.getName();
        PurchaseHistoryDto purchaseHistory = storeRepository.getPurchaseHistoryForUser(username);
        model.addAttribute("purchaseHistory", purchaseHistory);
        return "purchases";
    }
}
