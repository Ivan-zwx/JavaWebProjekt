package hr.algebra.javawebprojekt.controller;

import org.springframework.ui.Model;
import hr.algebra.javawebprojekt.domain.Racun;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("store/purchases")
@AllArgsConstructor
public class PurchaseHistoryController {

    private final StoreRepository storeRepository;

    @GetMapping("")
    public String showPurchaseHistory(Authentication authentication, Model model) {
        String username = authentication.getName();
        //List<Racun> racuni = storeRepository.getRacuniForUser(username);
        List<Racun> racuni = new ArrayList<>();
        model.addAttribute("racuni", racuni);
        return "purchases";
    }
}
