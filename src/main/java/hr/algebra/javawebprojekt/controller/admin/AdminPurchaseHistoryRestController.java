package hr.algebra.javawebprojekt.controller.admin;

import hr.algebra.javawebprojekt.dto.PurchaseHistoryDto;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin/purchases")
@AllArgsConstructor
public class AdminPurchaseHistoryRestController {

    private final StoreRepository storeRepository;

    /*
    @GetMapping("")
    public List<PurchaseHistoryDto> getCompletePurchaseHistory() {
        return storeRepository.getCompletePurchaseHistory();
    }
    */

    @GetMapping("")
    public List<PurchaseHistoryDto> getCompletePurchaseHistory(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "startDate", required = false) String startDateStr,
            @RequestParam(name = "endDate", required = false) String endDateStr) {

        List<PurchaseHistoryDto> completePurchaseHistory = storeRepository.getCompletePurchaseHistory();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        return completePurchaseHistory.stream()
                .filter(dto -> dto.getRacunDetailsList().stream().anyMatch(racunDetails -> {
                    if (username != null) {
                        return username.equals(racunDetails.getRacun().getUsername());
                    }
                    return true;
                }))
                .map(dto -> new PurchaseHistoryDto(
                        dto.getRacunDetailsList().stream()
                                .filter(racunDetails -> {
                                    if (startDateStr != null) {
                                        LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
                                        LocalDateTime invoiceDate = LocalDateTime.parse(racunDetails.getRacun().getVrijemeKupovine(), formatter);
                                        return !invoiceDate.isBefore(startDate);
                                    }
                                    if (endDateStr != null) {
                                        LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);
                                        LocalDateTime invoiceDate = LocalDateTime.parse(racunDetails.getRacun().getVrijemeKupovine(), formatter);
                                        return !invoiceDate.isAfter(endDate);
                                    }
                                    return true;
                                })
                                .collect(Collectors.toList())
                ))
                .filter(dto -> !dto.getRacunDetailsList().isEmpty())
                .collect(Collectors.toList());
    }
}
