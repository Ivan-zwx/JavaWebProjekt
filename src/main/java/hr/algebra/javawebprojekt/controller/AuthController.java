package hr.algebra.javawebprojekt.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
@AllArgsConstructor
public class AuthController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
