package hr.algebra.javawebprojekt.controller.admin;

import hr.algebra.javawebprojekt.domain.LoginHistory;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("admin/logins")
@AllArgsConstructor
public class AdminLoginHistoryController {

    private final StoreRepository storeRepository;

    @GetMapping("")
    public String getCompleteLoginHistory(Model model) {
        List<LoginHistory> completeLoginHistory = storeRepository.getCompleteLoginHistory();
        model.addAttribute("completeLoginHistory", completeLoginHistory);
        return "admin/logins";
    }
}
