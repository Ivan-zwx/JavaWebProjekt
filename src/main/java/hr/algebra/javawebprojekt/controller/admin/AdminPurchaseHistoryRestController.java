package hr.algebra.javawebprojekt.controller.admin;

import hr.algebra.javawebprojekt.dto.PurchaseHistoryDto;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/admin/purchases")
@AllArgsConstructor
public class AdminPurchaseHistoryRestController {

    private final StoreRepository storeRepository;

    @GetMapping("")
    public List<PurchaseHistoryDto> getCompletePurchaseHistory() {
        return storeRepository.getCompletePurchaseHistory();
    }
}
