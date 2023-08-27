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
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        DateTimeFormatter parameterFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        //System.out.println("**************************************************");
        //System.out.println("Server method getCompletePurchaseHistory called");
        //System.out.println("Username: " + username);
        //System.out.println("StartDate: " + startDateStr);
        //System.out.println("EndDate: " + endDateStr);

        //System.out.println("Total records fetched: " + completePurchaseHistory.size());

        List<PurchaseHistoryDto> filteredPurchaseHistory = completePurchaseHistory.stream()
                .filter(dto -> dto.getRacunDetailsList().stream().anyMatch(racunDetails -> {
                    if (username != null && !username.isBlank()) {
                        return username.equals(racunDetails.getRacun().getUsername());
                    }
                    return true;
                }))
                .map(dto -> new PurchaseHistoryDto(
                        dto.getRacunDetailsList().stream()
                                .filter(racunDetails -> {
                                    if (startDateStr != null && !startDateStr.isBlank()) {
                                        LocalDateTime startDate = LocalDateTime.parse(startDateStr, parameterFormatter);
                                        LocalDateTime invoiceDate = LocalDateTime.parse(racunDetails.getRacun().getVrijemeKupovine(), dataFormatter);
                                        return !invoiceDate.isBefore(startDate);
                                    }
                                    if (endDateStr != null && !endDateStr.isBlank()) {
                                        LocalDateTime endDate = LocalDateTime.parse(endDateStr, parameterFormatter);
                                        LocalDateTime invoiceDate = LocalDateTime.parse(racunDetails.getRacun().getVrijemeKupovine(), dataFormatter);
                                        return !invoiceDate.isAfter(endDate);
                                    }
                                    return true;
                                })
                                .collect(Collectors.toList())
                ))
                .filter(dto -> !dto.getRacunDetailsList().isEmpty())
                .collect(Collectors.toList());

        //System.out.println("Total records after filtering: " + filteredPurchaseHistory.size());
        //System.out.println("**************************************************");

        return filteredPurchaseHistory;
    }
}
